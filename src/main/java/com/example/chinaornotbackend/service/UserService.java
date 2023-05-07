package com.example.chinaornotbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chinaornotbackend.exception.UserNotFoundExeption;
import com.example.chinaornotbackend.model.User;
import com.example.chinaornotbackend.model.repository.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public Long createUser(User user) {
    User newUser = userRepository.save(user);
    return newUser.getId();
  }

  public User getUserByid(Long userId) {
    Optional<User> user = userRepository.findById(userId);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UserNotFoundExeption("ユーザーが見つかりませんでした。");
    }
  }

  public User findOrCreateUser(String userName) {
    User user = userRepository.findByName(userName);
    if (user == null) {
      user = new User();
      user.setName(userName);
      createUser(user);
    }
    return user;
  }
}
