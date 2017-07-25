package com.dh.crawl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dh.bean.config.CrawlBean;
import com.dh.bean.config.PageKvBean;
import com.dh.bean.config.PageListBean;
import com.dh.bean.config.StateBean;
import com.dh.bean.result.Result;
import com.dh.service.ResultService;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
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

    private volatile Set<String> visitedLinks = new HashSet<String>();

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
            if (crawlBean.isUsePersistence()) {
                resultService.insertResult(result);
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
        this.site = Site.me().setSleepTime(1000).setUserAgent(
                "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/59.0.3071.115");
        Spider.create(this).addUrl(crawlBean.getSeedUrl()).run();
    }

    @Override
    public Site getSite() {
        return site;
    }
}