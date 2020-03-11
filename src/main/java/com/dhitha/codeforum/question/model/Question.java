package com.dhitha.codeforum.question.model;

import com.dhitha.codeforum.common.model.Audit;
import com.dhitha.codeforum.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "question")
@Data
@EqualsAndHashCode
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
