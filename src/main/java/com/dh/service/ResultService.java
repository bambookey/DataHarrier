package com.dh.service;

import com.dh.bean.result.Result;
import com.dh.dao.ResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixiangyu on 2017/7/23.
 */
@Service
public class ResultService {

    @Autowired
    ResultDao resultDao;

    /**
     * 新增一条爬取结果，如果存在则更新
     *
     * @param result
     */
    public void insertResult(Result result) {
        List<Result> results = resultDao.selectResultByUrl(result.getUrl());
        if(results.size() == 0) {
            resultDao.insertResult(result);
        } else {
            resultDao.updateResult(result);
        }

    }
}
