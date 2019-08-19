package com.net.point.response;

public class PersonalBean {
    /**
     * userId : 832
     * payMoney : 0
     */

    private String userId;
    private String payMoney;
    private String netSignId;

    public String getNetSignId() {
        return netSignId;
    }

    public void setNetSignId(String netSignId) {
        this.netSignId = netSignId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }
}
