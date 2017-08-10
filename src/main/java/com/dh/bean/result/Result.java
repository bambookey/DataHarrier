package com.dh.bean.result;

import java.util.Date;

/**
 * 抓取返回结果类
 *
 * Created by lixiangyu on 2017/7/23.
 */
public class Result {
    int id;                     // 主键id
    private Object data;        // 数据
    private Date updateTime;    // 更新日期
    private String url;         // 页面Url
    private String jobName;     // 任务名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String series) {
        this.jobName = series;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
