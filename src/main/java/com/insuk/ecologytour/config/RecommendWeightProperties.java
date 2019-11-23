package com.insuk.ecologytour.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "api.weight")
@Validated
public class RecommendWeightProperties {
    @Getter
    @Setter
    @NotNull
    private int theme;

    @Getter
    @Setter
    @NotNull
    private int explain;

    @Getter
    @Setter
    @NotNull
    private int detailExplain;
}
