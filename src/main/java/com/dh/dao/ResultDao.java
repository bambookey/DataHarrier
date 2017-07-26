package com.dh.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dh.bean.result.Result;

/**
 * 数据抓取结果类
 * <p>
 * Created by lixiangyu on 2017/7/23.
 */
@Repository
public interface ResultDao {
    public int insertResult(Result result);

    public List<Result> selectResultBySeries(String series);

    public List<Result> selectResultByUrl(String url);

    public int updateResult(Result result);
}
