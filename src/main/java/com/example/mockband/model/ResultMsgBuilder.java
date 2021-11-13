package com.example.mockband.model;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResultMsgBuilder {
    public static <T> ResultMsg<T> success(T data, HttpServletResponse response){
        ResultMsg<T> result = new ResultMsg<>(EnumMsgCode.SUCCESS,data);
        try{
            if(response!=null){
                response.getWriter().write(new Gson().toJson(result));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public static <T> ResultMsg<T> unknownError(T data)
    {
        ResultMsg<T> result = new ResultMsg<>(EnumMsgCode.UNKONWN_ERROR,data);
        return result;
    }

    public static <T> ResultMsg<T> commonError(EnumMsgCode code,T data, HttpServletResponse response)
    {
        ResultMsg<T> result = new ResultMsg<>(code,data);
        try{
            if(response!=null){
                response.getWriter().write(new Gson().toJson(result));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
