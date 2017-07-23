package com.dh.bean.result;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lixiangyu on 2017/7/23.
 */
public class ResultPage {
    private Map<String, Object> fields;
    private Date createTime;
    private String url;

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
