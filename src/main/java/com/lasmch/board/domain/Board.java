package com.lasmch.board.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Board {

    @JsonProperty("seq_id")
    private String seqId;

    @JsonProperty("type_c")
    private String typeC;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("hit")
    private String hit;

    @JsonProperty("etc")
    private String etc;

    @JsonProperty("create_dt")
    private String createDt;

    @JsonProperty("create_nm")
    private String createNm;

    @JsonProperty("update_dt")
    private String updateDt;

    @JsonProperty("update_nm")
    private String updateNm;

}
