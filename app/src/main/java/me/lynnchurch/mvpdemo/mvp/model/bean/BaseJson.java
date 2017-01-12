package me.lynnchurch.mvpdemo.mvp.model.bean;

import java.io.Serializable;

public class BaseJson<T> implements Serializable {
    private static final String CODE_SUCCESS = "0";
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return code.equals(CODE_SUCCESS);
    }
}
