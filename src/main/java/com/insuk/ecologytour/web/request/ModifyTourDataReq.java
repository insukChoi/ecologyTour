package com.insuk.ecologytour.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyTourDataReq {
    private Long id;

    private String programName;

    private String theme;

    private String regions;

    private String explain;

    private String detailExplain;
}
