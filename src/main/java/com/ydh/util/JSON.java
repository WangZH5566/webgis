package com.ydh.util;

public class JSON {

    public static JSON SUCCESS = new JSON(0, "SUCCESS");

    public static JSON FAILURE = new JSON(1000, "FAILURE");

    private Integer code;

    private String msg;

    private Object result;

    private JSON(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public JSON setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public JSON setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public JSON setResult(Object result) {
        this.result = result;
        return this;
    }
}
