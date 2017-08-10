package com.dh.bean.config;

import java.util.List;

/**
 * 页面游走状态
 * Created by lixiangyu on 2017/7/23.
 */
public class StateBean {
    private String urlPattern;          // 页面链接规则
    private List<PageKvBean> kvs;       // 页面提取kv类型数据的规则
    private List<PageListBean> arrs;    // 页面提取arrs类型数据的规则


    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public List<PageKvBean> getKvs() {
        return kvs;
    }

    public void setKvs(List<PageKvBean> kvs) {
        this.kvs = kvs;
    }

    public List<PageListBean> getArrs() {
        return arrs;
    }

    public void setArrs(List<PageListBean> arrs) {
        this.arrs = arrs;
    }
}
