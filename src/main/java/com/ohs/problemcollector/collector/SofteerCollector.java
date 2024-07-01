package com.ohs.problemcollector.collector;

import com.ohs.problemcollector.dto.ProblemDto;
import com.ohs.problemcollector.util.QueryParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SofteerCollector extends ProblemCollector {
    private int cachedLastPage;
    public SofteerCollector() {
        super();

        version = 1;
        platform = "softeer";
    }

    @Override
    protected void validatePage() {
        cachedLastPage = getWindowsCount();
        //getProblemsFromWindow(0);
        //throw new RuntimeException("abc");
    }

    @Override
    protected int getWindowSize() {

        return 9;
    }

    @Override
    public int getWindowsCount() {

        loadUrl("https://softeer.ai/practice/list", 10000);

        WebElement paginationElement = driver.findElement(By.cssSelector("div.con__bottom > div.pagination > a.pagination__link--last"));
        String lastUrl = paginationElement.getAttribute("href");

        URL url = null;
        try{
            url= new URL(lastUrl);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        var urlparams = QueryParser.parse(url.getQuery());
        String pageValue = urlparams.get("page");

        int counts = Integer.parseInt(pageValue) + 1;

        if(counts <= 5)
            throw new RuntimeException("ProgrammersCollector.getWindowsCount : 유효하지 않은 숫자입니다.");
        return counts;
    }

    @Override
    public List<ProblemDto> getProblemsFromWindow(int windowId) {

        loadUrl("https://softeer.ai/practice/list?page=%d".formatted(cachedLastPage-windowId-1), 10000);

        WebElement itemContainer = driver.findElement(By.cssSelector("#practice > div.con__body > div.item-container.style-list"));

        List<ProblemDto> problemList = new ArrayList<>();

        List<WebElement> rows = itemContainer.findElements(By.cssSelector("div.item-list"));
        for (int i = rows.size() - 1; i >= 0; i--) {
            WebElement row = rows.get(i);

            String title =  row.findElement(By.cssSelector("div.item-base > a> p")).getText();
            String link = row.findElement(By.cssSelector("div.item-base > a")).getAttribute("href");
            String id = link.substring(link.lastIndexOf('/')+1);

            String difficulty = row.findElement(By.cssSelector("div.item-base > a > div.item-status > i.badge-level")).getAttribute("class").split(" ")[1];

            ProblemDto problem = ProblemDto.builder()
                    .platform(platform)
                    .platformId(id)
                    .title(title)
                    .difficulty(difficulty)
                    .foundDate(LocalDateTime.now())
                    .link(link)
                    .collectorVersion(version)
                    .build();
            problemList.add(problem);

        }

        return problemList;
    }


}