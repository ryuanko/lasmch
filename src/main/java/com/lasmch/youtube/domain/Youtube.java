package com.lasmch.youtube.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Youtube {

    @JsonProperty("id")
    private String id;

    @JsonProperty("youtube_type")
    private String youtubeType;

    @JsonProperty("youtube_name")
    private String youtubeName;

    @JsonProperty("youtube_url")
    private String youtubeUrl;

    @JsonProperty("update_date")
    private String updateDate;

    @JsonProperty("update_name")
    private String updateName;

}
