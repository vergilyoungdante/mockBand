package com.example.mockband.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.micrometer.core.instrument.util.StringUtils;

import org.springframework.web.bind.annotation.*;

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
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("status","OK");
        if("full".equals(format)){
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            objectNode.put("currentTime",pattern.format(localDateTime));
            response.getWriter().write(objectNode.toString());
        }
        if("short".equals(format)){
            response.getWriter().write(objectNode.toString());
        }
        response.setStatus(400);
    }
    @PutMapping("/healthcheck")
    public void healthcheckPut(HttpServletResponse response){
        response.setStatus(405);
        return;
    }
    @PostMapping("/healthcheck")
    public void healthcheckPost(HttpServletResponse response){
        response.setStatus(405);
        return;
    }
    @DeleteMapping("/healthcheck")
    public void healthcheckDelete(HttpServletResponse response){
        response.setStatus(405);
        return;
    }

}
