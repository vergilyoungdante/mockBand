package com.example.mockband.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public enum EnumMsgCode implements Serializable {
    @SerializedName("200")
    SUCCESS(200),
    @SerializedName("-1")
    UNKONWN_ERROR(-1),

    ;
    private int code;

    EnumMsgCode(int code) {
        this.code = code;
    }

    @Override
    public String toString()
    {
        return String.valueOf(this.code);
    }

    public int getValue()
    {
        return code;
    }
}
