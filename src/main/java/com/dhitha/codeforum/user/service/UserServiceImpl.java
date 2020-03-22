package com.dhitha.codeforum.user.service;

import com.dhitha.codeforum.user.model.User;
import com.dhitha.codeforum.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired UserRepository userRepository;

  @Override
  public User findByLoginId(String loginId) {
    return userRepository.findByLoginId(loginId);
  }

  @Override
  public User addUser(User user) {
    return null;
  }

  @Override
  public void deleteUser(Long userId) {}
}
