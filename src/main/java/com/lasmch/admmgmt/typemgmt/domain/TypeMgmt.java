package com.lasmch.admmgmt.typemgmt.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TypeMgmt{

    @JsonProperty("type_c")
    private String typeC;

    @JsonProperty("description")
    private String description;

}
