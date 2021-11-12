package com.example.mockband.model;

public class ResultMsgBuilder {
    public static <T> ResultMsg<T> success(T data)
    {
        ResultMsg<T> result = new ResultMsg<>(EnumMsgCode.SUCCESS,data);
        return result;
    }

    public static <T> ResultMsg<T> unknownError(T data)
    {
        ResultMsg<T> result = new ResultMsg<>(EnumMsgCode.UNKONWN_ERROR,data);
        return result;
    }
}
