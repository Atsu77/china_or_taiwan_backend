package com.example.chinaornotbackend.exception;

public class UserNotFoundExeption extends RuntimeException {
  public UserNotFoundExeption(String message) {
    super(message);
  }
}
