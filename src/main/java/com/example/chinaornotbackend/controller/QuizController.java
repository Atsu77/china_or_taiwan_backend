package com.example.chinaornotbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chinaornotbackend.model.Quiz;
import com.example.chinaornotbackend.model.repository.AnswerRepository;
import com.example.chinaornotbackend.response.QuizResponse;
import com.example.chinaornotbackend.service.QuizService;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
  @Autowired
  private QuizService quizService;

  @Autowired
  private AnswerRepository answerRepository;

  @GetMapping
  public ResponseEntity<List<QuizResponse>> getQuizzes(@RequestParam(defaultValue = "10") int limit) {
    List<QuizResponse> quizzes = quizService.getQuizzes(limit);
    return new ResponseEntity<>(quizzes, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<QuizResponse> createQuiz(@RequestBody QuizCreateRequest request) {
    Quiz quiz = new Quiz();
    quiz.setAnswer(answerRepository.findById(request.getAnswerId())
        .orElseThrow(() -> new RuntimeException("Cannot find answer with id: " + request.getAnswerId())));
    quiz.setQuestion(request.getQuestion());
    quiz.setImageUrl(request.getImageUrl());

    QuizResponse quizResponse = quizService.createQuiz(quiz);
    return new ResponseEntity<>(quizResponse, HttpStatus.CREATED);
  }

  @Data
  public static class QuizCreateRequest {
    private Long answerId;
    private String question;
    private String imageUrl;

    @JsonProperty("answer_id")
    public void setAnswerId(Long answerId) {
      this.answerId = answerId;
    }

    @JsonProperty("question")
    public void setQuestion(String question) {
      this.question = question;
    }

    @JsonProperty("image_url")
    public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
    }
  }

}
