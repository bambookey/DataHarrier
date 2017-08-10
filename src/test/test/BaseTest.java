import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dh.dao.ResultDao;

/**
 * Created by lixiangyu on 2017/7/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"../resources/spring-mvc.xml", "../resources/spring-mybatis.xml"}) //加载配置文件
public class BaseTest {

    @Autowired
    ResultDao resultDao;
}
