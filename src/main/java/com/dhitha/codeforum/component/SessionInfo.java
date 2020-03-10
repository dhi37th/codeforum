package com.dhitha.codeforum.component;

import com.dhitha.codeforum.model.User;
import com.dhitha.codeforum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessionInfo {
  @Autowired private UserService userService;
  private User user;

  public User getSessionUser() {
    if (user == null) {
      user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    return user;
  }
}
