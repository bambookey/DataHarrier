package test;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.dh.bean.result.Result;

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
