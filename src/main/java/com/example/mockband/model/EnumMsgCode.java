package com.example.mockband.model;

public enum EnumMsgCode {
    SUCCESS(200),
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
}
