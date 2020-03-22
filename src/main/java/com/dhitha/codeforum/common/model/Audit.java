package com.dhitha.codeforum.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@JsonIgnoreProperties(
    value = {"createdAt", "updatedAt", "createdBy", "createdAt"},
    allowGetters = true)
@Data
public abstract class Audit implements Serializable {
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  private LocalDateTime updatedAt;

  private Long createdBy;

  private Long updatedBy;
}
