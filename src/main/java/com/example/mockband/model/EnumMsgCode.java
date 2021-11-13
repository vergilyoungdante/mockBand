package com.example.mockband.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public enum EnumMsgCode implements Serializable {
    @SerializedName("200")
    SUCCESS(200),
    @SerializedName("-1")
    UNKONWN_ERROR(-1),
    @SerializedName("-2")
    PASSWORD_INCONSISTENT_ERROR,
    @SerializedName("-3")
    NO_PHOTO_ERROR,
    @SerializedName("-4")
    REPEAT_ACCOUNT_ERROR,


    ;
    private int code;

    EnumMsgCode(int code) {
        this.code = code;
    }

    EnumMsgCode() {

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
