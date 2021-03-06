package com.dh.bean.config;

import java.util.List;

/**
 * Created by lixiangyu on 2017/7/23.
 */
public class CrawlBean {
    private String seedUrl;                     // 种子链接
    private List<StateBean> states;             // 抓取规则
    private String jobName;                     // 任务名称
    private boolean useDbPersistence = false;   // 使用数据库做持久化
    private boolean useFilePersistence = false; // 使用文件做持久化
    private boolean useProxyPool = false;       // 使用代理池
    private int threadsCnt = 2;                 // 使用线程数量
    private String filePersistencePath;         // 文件保存路径
    private int pulseMillionSeconds = 1000;     // 抓取时间间隔
    private String charset = "UTF-8";           // 解析网页字符集

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public int getThreadsCnt() {
        return threadsCnt;
    }

    public void setThreadsCnt(int threadsCnt) {
        this.threadsCnt = threadsCnt;
    }

    public boolean isUseProxyPool() {
        return useProxyPool;
    }

    public void setUseProxyPool(boolean useProxyPool) {
        this.useProxyPool = useProxyPool;
    }

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

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
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
