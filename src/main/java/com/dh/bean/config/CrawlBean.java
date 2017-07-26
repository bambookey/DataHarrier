package com.dh.bean.config;

import java.util.List;

/**
 * Created by lixiangyu on 2017/7/23.
 */
public class CrawlBean {
    private String seedUrl;
    private List<StateBean> states;
    private String series;                      // 系列名称
    private boolean useDbPersistence = false;   // 使用数据库做持久化
    private boolean useFilePersistence = false; // 使用文件做持久化
    private String filePersistencePath;         // 文件保存路径
    private int pulseMillionSeconds = 1000;   // 抓取时间间隔

    public int getPulseMillionSeconds() {
        return pulseMillionSeconds;
    }

    public void setPulseMillionSeconds(int pulseMillionSeconds) {
        this.pulseMillionSeconds = pulseMillionSeconds;
    }

    public boolean isUseFilePersistence() {
        return useFilePersistence;
    }

    public void setUseFilePersistence(boolean useFilePersistence) {
        this.useFilePersistence = useFilePersistence;
    }

    public String getFilePersistencePath() {
        return filePersistencePath;
    }

    public void setFilePersistencePath(String filePersistencePath) {
        this.filePersistencePath = filePersistencePath;
    }

    public boolean isUseDbPersistence() {
        return useDbPersistence;
    }

    public void setUseDbPersistence(boolean useDbPersistence) {
        this.useDbPersistence = useDbPersistence;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

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
