package com.ohs.problemcollector.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CollectProgressUpdateForm {
    Integer targetWindow;
    //Boolean applyIfExists;
    List<ProblemDto> problemList;
}
