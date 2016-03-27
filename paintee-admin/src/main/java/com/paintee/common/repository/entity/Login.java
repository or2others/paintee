package com.paintee.common.repository.entity;

import java.util.Date;

import com.paintee.common.object.BaseEntity;

public class Login extends BaseEntity {
    private Integer seq;

    private String userId;

    private String hash;

    private Date expireDate;

    private String accessGubun;
    
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getAccessGubun() {
        return accessGubun;
    }

    public void setAccessGubun(String accessGubun) {
        this.accessGubun = accessGubun;
    }
}