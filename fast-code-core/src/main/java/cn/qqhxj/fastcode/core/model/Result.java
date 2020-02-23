package cn.qqhxj.fastcode.core.model;

import lombok.Data;

@Data
public class Result<T> {
    private final static String DEFAULT_ERR_MSG = "请求错误";
    private final static String DEFAULT_OK_MSG = "请求成功";
    private final static Integer DEFAULT_OK_CODE = 200;
    private final static Integer DEFAULT_ERR_CODE = -1;
    private Integer code = DEFAULT_OK_CODE;
    private String msg = DEFAULT_OK_MSG;

    private T data;

    private Result() {

    }

    public static <T> Result<T> ok() {
        return new Result<T>();
    }

    public static <T> Result<T> ok(T data) {
        Result<T> ok = ok();
        ok.data = data;
        return ok;
    }

    public static <T> Result<T> ok(T data, String msg) {
        Result<T> ok = ok(data);
        ok.msg = msg;
        return ok;
    }

    public static <T> Result<T> err() {
        Result<T> result = new Result<T>();
        result.msg = DEFAULT_ERR_MSG;
        result.code = DEFAULT_ERR_CODE;
        return result;
    }

    public static <T> Result<T> err(String msg) {
        Result<T> err = err();
            err.msg = msg;
        return err;
    }

    public static <T> Result build(boolean flag, T data, String err) {
        Result<T> r = null;
        if (flag) {
            r = ok(data);
        } else {
            r = err(err);
        }
        return r;
    }

    public static <T> Result build(int code, T data, String msg) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }


    public static <T> Result<T> err(Integer code, String msg) {
        Result<T> err = err(msg);
        err.setCode(code);
        return err;
    }


}
