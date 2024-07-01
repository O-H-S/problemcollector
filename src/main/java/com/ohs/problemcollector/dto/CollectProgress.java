package com.ohs.problemcollector.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CollectProgress {
    String platform;
    Integer lastWindow;
    Integer collectorVersion;
    LocalDateTime startDate;
}
