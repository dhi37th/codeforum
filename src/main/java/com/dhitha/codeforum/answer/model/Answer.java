package com.dhitha.codeforum.answer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Answer{
  private Long id;

  private Long questionId;

  @NotNull private String text;

  private Long upVote;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  private LocalDateTime updatedAt;

  private Long createdBy;

  private Long updatedBy;
}
