package com.ohs.problemcollector;

import com.ohs.problemcollector.api.GetAccountApi;
import com.ohs.problemcollector.api.LoginApi;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.*;
import java.net.http.HttpClient;

public class Main {
    public static void main(String[] args) throws Exception {



        AuthenticationManager authenticationManager = new AuthenticationManager();
        authenticationManager.init();

        ProblemCollectManager problemCollectManager = new ProblemCollectManager();
        problemCollectManager.init();

        while(true) {
            try {
                System.out.println("API 서버에 인증을 시도하는 중 입니다.");
                authenticationManager.login();
                System.out.println("크롤러 매니저가 동작하는 중 입니다.");
                problemCollectManager.start();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("MAIN : 에러 발생, 1분 후 시스템이 재시작 됩니다.");
                Thread.sleep(1000 * 60);

            }
        }
    }
}