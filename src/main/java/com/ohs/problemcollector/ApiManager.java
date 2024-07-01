package com.ohs.problemcollector;

import com.ohs.problemcollector.api.*;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiManager {
    private static ApiManager instance = null;
    // --------------------------------------------

    private Map<Class<? extends ApiBase>, ApiBase> apiMap = new HashMap<>();

    public HttpClient client; // private로 바꾸기
    CookieManager cookieManager;
    private ApiManager() {
        // HttpClient는 불변 객체로, 한 번 생성하면 변경할 수 없습니다. 따라서 다양한 요청에 재사용할 수 있습니다. (쓰레드 safe)
        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        client = HttpClient.newBuilder()
                .cookieHandler(cookieManager)
                .build();

        // api 객체 등록
        addApi(LoginApi.class, new LoginApi(client));
        addApi(GetAccountApi.class, new GetAccountApi(client));
        addApi(GetCollectProgressApi.class, new GetCollectProgressApi(client));
        addApi(PutCollectProgressApi.class, new PutCollectProgressApi(client));
    }

    public static synchronized ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();

        }
        return instance;
    }

    public static <T extends ApiBase> T getApi(Class<T> apiClass) {
        ApiBase api = getInstance().apiMap.get(apiClass);
        if (api == null) {
            throw new RuntimeException("존재하지 않는 API 입니다.");
        }
        List<HttpCookie> cookies = getInstance().cookieManager.getCookieStore().getCookies();
        return apiClass.cast(api);
    }

    public void addApi(Class<? extends ApiBase> apiClass, ApiBase apiInstance) {
        apiMap.put(apiClass, apiInstance);
    }

}
