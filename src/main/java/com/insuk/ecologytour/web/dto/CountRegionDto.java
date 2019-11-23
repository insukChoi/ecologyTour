package com.insuk.ecologytour.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountRegionDto {
    private String region;
    private Long count;
}
