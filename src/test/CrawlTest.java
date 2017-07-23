import com.dh.crawl.Crawler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiangyu on 2017/7/23.
 */
public class CrawlTest {


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
        crawlBean.setSeedUrl("https://www.zhihu.com/collection/25252353");
        crawlBean.setStates(states);

        Crawler crawler = new Crawler();
        crawler.setCrawlBean(crawlBean);
        crawler.start();
    }
}
