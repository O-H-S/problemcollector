package com.ohs.problemcollector;

import com.ohs.problemcollector.api.GetCollectProgressApi;
import com.ohs.problemcollector.api.PutCollectProgressApi;
import com.ohs.problemcollector.collector.*;
import com.ohs.problemcollector.dto.CollectProgress;
import com.ohs.problemcollector.dto.CollectProgressUpdateForm;
import com.ohs.problemcollector.dto.ProblemDto;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class ProblemCollectManager {


    GetCollectProgressApi getCollectProgressApi;
    PutCollectProgressApi putCollectProgressApi;
    List<ProblemCollector> collectorList = new ArrayList<>();
    public void init(){
        getCollectProgressApi = ApiManager.getApi(GetCollectProgressApi.class);
        putCollectProgressApi = ApiManager.getApi(PutCollectProgressApi.class);




        //System.setProperty("webdriver.chrome.whitelistedIps", "");

        //collectorList.add(new TestPlatformCollector());

        collectorList.add(new BaekjoonCollector());
        collectorList.add(new ProgrammersCollector());
        collectorList.add(new SofteerCollector());
        collectorList.add(new SWEACollector());


    }

    public WebDriver createDriver(){
        // Selenium Grid의 URL (Docker 컨테이너의 Selenium Grid URL)
        // selenium grid와 동일한 네트워크를 사용해야함.
        String seleniumGridURL = CollectorConfig.get("selenium-grid-adr");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");

        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL(seleniumGridURL), options);
        }catch (Exception e) {
            throw new RuntimeException("driver 생성에 실패하였습니다.");
        }
        return driver;
    }


    public CollectProgress getProgress(ProblemCollector collector){
        return getProgress(collector.platform, collector.version);
    }

    public CollectProgress getProgress(String target, int version){
        CollectProgress result = getCollectProgressApi.getProgress(target,version);
        if(result == null)
        {
            result = new CollectProgress();
            result.setPlatform(target);
            result.setCollectorVersion(version);
            result.setLastWindow(-1);
            result.setStartDate(LocalDateTime.now());
        }
        return result;
    }

    public void putProgress(List<ProblemDto> problems, CollectProgress progress){

        CollectProgressUpdateForm form = CollectProgressUpdateForm.builder()
                .targetWindow(progress.getLastWindow())
                .problemList(problems)
                .build();


        putCollectProgressApi.update(progress.getPlatform(), progress.getCollectorVersion(), form);

    }


    public void start(){
        WebDriver reuseDriver = createDriver();
        try {
            while (true) {
                for (ProblemCollector collector : collectorList) {
                    try {
                        collector.start(this, reuseDriver);
                    } catch(Exception e){
                        System.out.println("크롤링 예외 inner : " + e.toString());
                        e.printStackTrace();
                    }
                }
                try {
                    reuseDriver.close();
                }catch (Exception e)
                {
                    System.out.println("driver close 예외 : " + e.toString());
                    e.printStackTrace();
                }finally {
                    reuseDriver.quit();
                }

                System.out.println("모든 Collector 작업이 완료 되었습니다. 10분 재시작 됩니다.");
                Thread.sleep(1000 * 60 * 10);
                //.sleep(5000);

                reuseDriver = createDriver();
            }
        }catch(Exception e){
            System.out.println("크롤링 루프 예외");
            e.printStackTrace();
        }finally {
            reuseDriver.quit();
        }
    }
}
