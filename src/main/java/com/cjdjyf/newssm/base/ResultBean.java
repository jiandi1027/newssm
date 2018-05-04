package com.cjdjyf.newssm.base;

import java.io.Serializable;

/**
 * @author : cjd
 * @description : AOP拦截返回为ResultBean的类
 * @date : 2018/4/24 11:18
 */
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = 200;
    public static final int FAIL = 999;
    public static final int NO_LOGIN = -1;

    /* 默认成功 若AOP拦截异常则失败*/
    private int code = SUCCESS;
    private T data;
    private String msg;
    public ResultBean() {
    }

    public ResultBean(T data) {
        this.data = data;
    }

    public ResultBean(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}