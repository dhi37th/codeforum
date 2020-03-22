package com.dhitha.codeforum.user.repository;

import com.dhitha.codeforum.user.model.User;
import org.springframework.stereotype.Repository;

public interface UserRepository {

  /**
   * Find user by login id
   * @param loginId login id of the user
   * @return User
   */
  User findByLoginId(String loginId);

}
