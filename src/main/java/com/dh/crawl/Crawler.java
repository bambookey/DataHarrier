package com.dh.crawl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dh.bean.config.CrawlBean;
import com.dh.bean.config.PageKvBean;
import com.dh.bean.config.PageListBean;
import com.dh.bean.config.StateBean;
import com.dh.bean.result.Result;
import com.dh.service.ResultService;
import com.dh.util.NetUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 核心抓取类
 * <p>
 * Created by lixiangyu on 2017/7/23.
 */
@Component
@Scope("prototype")
public class Crawler implements PageProcessor {

    @Autowired
    ResultService resultService;

    private CrawlBean crawlBean;

    private static volatile Set<String> visitedLinks = new HashSet<String>();

    private Site site;

    @Override
    public void process(Page page) {

        String pageUrl = page.getUrl().toString();
        if (visitedLinks.contains(pageUrl)) {
            return;
        } else {
            visitedLinks.add(page.getUrl().get());
        }

        List<StateBean> states = crawlBean.getStates();
        for (StateBean state : states) {
            if (!page.getUrl().regex(state.getUrlPattern()).match()) {
                continue;
            }
            Map<String, Object> data = new HashMap<String, Object>();

            // 解析页面KV形式内容
            List<PageKvBean> kvs = state.getKvs();
            if (null != kvs) {
                for (PageKvBean kv : kvs) {
                    String k = kv.getK();
                    String v = page.getHtml().xpath(kv.getXpath()).toString();
                    System.out.println(k + ":" + v);
                    data.put(k, v);
                }
            }

            // 解析页面列表形式内容
            List<PageListBean> arrs = state.getArrs();
            if (null != arrs) {
                for (PageListBean arr : arrs) {
                    List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
                    List<Selectable> nodes = page.getHtml().xpath(arr.getXpathOuter()).nodes();
                    for (Selectable node : nodes) {
                        Map<String, Object> oData = new HashMap<String, Object>();
                        for (PageKvBean kv : arr.getKvs()) {
                            String k = kv.getK();
                            String v = node.xpath(kv.getXpath()).toString();
                            oData.put(k, v);
                        }
                        listData.add(oData);
                    }
                    data.put(arr.getName(), listData);
                }
            }

            // 生成抓取页面结果
            Result result = new Result();
            result.setUrl(pageUrl);
            result.setUpdateTime(new Date());
            result.setSeries(crawlBean.getSeries());
            result.setData(data);
            if (crawlBean.isUseDbPersistence()) {
                resultService.insertResult(result);
            }
            if (crawlBean.isUseFilePersistence()) {
                try {
                    FileUtils.writeStringToFile(new File(crawlBean.getFilePersistencePath()), JSON.toJSONString(result) + "\n", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(JSON.toJSONString(result));
            break;
        }

        // 提取网页内链
        List<String> links = page.getHtml().xpath("//a").links().all();
        List<String> unvisitedLinks = new ArrayList<String>();
        for (String link : links) {
            for (StateBean state : states) {
                if (link.contains(state.getUrlPattern())) {
                    unvisitedLinks.add(link);
                    break;
                }
            }
        }
        page.addTargetRequests(unvisitedLinks);
    }

    public void start(CrawlBean crawlBean) {
        Site site = Site.me();
        site.setSleepTime(crawlBean.getPulseMillionSeconds());
        site.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/59.0.3071.115");
        this.site = site;
        this.crawlBean = crawlBean;

        Spider spider = Spider.create(this).addUrl(crawlBean.getSeedUrl()).thread(crawlBean.getThreadsCnt());
        // 使用代理模式
        if (crawlBean.isUseProxyPool()) {
            HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
            Proxy[] proxies = getProxies();
            SimpleProxyProvider proxyProvider = SimpleProxyProvider.from(proxies);
            httpClientDownloader.setProxyProvider(proxyProvider);
            spider.setDownloader(httpClientDownloader);
        }
        spider.run();
    }

    /**
     * 获取代理池
     *
     * @return
     */
    private Proxy[] getProxies() {
        List<String> lines = new ArrayList<String>();
        try {
            lines = FileUtils.readLines(new File("proxy.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Proxy> proxyList = new ArrayList<Proxy>();
        int crawledHosts = 0;
        int avaliableHosts = 0;
        for (String line : lines) {
            Result result = JSON.parseObject(line, Result.class);
            Object data = result.getData();
            JSONObject oLineProxies = JSON.parseObject(data.toString());
            JSONArray proxyArray = JSON.parseArray(oLineProxies.get("proxys").toString());
            for (Object proxy : proxyArray) {
                crawledHosts++;
                String ip = JSON.parseObject(proxy.toString()).getString("IP");
                int port = JSON.parseObject(proxy.toString()).getInteger("PORT");
                Proxy p = new Proxy(ip, port);
                if (NetUtil.isIpReachable(ip)) {
                    avaliableHosts++;
                    System.out.println("load proxies. avaliables:" + avaliableHosts + " crawled:" + crawledHosts);
                    proxyList.add(p);
                }

                if(avaliableHosts > 20) {
                    break;
                }
            }
            if(avaliableHosts > 20) {
                break;
            }
        }
        int len = proxyList.size();
        Proxy[] proxies = new Proxy[len];
        for (int i = 0; i < len; i++) {
            proxies[i] = proxyList.get(i);
        }
        System.out.println("proxy init finished. size" + proxies.length);
        return proxies;
    }

    @Override
    public Site getSite() {
        return site;
    }
}