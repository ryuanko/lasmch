package com.lasmch.youtube.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Youtube {

    @JsonProperty("seq_id")
    private int seqId;

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

    @JsonProperty("youtube_dt")
    private String youtubeDt;

    @JsonProperty("update_dt")
    private String updateDt;

    @JsonProperty("update_name")
    private String updateName;

}
