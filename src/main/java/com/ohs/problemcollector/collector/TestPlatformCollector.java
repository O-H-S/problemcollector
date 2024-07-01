package com.ohs.problemcollector.collector;

import com.ohs.problemcollector.dto.ProblemDto;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class TestPlatformCollector extends ProblemCollector {
    public TestPlatformCollector() {
        super();

        version = 2;
        platform = "test2";
    }
    @Override
    protected void validatePage() {

    }

    @Override
    protected int getWindowSize() {
        return 5;
    }

    @Override
    public int getWindowsCount() {
        return 10;
    }

    @Override
    public List<ProblemDto> getProblemsFromWindow(int windowId) {
        sleep(1000);

        List<ProblemDto> testProblems = new LinkedList<>();
        int count = windowId == getWindowsCount()-1 ? 4: getWindowSize();
        for(int i = 0; i < count; i++){
            String pID = "id" + (getWindowSize() *windowId + i);

            ProblemDto problem = ProblemDto.builder()
                    .platform(platform)
                    .platformId(pID)
                    .title("title of " + pID)
                    .difficulty("lv1")
                    .foundDate(LocalDateTime.of(2011, 11,1, 1,1).plusMinutes((long) getWindowSize() *windowId + i))
                    .link("https://testPlatform.com")
                    .collectorVersion(version)
                    .build();
            testProblems.add(problem);
        }
        return testProblems;
    }
}
