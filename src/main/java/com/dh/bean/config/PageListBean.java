package com.dh.bean.config;

import java.util.List;

/**
 * 用于解析页面中的列表元素
 *
 * @author Created by lixiangyu on 2017/7/25.
 */
public class PageListBean {
    private String name;
    private String xpathOuter;
    private List<PageKvBean> kvs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXpathOuter() {
        return xpathOuter;
    }

    public void setXpathOuter(String xpathOuter) {
        this.xpathOuter = xpathOuter;
    }

    public List<PageKvBean> getKvs() {
        return kvs;
    }

    public void setKvs(List<PageKvBean> kvs) {
        this.kvs = kvs;
    }
}