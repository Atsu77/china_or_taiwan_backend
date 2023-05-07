package com.example.chinaornotbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chinaornotbackend.model.QuizCategory;
import com.example.chinaornotbackend.model.repository.QuizCategoryRepository;

@Service
public class QuizCategoryService {
  @Autowired
  private QuizCategoryRepository quizCategoryRepository;

  public List<QuizCategory> getQuizCategories() {
    List<QuizCategory> quizCategories = quizCategoryRepository.findAll();
    return quizCategories;
  }
}
