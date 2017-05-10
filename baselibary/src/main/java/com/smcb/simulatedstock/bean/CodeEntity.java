package com.smcb.simulatedstock.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/15.
 */

public class CodeEntity implements Serializable {

    private String STID;
    private String CODE;

    public CodeEntity(String code,String stid) {
        this.CODE = code;
        this.STID = stid;
    }

    public String getSTID() {
        return STID;
    }

    public void setSTID(String STID) {
        this.STID = STID;
    }
}
