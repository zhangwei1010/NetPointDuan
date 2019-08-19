package com.net.point.request;

import org.jetbrains.annotations.Nullable;


/**
 * Created by cxm
 * on 2017/3/10.
 */

public class HttpResult<T> {

    private int errcode;
    @Nullable
    private String errdesc;
    @Nullable
    private int code;
    @Nullable
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Nullable
    public String getMsg() {
        return msg;
    }

    public void setMsg(@Nullable String msg) {
        this.msg = msg;
    }

    private T data;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    @Nullable
    public String getErrdesc() {
        return errdesc;
    }

    public @Nullable
    T getData() {
        return data;
    }

    public void setErrdesc(@Nullable String errdesc) {
        this.errdesc = errdesc;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        if (errcode == RetrofitUtil.Code.ERROR_UNAVAILABLE) {
//            Context context = com.net.point.NetPointApplication.getInstance();
//            Intent intent = new Intent(com.net.point.NetPointApplication.getInstance(), LoginActivity.class);
//            if (!(context instanceof Activity))
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
        }
        return errcode == RetrofitUtil.Code.SUCCESS;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "errcode=" + errcode +
                ", errdesc='" + errdesc + '\'' +
                ", data=" + data +
                '}';
    }
}
