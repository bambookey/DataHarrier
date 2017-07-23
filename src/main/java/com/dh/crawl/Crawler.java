package com.dh.crawl;

import com.alibaba.fastjson.JSON;
import com.dh.bean.config.CrawlBean;
import com.dh.bean.config.PageKvBean;
import com.dh.bean.config.StateBean;
import com.dh.bean.result.Result;
import com.dh.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.*;

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

    private Site site = Site.me().setSleepTime(1000).setUserAgent(
            "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/59.0.3071.115");

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
            if (page.getUrl().regex(state.getUrlPattern()).match()) {
                List<PageKvBean> kvs = state.getKvs();
                if (null == kvs) {
                    continue;
                }

                Map<String, Object> data = new HashMap<String, Object>();
                for (PageKvBean kv : kvs) {
                    String k = kv.getK();
                    String v = page.getHtml().xpath(kv.getXpath()).toString();
                    System.out.println(k + ":" + v);
                    data.put(k, v);
                }

                Result result = new Result();
                result.setUrl(pageUrl);
                result.setUpdateTime(new Date());
                result.setSeries(crawlBean.getSeries());
                result.setData(JSON.toJSONString(data));
                resultService.insertResult(result);
                break;
            }
        }

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

    public void start() {
        if (null == crawlBean) {
            return;
        }
        Spider.create(this).addUrl(crawlBean.getSeedUrl()).run();
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void setCrawlBean(CrawlBean crawlBean) {
        this.crawlBean = crawlBean;
    }
}