package com.dhitha.codeforum.answer.model;

import com.dhitha.codeforum.common.model.Audit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Answer extends Audit {
  private Long id;

  @JsonIgnore
  private Long questionId;

  @NotNull private String text;

  private Long upVote;
}
