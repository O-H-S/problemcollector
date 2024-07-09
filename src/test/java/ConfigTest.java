import com.ohs.problemcollector.CollectorConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigTest {
    @Test
    public void test(){

        String api_id =  CollectorConfig.get("api-id");
        String api_pass =  CollectorConfig.get("api-password");
        String any_env =  CollectorConfig.get("exist-env");
        String non_env =  CollectorConfig.get("non-exist-env");

        String value = System.getenv("EXIST-ENV");
        assertEquals("I AM HERE", value);

        assertEquals("testid", api_id);
        assertEquals("testpassword", api_pass);
        assertEquals(System.getenv("EXIST-ENV"), any_env);
        assertEquals(null, non_env);

    }

}
