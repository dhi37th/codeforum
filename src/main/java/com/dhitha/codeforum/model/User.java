package com.dhitha.codeforum.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column @NotNull @NotEmpty private String name;

  @Column @NotNull @NotEmpty private String loginId;

  @Column @NotNull @NotEmpty private String password;

  @Column private boolean isAdmin;
}
