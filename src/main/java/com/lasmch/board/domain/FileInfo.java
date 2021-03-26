package com.lasmch.board.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

@Data
@NoArgsConstructor
public class FileInfo {

  @JsonProperty("seq_id")
  private String seq_id;

  @JsonProperty("type_c")
  private String typeC;

  @JsonProperty("file_name")
  private String fileName;

  @JsonProperty("file_s3_key")
  private String fileS3Key;

  @JsonProperty("size_n")
  private Long sizeN;

  @JsonProperty("file_s3_url")
  private URL fileS3Url;


  @JsonProperty("delfile")
  private List<String> delfile;

  @JsonProperty("upfile")
  private List<MultipartFile> upfile;
}
