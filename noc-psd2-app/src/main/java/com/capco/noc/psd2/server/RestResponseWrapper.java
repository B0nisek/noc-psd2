package com.capco.noc.psd2.server;

public class RestResponseWrapper<T extends Object>{

    private Result result;
    private T data;

    public RestResponseWrapper() {}

    public RestResponseWrapper(Result result) {
        this.result = result;
    }

    public RestResponseWrapper(Result result, T data) {
        this.result = result;
        this.data = data;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static class Result{
        private int code;
        private String info;

        public Result() {}

        public Result(int code, String info) {
            this.code = code;
            this.info = info;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
