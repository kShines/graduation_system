package com.example.managementsystem.util;

import java.util.HashMap;
import java.util.Map;

public class ResultInfo<T> {
    private Map<String, Object> header;

    private T body;

    public ResultInfo(T body, Map<String, Object> header) {
        this.body = body;
        this.header = header;
    }

    public ResultInfo(int code, String message, T data) {
        this.header = new HashMap<String, Object>();
        this.header.put("code", code);
        this.header.put("message", message);
        this.body = data;
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
