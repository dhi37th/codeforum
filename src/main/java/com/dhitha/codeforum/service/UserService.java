package com.dhitha.codeforum.service;

import com.dhitha.codeforum.model.User;
import org.springframework.stereotype.Service;

/**
 * User related services
 */
public interface UserService {

  /**
   * Find user by login id
   *
   * @param loginId login id
   * @return User
   */
  User findByLoginId(String loginId);
}
