package com.lasmch.youtube.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Youtube {

    @JsonProperty("id")
    private int id;

    @JsonProperty("read_cnt")
    private int readCnt;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("youtube_type")
    private String youtubeType;

    @JsonProperty("youtube_title")
    private String youtubeTitle;

    @JsonProperty("youtube_url")
    private String youtubeUrl;

    @JsonProperty("update_date")
    private String updateDate;

    @JsonProperty("update_name")
    private String updateName;

}
