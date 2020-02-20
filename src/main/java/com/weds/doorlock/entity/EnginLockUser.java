package com.weds.doorlock.entity;

public class EnginLockUser {

    private Integer xh;

    private String zh;

    private String pw;

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh == null ? null : zh.trim();
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw == null ? null : pw.trim();
    }
}