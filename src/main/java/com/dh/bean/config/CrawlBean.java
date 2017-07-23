package com.dh.bean.config;

import java.util.List;

/**
 * Created by lixiangyu on 2017/7/23.
 */
public class CrawlBean {
    private String seedUrl;
    private List<StateBean> states;

    public String getSeedUrl() {
        return seedUrl;
    }

    public void setSeedUrl(String seedUrl) {
        this.seedUrl = seedUrl;
    }

    public List<StateBean> getStates() {
        return states;
    }

    public void setStates(List<StateBean> states) {
        this.states = states;
    }
}
