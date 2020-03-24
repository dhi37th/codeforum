package com.dhitha.codeforum.common.component;

import com.dhitha.codeforum.user.model.User;
import com.dhitha.codeforum.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessionUtil {
  @Autowired private UserService userService;
  private User user;

  public User getSessionUser() {
    if (user == null) {
       user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    return user;
  }
}
