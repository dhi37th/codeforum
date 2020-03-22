package com.dhitha.codeforum.user.model;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long id;

  @NotNull @NotEmpty private String name;

  @NotNull @NotEmpty private String loginId;

  @NotNull @NotEmpty private String password;

  private Boolean isAdmin;
}
