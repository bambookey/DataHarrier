package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dh.bean.config.CrawlBean;
import com.dh.bean.config.PageKvBean;
import com.dh.bean.config.StateBean;
import com.dh.crawl.Crawler;

/**
 * Created by lixiangyu on 2017/7/23.
 */
public class CrawlTest extends test.BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CrawlTest.class);

    @Autowired
    Crawler crawler;

    @Test
    public void crawlTest() {
        StateBean page = new StateBean();
        page.setUrlPattern("https://www.zhihu.com/collection/");


        PageKvBean kvBean = new PageKvBean();
        kvBean.setK("关注");
        kvBean.setXpath("//div[@class='QuestionHeader']/div[@class='QuestionHeader-content']/div[@class='QuestionHeader-main']/h1[@class='QuestionHeader-title']/text()");
        List<PageKvBean> kvBeans = new ArrayList<PageKvBean>();
        kvBeans.add(kvBean);
        StateBean content = new StateBean();
        content.setUrlPattern("https://www.zhihu.com/question/");
        content.setKvs(kvBeans);

        List<StateBean> states = new ArrayList<StateBean>();
        states.add(page);
        states.add(content);

        CrawlBean crawlBean = new CrawlBean();
        crawlBean.setPulseMillionSeconds(100);
        crawlBean.setSeedUrl("https://www.zhihu.com/collection/25252353");
        crawlBean.setStates(states);
        crawlBean.setJobName("zhihu_renxing");
        crawlBean.setUseProxyPool(true);
        crawler.start(crawlBean);
    }
}
