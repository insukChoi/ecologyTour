package com.insuk.ecologytour.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insuk.ecologytour.web.dto.ProgramDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProgramRes {
    final static public ProgramRes EMPTY = new ProgramRes();

    @JsonProperty("region")
    private String regionCode;

    @JsonProperty("programs")
    private List<ProgramDto> programList = new ArrayList<>();
}
