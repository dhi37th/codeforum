package com.dhitha.codeforum.question.model;

import com.dhitha.codeforum.common.model.Audit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Question extends Audit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column @NotNull @NotEmpty @Lob private String tag;

  @Column @NotNull @NotEmpty private String issue;

  @Column @NotNull @NotEmpty @Lob private String heading;

  @Column private Long upVote;
}
