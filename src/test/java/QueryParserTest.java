import com.ohs.problemcollector.util.QueryParser;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryParserTest {
    @Test
    public void test(){
        URL url = null;
        try{
            url= new URL("https://school.programmers.co.kr/learn/challenges?order=recent&page=1000");
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        var result = QueryParser.parse(url.getQuery());
        assertEquals("recent", result.get("order"));
        assertEquals("1000", result.get("page"));

    }

    // 인코딩된 url도 올바르게 파싱
    @Test
    public void testParseEncodedQuery() {
        URL url = null;
        try {
            url = new URL("https://example.com/search?q=%EB%B0%98%EB%A0%A4%EA%B2%AC&lang=ko");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> result = QueryParser.parse(url.getQuery());
        assertEquals("반려견", result.get("q"));
        assertEquals("ko", result.get("lang"));
    }
}
