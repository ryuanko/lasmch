package com.lasmch.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    @JsonProperty("id")
    private int id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("pwd")
    private String pwd;

    @JsonProperty("auth")
    private String auth;

    @JsonProperty("kor_name")
    private String korName;

    @JsonProperty("eng_name")
    private String engName;

}
