package com.insuk.ecologytour.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class JwtResponse implements Serializable {

    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("refresh_token")
    private final String refreshToken;
}
