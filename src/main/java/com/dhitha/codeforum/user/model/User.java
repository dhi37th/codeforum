package com.dhitha.codeforum.user.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User implements UserDetails, Serializable {
  private static final long serialVersionUID = 1L;

  private Long id;

  @NotNull @NotEmpty private String name;

  @NotNull @NotEmpty private String loginId;

  @NotNull @NotEmpty private String password;

  private Boolean isLocked;

  private Boolean isAdmin;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> grantList = new ArrayList<>();
    if (Boolean.TRUE.equals(isAdmin)) {
      GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
      GrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_USER");
      grantList.add(adminAuthority);
      grantList.add(userAuthority);
    } else {
      GrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_USER");
      grantList.add(userAuthority);
    }
    return grantList;
  }

  @Override
  public String getUsername() {
    return loginId;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !isLocked;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !isLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !isLocked;
  }

  @Override
  public boolean isEnabled() {
    return !isLocked;
  }
}
