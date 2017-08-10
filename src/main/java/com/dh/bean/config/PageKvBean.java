package com.dh.bean.config;

/**
 * 用于解析页面KV形式内容
 * <p>
 * Created by lixiangyu on 2017/7/23.
 */
public class PageKvBean {
    private String k;       // kv类型数据的字段名
    private String xpath;   // kv类型数据value的xpath

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }
}
