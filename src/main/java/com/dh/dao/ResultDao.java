package com.dh.dao;

import com.dh.bean.result.Result;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据抓取结果类
 *
 * Created by lixiangyu on 2017/7/23.
 */
@Repository
public interface ResultDao {
    public int insertResult(Result result);

    public List<Result> selectResultBySeries(String series);

    public List<Result> selectResultByUrl(String url);

    public int updateResult(Result result);
}
