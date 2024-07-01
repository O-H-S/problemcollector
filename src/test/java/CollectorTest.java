import com.ohs.problemcollector.ProblemCollectManager;
import com.ohs.problemcollector.collector.BaekjoonCollector;
import com.ohs.problemcollector.dto.CollectProgress;
import com.ohs.problemcollector.dto.ProblemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


// 로컬에 Selenium Grid 가 구동 중이어야, 테스트 가능.
@ExtendWith(MockitoExtension.class) // 이 어노테이션을 사용하면 테스트 클래스에서 Mockito의 어노테이션(@Mock, @Spy, @InjectMocks)을 간편하게 사용할 수 있으며, Mockito가 이러한 어노테이션을 처리하고 초기화해줍니다.
public class CollectorTest {
    @Spy
    ProblemCollectManager manager;

    @BeforeEach
    public void setUp() {
        // MockitoAnnotations.openMocks(this); // Initialize mocks and spies before each test, @ExtendWith(MockitoExtension.class) 있으면 주석 처리 가능

        manager = spy(new ProblemCollectManager()); // Explicitly creating the spy object

        //doNothing().when(manager).modifyProblems(any(List.class), any(CollectProgress.class), any(BiConsumer.class));
    }


    @Test
    public void baejoonCollectorTest(){
        WebDriver driver = manager.createDriver();

        BaekjoonCollector collector = new BaekjoonCollector();
        collector.setDriver(driver);


        List<ProblemDto> resultProblems = collector.getProblemsFromWindow(1);

        int resultCount =  collector.getWindowsCount();
        assertTrue(resultCount > 0); //
        assertTrue(resultProblems != null && !resultProblems.isEmpty());


    }
}
