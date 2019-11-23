package com.insuk.ecologytour.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationPrgRes {
    final static public RecommendationPrgRes EMPTY = new RecommendationPrgRes(null);

    private String program;
}
