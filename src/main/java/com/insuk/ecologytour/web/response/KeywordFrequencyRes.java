package com.insuk.ecologytour.web.response;

import lombok.Data;

@Data
public class KeywordFrequencyRes {
    final static public KeywordFrequencyRes EMPTY = new KeywordFrequencyRes();

    private String keyword;
    private Long count;
}
