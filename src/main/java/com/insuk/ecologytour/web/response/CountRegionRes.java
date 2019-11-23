package com.insuk.ecologytour.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insuk.ecologytour.web.dto.CountRegionDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CountRegionRes {
    final static public CountRegionRes EMPTY = new CountRegionRes();

    private String keyword;

    @JsonProperty("programs")
    private List<CountRegionDto> programs = new ArrayList<>();
}
