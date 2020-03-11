package com.dhitha.codeforum.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ApiError implements Serializable {

  private static final long serialVersionUID = 1L;

  private HttpStatus status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  private String message;

  private List<String> debugMessage;

  public ApiError(HttpStatus status, String message, List<String> ex) {
    this();
    this.status = status;
    this.message = message;
    this.debugMessage = ex;
  }

}