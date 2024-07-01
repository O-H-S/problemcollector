import com.ohs.problemcollector.ApiManager;
import com.ohs.problemcollector.api.GetAccountApi;
import com.ohs.problemcollector.api.GetCollectProgressApi;
import com.ohs.problemcollector.api.LoginApi;
import com.ohs.problemcollector.api.PutCollectProgressApi;
import com.ohs.problemcollector.dto.CollectProgressUpdateForm;
import com.ohs.problemcollector.dto.ProblemDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// 개발(로컬) 서버가 열려있고, 크롤러 계정이 있어야 테스트 가능.
public class ApiTest {


    @Test
    public void loginTest(){
        LoginApi api = ApiManager.getApi(LoginApi.class);
        Boolean result = api.login();
        assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void getAccountTest_notlogged(){
        GetAccountApi api = ApiManager.getApi(GetAccountApi.class);
        Boolean result = api.isFetchable();
        assertEquals(Boolean.FALSE, result);
    }

    @Test
    public void getAccountTest_logged(){

        Boolean login_result  =ApiManager.getApi(LoginApi.class).login();

        GetAccountApi api = ApiManager.getApi(GetAccountApi.class);
        Boolean result = api.isFetchable();
        assertEquals(Boolean.TRUE, login_result);
        assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void getCollectProgressTest(){
        Boolean login_result  = ApiManager.getApi(LoginApi.class).login();

        var result =  ApiManager.getApi(GetCollectProgressApi.class).getProgress("baekjoon", 0);

        assertEquals(Boolean.TRUE, login_result);
        assertNotNull(result);
    }


    // window 인덱스가 올바르지 않으면 예외 발생
    @Test
    public void putCollectProgressTest_failed(){
        Boolean login_result  = ApiManager.getApi(LoginApi.class).login();

        List<ProblemDto> testProblems = new LinkedList<>();
        for(int i = 5; i < 14; i++){
            ProblemDto problem = ProblemDto.builder()
                    .platform("test")
                    .platformId("id"+i)
                    .title("test title" + i)
                    //.difficulty("lv3")
                    .link("https://testPlatform.com1234")
                    .foundDate(LocalDateTime.of(1999, 11,1, 1,1))
                    .collectorVersion(0)
                    .build();
            testProblems.add(problem);
        }
        CollectProgressUpdateForm form = CollectProgressUpdateForm.builder()
                .targetWindow(1)
                //.applyIfExists(Boolean.FALSE)
                .problemList(testProblems)
                .build();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ApiManager.getApi(PutCollectProgressApi.class).update("test", 0, form);
        });

        assertEquals(Boolean.TRUE, login_result);
        assertNotNull(exception);
        //assertNotNull(result);
    }
}
