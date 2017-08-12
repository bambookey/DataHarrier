package com.dh.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dh.bean.config.CrawlBean;
import com.dh.bean.config.PageKvBean;
import com.dh.bean.config.PageListBean;
import com.dh.bean.config.StateBean;
import com.dh.crawl.Crawler;

/**
 * @author lixiangyu
 */
@Service
public class ProxyPool {

    @Autowired
    static Crawler crawler;

    public static void start() {
        crawler.start(getCrawlBean());
    }

    /**
     * 获得代理抓取Bean
     *
     * @return
     */
    private static CrawlBean getCrawlBean() {
        List<PageKvBean> kvs = new ArrayList<PageKvBean>();
        PageKvBean kv1 = new PageKvBean();
        kv1.setK("IP");
        kv1.setXpath("//tr/td[1]/text()");
        PageKvBean kv2 = new PageKvBean();
        kv2.setK("PORT");
        kv2.setXpath("//tr/td[2]/text()");
        kvs.add(kv1);
        kvs.add(kv2);

        PageListBean pageListBean = new PageListBean();
        pageListBean.setName("proxys");
        pageListBean.setXpathOuter("//div/div[@id='list']/table/tbody/tr");
        pageListBean.setKvs(kvs);

        List<PageListBean> listBeans = new ArrayList<PageListBean>();
        listBeans.add(pageListBean);

        StateBean proxyPage = new StateBean();
        proxyPage.setUrlPattern("http://www.kuaidaili.com/free/inha/");
        proxyPage.setArrs(listBeans);

        List<StateBean> states = new ArrayList<StateBean>();
        states.add(proxyPage);

        CrawlBean crawlBean = new CrawlBean();
        crawlBean.setSeedUrl("http://www.kuaidaili.com/free/inha/1/");
        crawlBean.setStates(states);
        crawlBean.setJobName("proxy");
        crawlBean.setUseDbPersistence(false);
        crawlBean.setUseFilePersistence(true);
        crawlBean.setFilePersistencePath("proxy.txt");
        return crawlBean;
    }
}