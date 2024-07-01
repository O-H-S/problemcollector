package com.ohs.problemcollector.api;

import com.ohs.problemcollector.CollectorConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginApi  extends ApiBase {

    public LoginApi(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected HttpRequest getRequest(Object... params)  {


        try {
            HttpRequest authenticationRequest = HttpRequest.newBuilder()
                    .uri(new URI(CollectorConfig.get("api-base-url") +"/api/accounts/login"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(
                                    "id="+CollectorConfig.get("api-id")+
                                            "&password="+CollectorConfig.get("api-password")
                            )
                    )
                    .build();
            return authenticationRequest;
        }catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Boolean login(){
        HttpResponse<String> accountResponse = send();
        if(isSuccessful(accountResponse.statusCode()))
            return true;
        return false;
    }

    private static boolean isSuccessful(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }

}
