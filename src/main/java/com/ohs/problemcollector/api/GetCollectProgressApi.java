package com.ohs.problemcollector.api;

import com.ohs.problemcollector.CollectorConfig;
import com.ohs.problemcollector.dto.CollectProgress;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetCollectProgressApi extends ApiBase{
    public GetCollectProgressApi(HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected HttpRequest getRequest(Object... params)  {

        String target = (String) params[0];
        int version = (Integer) params[1];

        try {
            URI uri = new URI(CollectorConfig.get("api-base-url") + "/api/collectProgresses?target=" + target + "&version=" + version);

            HttpRequest template = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            return template;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public CollectProgress getProgress(String target, Integer version){
        HttpResponse<String> response = send(target, version);
        try{
            CollectProgress progress = getObjectMapper().readValue(response.body(), CollectProgress.class);
            return progress;
        }catch (Exception e){
            return null;
        }
    }

    public static class Request{
        String target;
        Integer version;
    }
    public static class Response{
        CollectProgress progress;
    }
}
