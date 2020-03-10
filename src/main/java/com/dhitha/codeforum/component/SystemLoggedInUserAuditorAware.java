package com.dhitha.codeforum.component;

import com.dhitha.codeforum.model.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class SystemLoggedInUserAuditorAware implements AuditorAware<User> {

  @Autowired private SessionInfo sessionInfo;

  @Override
  public Optional<User> getCurrentAuditor() {
    return Optional.of(sessionInfo.getSessionUser());
  }
}
