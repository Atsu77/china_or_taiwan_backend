package com.example.chinaornotbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chinaornotbackend.model.Score;
import com.example.chinaornotbackend.model.User;
import com.example.chinaornotbackend.model.repository.QuizRepository;
import com.example.chinaornotbackend.response.ScoreResponse;
import com.example.chinaornotbackend.service.QuizResultService;
import com.example.chinaornotbackend.service.QuizService;
import com.example.chinaornotbackend.service.ScoreService;
import com.example.chinaornotbackend.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {
  @Autowired
  private ScoreService scoreService;

  @Autowired
  private UserService userService;

  @Autowired
  private QuizResultService quizResultService;

  @PostMapping
  public ResponseEntity<Long> createScore(@RequestBody ScoreRequest scoreRequest) {
    User user = userService.findOrCreateUser(scoreRequest.getUserName());
    List<QuizResultRequest> quizResults = scoreRequest.getQuizResults();
    quizResultService.processQuizResults(quizResults);
    int totalScore = (int) quizResults.stream().filter(result -> result.isCorrect()).count();
    Long scoreId = scoreService.createScore(user, totalScore);
    quizResults.forEach(result -> {
      quizResultService.updateCorrectRate(result.getQuizId());
    });

    return new ResponseEntity<>(scoreId, HttpStatus.CREATED);
  }

  @GetMapping("/{user_id}")
  public ResponseEntity<ScoreResponse> getScoreByUserId(@PathVariable("user_id") Long userId) {
    String userName = userService.getUserByid(userId).getName();
    List<Score> scores = scoreService.getScoreByUserId(userId);
    if (scores.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    ScoreResponse scoreResponse = new ScoreResponse(userName, scores);

    return new ResponseEntity<>(scoreResponse, HttpStatus.OK);
  }

  @Data
  public static class ScoreRequest {
    private String userName;
    private List<QuizResultRequest> quizResults;

    @JsonProperty("user_name")
    public void setUserName(String userName) {
      this.userName = userName;
    }

    @JsonProperty("quiz_results")
    public void setQuizResult(List<QuizResultRequest> quizResults) {
      this.quizResults = quizResults;
    }
  }

  @Data
  public static class QuizResultRequest {
    @Autowired
    QuizRepository quizRepository;

    private Long quizId;
    private boolean isCorrect;

    @JsonProperty("quiz_id")
    public void setQuiz(Long quizId) {
      this.quizId = quizId;
    }

    @JsonProperty("is_correct")
    public void setIsCorrect(boolean isCorrect) {
      this.isCorrect = isCorrect;
    }
  }

}
