package com.smcb.simulatedstock.data.entity;


import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/7.
 */

public class LogResult implements Serializable{
    private String ERMS;

    private String ERMT;

    private String TIME;

    public void setERMS(String ERMS) {
        this.ERMS = ERMS;
    }

    public String getERMS() {
        return this.ERMS;
    }

    public void setERMT(String ERMT) {
        this.ERMT = ERMT;
    }

    public String getERMT() {
        return this.ERMT;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getTIME() {
        return this.TIME;
    }
}
