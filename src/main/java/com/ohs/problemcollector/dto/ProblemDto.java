package com.ohs.problemcollector.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProblemDto {
    private String platform;

    private String platformId;

    private String title;

    private String difficulty;

    private String link;

    private LocalDateTime foundDate;

    private Integer collectorVersion;
}
