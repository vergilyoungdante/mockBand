package com.example.mockband.model;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResultMsgBuilder {
    public static <T> ResultMsg<T> success(T data, HttpServletResponse response){
        ResultMsg<T> result = new ResultMsg<>(EnumMsgCode.SUCCESS,data);
        buildResponseHeader(  response);
        try {
            response.getWriter().write(new Gson().toJson(result));
        } catch (IOException e) {
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
        buildResponseHeader( response);
        try {
            response.getWriter().write(new Gson().toJson(result));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static HttpServletResponse buildResponseHeader(HttpServletResponse response)
    {
        try{
            if(response!=null){
                response.setContentType("application/json;charset=UTF-8");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
