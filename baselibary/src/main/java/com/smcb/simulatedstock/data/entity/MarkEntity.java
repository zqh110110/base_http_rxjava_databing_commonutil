package com.smcb.simulatedstock.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/26.
 */
@Entity
public class MarkEntity implements Serializable{
    @Id
    private Long id;
    private String CODE;
    private String NAME;
    private String PYTAG;
    private String PYALL;
    private String ext1;

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    private String ext2;

    public String getPYALL() {
        return PYALL;
    }

    public void setPYALL(String PYALL) {
        this.PYALL = PYALL;
    }

    private String STID;

    @Generated(hash = 1173393105)
    public MarkEntity(Long id, String CODE, String NAME, String PYTAG, String PYALL,
                      String ext1, String ext2, String STID) {
        this.id = id;
        this.CODE = CODE;
        this.NAME = NAME;
        this.PYTAG = PYTAG;
        this.PYALL = PYALL;
        this.ext1 = ext1;
        this.ext2 = ext2;
        this.STID = STID;
    }

    @Generated(hash = 86880038)
    public MarkEntity() {
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPYTAG() {
        return PYTAG;
    }

    public void setPYTAG(String PYTAG) {
        this.PYTAG = PYTAG;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSTID() {
        return STID;
    }

    public void setSTID(String STID) {
        this.STID = STID;
    }
}
