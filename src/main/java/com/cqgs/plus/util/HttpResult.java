package com.cqgs.plus.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HttpResult {
    // Getters 和 Setters
    private Object data;  // 用于封装所有的返回值
    private String msg;   // 用于封装一些提示信息
    private Integer code; // 用于封装一些状态码

    // 构造函数
    public HttpResult(Integer code, Object data, String msg) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }

    public HttpResult() {
    }

    // 成功的返回结果
    public static HttpResult successResult(Object object) {
        return new HttpResult(200, object, "");
    }

    // 错误的返回结果
    public static HttpResult errorResult(String msg) {
        return new HttpResult(100, null, msg);
    }

    // 未登录的返回结果
    public static HttpResult nologinResult(String msg) {
        return new HttpResult(300, null, msg);
    }

    @Override
    public String toString() {
        return String.format("{\"code\":%d,\"msg\":\"%s\",\"data\":%s}",
                code,
                msg != null ? msg : "",
                data != null ? "\"" + data.toString() + "\"" : null);
    }
}
