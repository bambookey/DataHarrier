

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.core.status.StatusBase;
import com.dh.bean.config.PageListBean;
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
public class ScienceTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ScienceTest.class);

    @Autowired
    Crawler crawler;

    /**
     * 用户列表模式
     * @return
     */
    private StateBean getUserListPage() {
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
    private  StateBean getHomePage() {
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

    @Test
    public void crawlTest() {

        List<StateBean> states = new ArrayList<StateBean>();
        states.add(getHomePage());
        states.add(getUserListPage());

        CrawlBean crawlBean = new CrawlBean();
        crawlBean.setPulseMillionSeconds(100);
        crawlBean.setSeedUrl("http://blog.sciencenet.cn/blog.php?mod=member");
        crawlBean.setStates(states);
        crawlBean.setSeries("science");
        crawlBean.setUseProxyPool(false);
        crawlBean.setUseDbPersistence(true);
        crawler.start(crawlBean);
    }
}
