package com.net.point.response;

public class HomepageBackgroundBean {
    /**
     * id : 1
     * type : 1
     * picpath : satic/homepagepic/homepage1.png
     * sort : 1
     * crttime : 1563156232000
     * remark : 描述
     */

    private int id;
    private int type;
    private String picpath;
    private int sort;
    private long crttime;
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public long getCrttime() {
        return crttime;
    }

    public void setCrttime(long crttime) {
        this.crttime = crttime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
