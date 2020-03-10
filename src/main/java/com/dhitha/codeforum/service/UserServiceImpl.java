package com.dhitha.codeforum.service;

import com.dhitha.codeforum.model.User;
import com.dhitha.codeforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Override
  public User findByLoginId(String loginId) {
    return userRepository.findByLoginId(loginId);
  }
}
