package com.dhitha.codeforum.question.model;

import com.dhitha.codeforum.common.model.Audit;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Question extends Audit {
  private Long id;

  @NotNull @NotEmpty private String tag;

  @NotNull @NotEmpty private String text;

  @NotNull @NotEmpty private String heading;

  private Long upVote;
}
