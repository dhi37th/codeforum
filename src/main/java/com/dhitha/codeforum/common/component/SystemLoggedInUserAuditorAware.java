package com.dhitha.codeforum.common.component;

import com.dhitha.codeforum.user.model.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class SystemLoggedInUserAuditorAware implements AuditorAware<Long> {

  @Autowired private SessionInfo sessionInfo;

  @Override
  public Optional<Long> getCurrentAuditor() {
    return Optional.of(sessionInfo.getSessionUser().getId());
  }
}
