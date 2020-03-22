package com.dhitha.codeforum.comment.model;

import com.dhitha.codeforum.answer.model.Answer;
import com.dhitha.codeforum.common.model.Audit;
import com.dhitha.codeforum.question.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Comment extends Audit {
  private Long id;

  @JsonIgnore
  private Question question;

  @JsonIgnore
  private Answer answer;

  @NotNull private String text;

  private Long upVote;
}
