package com.dh.bean.result;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lixiangyu on 2017/7/23.
 */
public class Result {
    int id;
    private String data;
    private Date updateTime;
    private String url;
    private String series;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
