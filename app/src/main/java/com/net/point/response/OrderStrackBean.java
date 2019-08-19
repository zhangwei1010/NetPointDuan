package com.net.point.response;

public class OrderStrackBean {

    /**
     * id : 3905
     * billsnumber : 440114000000027
     * operationtime : 2019-03-14 18:10:42
     * operators : 002号快递员
     * incname : 广东省广州市花都区网点
     * incid : 44011400
     * mark : 1
     * description : 广东省广州市花都区网点已收件,扫描员是【002号快递员】
     */

    private int id;
    private String billsnumber;
    private String operationtime;
    private String operators;
    private String incname;
    private int incid;
    private int mark;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillsnumber() {
        return billsnumber;
    }

    public void setBillsnumber(String billsnumber) {
        this.billsnumber = billsnumber;
    }

    public String getOperationtime() {
        return operationtime;
    }

    public void setOperationtime(String operationtime) {
        this.operationtime = operationtime;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public String getIncname() {
        return incname;
    }

    public void setIncname(String incname) {
        this.incname = incname;
    }

    public int getIncid() {
        return incid;
    }

    public void setIncid(int incid) {
        this.incid = incid;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
