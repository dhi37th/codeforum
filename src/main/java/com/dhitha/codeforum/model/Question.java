package com.dhitha.codeforum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "question")
@Data
@Builder
public class Question extends Audit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private long id;

  @Column @NotNull @NotEmpty @Lob private String tag;

  @Column @NotNull @NotEmpty @Lob private String issue;

  @Column private long upVote;

  @Column private short rowState;
}
