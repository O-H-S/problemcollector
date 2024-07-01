package com.ohs.problemcollector.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
public abstract class ApiBase {
    static ObjectMapper objectMapper;
    protected ObjectMapper getObjectMapper(){
        if(objectMapper == null){
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
        }
        return objectMapper;
    }


    final HttpClient httpClient;
    HttpRequest httpRequest;

    protected abstract HttpRequest getRequest(Object... params);
    protected HttpResponse<String> send(Object... params){

        try {
            HttpResponse<String> response = httpClient.send(getRequest(params), HttpResponse.BodyHandlers.ofString());
            return response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
