package com.dh.main;

import com.dh.bean.config.CrawlBean;
import com.dh.bean.config.PageKvBean;
import com.dh.bean.config.PageListBean;
import com.dh.bean.config.StateBean;
import com.dh.crawl.Crawler;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiangyu on 2017/8/10.
 */
public class Run {

    public static void main(String[] args) throws InterruptedException {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.load("classpath*:spring-*.xml");
        context.refresh();

        List<StateBean> states = new ArrayList<StateBean>();
        states.add(getHomePage());
        states.add(getUserListPage());

        CrawlBean crawlBean = new CrawlBean();
        crawlBean.setPulseMillionSeconds(1000);
        crawlBean.setThreadsCnt(1);
        crawlBean.setSeedUrl("http://blog.sciencenet.cn/blog.php?mod=member&type=%CE%A2%C9%FA%CE%EF%D7%CA%D4%B4%D3%EB%B7%D6%C0%E0%D1%A7&realmmedium=%CE%A2%C9%FA%CE%EF%D1%A7&realm=%C9%FA%C3%FC%BF%C6%D1%A7&catid=137");
        crawlBean.setStates(states);
        crawlBean.setJobName("science");
        crawlBean.setCharset("gbk");
        crawlBean.setUseProxyPool(false);
        crawlBean.setUseFilePersistence(true);
        crawlBean.setFilePersistencePath("E:/file.txt");
        crawlBean.setUseDbPersistence(false);

        Crawler crawler = context.getBean(Crawler.class);
        crawler.start(crawlBean);
    }

    /**
     * 用户列表模式
     * @return
     */
    private static StateBean getUserListPage() {
        StateBean stateBean = new StateBean();
        stateBean.setUrlPattern("http://blog.sciencenet.cn/blog.php?mod=member&");

        List<PageKvBean> kvs = new ArrayList<PageKvBean>();
        List<PageListBean> arrs = new ArrayList<PageListBean>();

        stateBean.setKvs(kvs);
        stateBean.setArrs(arrs);
        return stateBean;
    }

    /**
     * 用户首页模式
     *
     * @return
     */
    private static StateBean getHomePage() {
        StateBean stateBean = new StateBean();
        stateBean.setUrlPattern("http://blog.sciencenet.cn/home.php");

        List<PageKvBean> kvs = new ArrayList<PageKvBean>();
        List<PageListBean> arrs = new ArrayList<PageListBean>();


        PageKvBean kvBean = new PageKvBean();
        kvBean.setK("name");
        kvBean.setXpath("//div[@id='profile_content']/div[@id='pcd']/div[@class='hm']/h2/a/text()");
        kvs.add(kvBean);

        stateBean.setKvs(kvs);
        stateBean.setArrs(arrs);
        return stateBean;
    }
}
