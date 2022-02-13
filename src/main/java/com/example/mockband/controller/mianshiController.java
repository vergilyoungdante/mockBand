package com.example.mockband.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class mianshiController {

    @GetMapping("/healthcheck")
    @ResponseBody()
    public ObjectNode healthcheck(String format){
        if(format==null){
            throw new ArgException();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("status","OK");
        if(format.equals("full")){
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            objectNode.put("currentTime",pattern.format(localDateTime));
            return objectNode;

        }
        if(format.equals("short")){
            return objectNode;

        }
        throw new ArgException();
    }
    @PutMapping("/healthcheck")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void healthcheckPut(){
        throw new OtherException();
    }
    @PostMapping("/healthcheck")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void healthcheckPost(){
        throw new OtherException();
    }
    @DeleteMapping("/healthcheck")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void healthcheckDelete(){
        throw new OtherException();
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    class OtherException extends RuntimeException{
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    class ArgException extends RuntimeException{
    }

}

