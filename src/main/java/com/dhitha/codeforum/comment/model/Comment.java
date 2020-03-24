package com.dhitha.codeforum.comment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Comment{
  @JsonView(QuestionView.class)
  private Long id;

  @JsonView(QuestionView.class)
  private Long questionId;

  private Long answerId;

  @JsonView(QuestionView.class)
  @NotNull private String text;

  @JsonView(QuestionView.class)
  private Long upVote;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  @JsonView(QuestionView.class)
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  @JsonView(QuestionView.class)
  private LocalDateTime updatedAt;

  @JsonView(QuestionView.class)
  private Long createdBy;

  @JsonView(QuestionView.class)
  private Long updatedBy;


  public interface QuestionView{
  //marker interface to not show answerid attribute for question comments
  }
}
