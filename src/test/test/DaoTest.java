package test;

import com.dh.bean.result.Result;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lixiangyu on 2017/7/23.
 */
public class DaoTest extends BaseTest {

    @Test
    public void insertTest() {
        Result result = new Result();
        result.setData("xxx");
        result.setSeries("series");
        result.setUpdateTime(new Date());
        result.setUrl("ssss");
        resultDao.insertResult(result);
    }

    @Test
    public void selectByUrl() {
        List<Result> results = resultDao.selectResultByUrl("xxx");
        System.out.println(results.size());
    }
}
