package com.ohs.problemcollector.api;

import com.ohs.problemcollector.CollectorConfig;
import com.ohs.problemcollector.dto.CollectProgressUpdateForm;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class PutCollectProgressApi extends ApiBase{
    public PutCollectProgressApi(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected HttpRequest getRequest(Object... params) {
        String target = (String) params[0];
        int version = (Integer) params[1];
        CollectProgressUpdateForm form = (CollectProgressUpdateForm) params[2];

        try {
            URI uri = new URI(CollectorConfig.get("api-base-url") + "/api/collectProgresses?target=" + target + "&version=" + version);
            String jsonBody = getObjectMapper().writeValueAsString(form);
            HttpRequest template = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            return template;
        }catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }


    public void update(String target, int version, CollectProgressUpdateForm form){
        send(target, version, form);

    }
    public static class Request{

    }
    public static class Response{

    }
}
