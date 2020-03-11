package com.dhitha.codeforum.user.service;

import com.dhitha.codeforum.user.model.User;

/** User related services */
public interface UserService {

  /**
   * Find user by login id
   *
   * @param loginId login id
   * @return User
   */
  User findByLoginId(String loginId);

  /**
   * Add a user
   *
   * @param user user to add
   * @return User
   */
  User addUser(User user);

  /**
   * Delete a user
   *
   * @param userId id of user to delete
   */
  void deleteUser(Long userId);
}
