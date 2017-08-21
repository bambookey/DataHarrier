package com.dh.main;

import com.dh.bean.config.CrawlBean;
import com.dh.bean.config.PageKvBean;
import com.dh.bean.config.PageListBean;
import com.dh.bean.config.StateBean;
import com.dh.crawl.Crawler;
import com.dh.util.CrawlerTemplateUtil;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiangyu on 2017/8/10.
 */
public class Run {

    public static void main(String[] args) throws InterruptedException, IOException {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.load("classpath*:spring-*.xml");
        context.refresh();

        CrawlBean crawlBean = new CrawlBean();
        crawlBean.setPulseMillionSeconds(1000);
        crawlBean.setThreadsCnt(1);
        crawlBean.setSeedUrl("https://www.douban.com/group/topic/80204323/?start=0");
        crawlBean.setStates(CrawlerTemplateUtil.getTemplate("/Users/lixiangyu/code/DataHarrier/src/main/resources/templates/douban"));
        crawlBean.setJobName("douban_yjs");
        crawlBean.setCharset("utf-8");
        crawlBean.setUseDbPersistence(true);
        crawlBean.setUseProxyPool(false);
        crawlBean.setUseFilePersistence(false);
        crawlBean.setFilePersistencePath("E:/file.txt");

        Crawler crawler = context.getBean(Crawler.class);
        crawler.start(crawlBean);
    }
}
