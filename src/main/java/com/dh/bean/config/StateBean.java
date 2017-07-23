package com.dh.bean.config;

import java.util.List;

/**
 * 页面游走状态
 * Created by lixiangyu on 2017/7/23.
 */
public class StateBean {
    private String series;
    private String urlPattern;
    private Integer enterCount = 100;
    private List<PageKvBean> kvs;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public Integer getEnterCount() {
        return enterCount;
    }

    public void setEnterCount(Integer enterCount) {
        this.enterCount = enterCount;
    }

    public List<PageKvBean> getKvs() {
        return kvs;
    }

    public void setKvs(List<PageKvBean> kvs) {
        this.kvs = kvs;
    }
}
