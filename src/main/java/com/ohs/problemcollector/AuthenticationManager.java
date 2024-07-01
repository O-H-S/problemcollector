package com.ohs.problemcollector;

import com.ohs.problemcollector.api.GetAccountApi;
import com.ohs.problemcollector.api.LoginApi;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



@RequiredArgsConstructor
public class AuthenticationManager {
    GetAccountApi getAccountApi;
    LoginApi loginApi;
    public void init(){
        loginApi = ApiManager.getApi(LoginApi.class);
        getAccountApi = ApiManager.getApi(GetAccountApi.class);

    }


    public void login() throws Exception{
       if(getAccountApi.isFetchable())
           return;
        if(!loginApi.login()){
            throw new RuntimeException("계정 인증 실패");
        }
    }

}
