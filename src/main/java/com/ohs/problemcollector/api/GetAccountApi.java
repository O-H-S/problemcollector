package com.ohs.problemcollector.api;

import com.ohs.problemcollector.CollectorConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetAccountApi extends ApiBase {

    public GetAccountApi(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected HttpRequest getRequest(Object... params)  {


        try {
            HttpRequest template  = HttpRequest.newBuilder()
                    .uri(new URI(CollectorConfig.get("api-base-url") +"/api/accounts/me"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            return template;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean isFetchable(){
        HttpResponse<String> accountResponse = send();
        if(isSuccessful(accountResponse.statusCode()))
            return true;
        return false;
    }

    private static boolean isSuccessful(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }
}
