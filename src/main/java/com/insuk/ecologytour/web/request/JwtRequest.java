package com.insuk.ecologytour.web.request;

import lombok.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {
    private String username;
    private String password;
}
