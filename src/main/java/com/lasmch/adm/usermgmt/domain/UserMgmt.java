package com.lasmch.adm.usermgmt.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuppressWarnings("serial")
public class UserMgmt implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String id;

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

    @JsonProperty("address1")
    private String address1;

    @JsonProperty("address2")
    private String address2;

    @JsonProperty("birth")
    private String birth;

}
