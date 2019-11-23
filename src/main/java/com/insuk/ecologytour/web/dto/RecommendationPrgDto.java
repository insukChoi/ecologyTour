package com.insuk.ecologytour.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendationPrgDto implements Comparable<RecommendationPrgDto>{
    final static public int WEIGHT_ZERO = 0;

    private String programCode;
    private String programName;
    private int totalWeight;

    public void plusWeight(int weight){
        this.totalWeight = this.totalWeight + weight;
    }

    public void plusWeight(int[] weights){
        for (int weight : weights) {
            this.plusWeight(weight);
        }
    }

    @Override
    public int compareTo(RecommendationPrgDto o) {
        return Integer.compare(totalWeight, o.totalWeight);
    }


}
