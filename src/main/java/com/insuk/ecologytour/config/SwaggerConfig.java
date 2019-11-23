package com.insuk.ecologytour.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder().name(HttpHeaders.AUTHORIZATION)
                .defaultValue("Bearer ")
                .description("JWT Token")
                .parameterType("header")
                .required(false).modelRef(new ModelRef("string")).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(parameters)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Ecology Tour API DOC")
                .description("ecologyTour Swagger2")
                .build();
    }
}
