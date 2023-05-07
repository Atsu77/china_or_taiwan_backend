package com.example.chinaornotbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chinaornotbackend.model.Score;
import com.example.chinaornotbackend.model.User;
import com.example.chinaornotbackend.model.repository.ScoreRepository;

@Service
public class ScoreService {
  @Autowired
  private ScoreRepository scoreRepository;

  public Long createScore(User user, int totalScore) {
    Score score = new Score();
    score.setUser(user);
    score.setTotalScore(totalScore);
    Score newScore = scoreRepository.save(score);
    return newScore.getId();
  }

  public List<Score> getScoreByUserId(Long userId) {
    List<Score> scores = scoreRepository.findByUserId(userId);
    return scores;
  }

}
