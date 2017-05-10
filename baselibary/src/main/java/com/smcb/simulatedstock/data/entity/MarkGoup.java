package com.smcb.simulatedstock.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */

public class MarkGoup implements Serializable {
    private String DATE;
    private String MARK;
    private String NUM;
    private String VER;
    private List<MarkEntity> list;

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getMARK() {
        return MARK;
    }

    public void setMARK(String MARK) {
        this.MARK = MARK;
    }

    public String getNUM() {
        return NUM;
    }

    public void setNUM(String NUM) {
        this.NUM = NUM;
    }

    public String getVER() {
        return VER;
    }

    public void setVER(String VER) {
        this.VER = VER;
    }

    public List<MarkEntity> getList() {
        return list;
    }

    public void setList(List<MarkEntity> list) {
        this.list = list;
    }
}
