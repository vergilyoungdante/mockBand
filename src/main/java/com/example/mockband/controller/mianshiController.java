package com.example.mockband.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class mianshiController {

    @GetMapping("/healthcheck")
    public void healthcheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String format = request.getParameter("format");
        if(StringUtils.isBlank(format)){
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status","OK");
        if("full".equals(format)){
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            jsonObject.addProperty("currentTime",pattern.format(localDateTime));
            response.getWriter().write(new Gson().toJson(jsonObject));
        }
        if("short".equals(format)){
            response.getWriter().write(new Gson().toJson(jsonObject));
        }
    }
}
