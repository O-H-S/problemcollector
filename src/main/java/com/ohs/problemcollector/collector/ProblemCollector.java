package com.ohs.problemcollector.collector;

import com.ohs.problemcollector.ProblemCollectManager;
import com.ohs.problemcollector.dto.CollectProgress;
import com.ohs.problemcollector.dto.ProblemDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

import java.util.List;

@RequiredArgsConstructor
public abstract class ProblemCollector {

    @Getter
    @Setter
    protected WebDriver driver;


    ProblemCollectManager manager;

    public int version = 0;
    public String platform;

    public void start(ProblemCollectManager manager, WebDriver driver){
        this.manager = manager;
        this.driver = driver;

        validatePage();
        CollectProgress lastProgress = manager.getProgress(this);
        int startWindowId = lastProgress.getLastWindow() + 1;
        int maxId = getWindowsCount()-1;
        if(maxId < startWindowId)
            startWindowId = maxId;
        for(int i = startWindowId; i <= maxId; i++){
            System.out.println(platform+ ") global collecting : " + i + "/" + maxId);
            List<ProblemDto> problems = getProblemsFromWindow(i);
            lastProgress.setLastWindow(i);
            manager.putProgress(problems, lastProgress);
        }
        System.out.println(platform+ ") global collecting : FINISHED");

    }

    protected void sleep(long time) {
        try {
            Thread.sleep(time);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    protected void loadUrl(String url, long waitTime){
        if(!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
            sleep(waitTime);
        }
    }

    protected abstract void validatePage(); // 대상 사이트가 기대하는 페이지 형식을 가지는지 검증함.
    protected abstract int getWindowSize();
    public abstract int getWindowsCount();
    public abstract List<ProblemDto> getProblemsFromWindow(int windowId);

    // global 탐색을 끝내고 난 뒤, 지속적으로 최신 문제들을 가져오는 메소드
    // protected List<Problem> getRecentProblems(){ return null;}
}
