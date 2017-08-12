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
        crawlBean.setSeedUrl("http://blog.sciencenet.cn/blog.php?mod=member&type=%CE%A2%C9%FA%CE%EF%D7%CA%D4%B4%D3%EB%B7%D6%C0%E0%D1%A7&realmmedium=%CE%A2%C9%FA%CE%EF%D1%A7&realm=%C9%FA%C3%FC%BF%C6%D1%A7&catid=137");
        crawlBean.setStates(CrawlerTemplateUtil.getTemplate("/Users/lixiangyu/code/DataHarrier/src/main/resources/templates/science"));
        crawlBean.setJobName("science");
        crawlBean.setCharset("gbk");
        crawlBean.setUseProxyPool(false);
        crawlBean.setUseFilePersistence(true);
        crawlBean.setFilePersistencePath("E:/file.txt");
        crawlBean.setUseDbPersistence(false);

        Crawler crawler = context.getBean(Crawler.class);
        crawler.start(crawlBean);
    }
}
