package com.cxc.cblog.common.lang;

import lombok.Data;

/**
 * Created by cxc Cotter on 2020/4/29.
 */
@Data
public class Result {
    private int status;
    private String msg;
    private Object data;
    private String action;

    public static Result success() {
        return success("操作成功", null);
    }
    public static Result success(Object data) {
        return Result.success("操作成功", data);
    }

    public static Result success(String msg, Object data) {
        Result result=new Result();
        result.setStatus(0);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail(String msg) {
        Result result=new Result();
        result.setStatus(-1);
        result.setData(null);
        result.setMsg(msg);
        return result;
    }

    public Result action(String action) {
        setAction(action);
        return this;
    }

}
