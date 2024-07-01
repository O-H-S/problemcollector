package com.ohs.problemcollector.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class QueryParser {
    public static Map<String, String> parse(String query){

        /*URL 인코딩된 문자열을 디코딩하는 역할을 합니다.
        URL 인코딩은 URL에 포함될 수 없는 또는 특수한 의미를 가지는 문자를 안전하게 전달하기 위해 사용하는 인코딩 방식입니다.
        예를 들어, 공백 문자는 %20으로 인코딩되고, & 문자는 %26으로 인코딩됩니다.*/

        Map<String, String> params = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
            String value = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
            params.put(key, value);
        }
        return params;
    }
}
