package com.example.mockband.model;

public class ResultMsg<T> {

    EnumMsgCode resultCode;

    T data;




    public ResultMsg(EnumMsgCode resultCode,T data)
    {
        this.resultCode = resultCode;
        this.data = data;
    }
}
