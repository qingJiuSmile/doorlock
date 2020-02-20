package com.weds.doorlock.entity;

public class EnginLock {
    private Integer xh;

    private Integer lockAddr;

    private String lockLocation;

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public Integer getLockAddr() {
        return lockAddr;
    }

    public void setLockAddr(Integer lockAddr) {
        this.lockAddr = lockAddr;
    }

    public String getLockLocation() {
        return lockLocation;
    }

    public void setLockLocation(String lockLocation) {
        this.lockLocation = lockLocation == null ? null : lockLocation.trim();
    }
}