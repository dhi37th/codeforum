package com.dhitha.codeforum.question.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Question{
  private Long id;

  @NotNull @NotEmpty private String tag;

  @NotNull @NotEmpty private String text;

  @NotNull @NotEmpty private String heading;

  private Long upVote;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  private LocalDateTime updatedAt;

  private Long createdBy;

  private Long updatedBy;
}
