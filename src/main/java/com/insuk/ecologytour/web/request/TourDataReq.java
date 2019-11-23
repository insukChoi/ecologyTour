package com.insuk.ecologytour.web.request;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class TourDataReq {
    @CsvBindByPosition(position = 1)
    private String programName;

    @CsvBindByPosition(position = 2)
    private String theme;

    @CsvBindByPosition(position = 3)
    private String regions;

    @CsvBindByPosition(position = 4)
    private String explain;

    @CsvBindByPosition(position = 5)
    private String detailExplain;
}
