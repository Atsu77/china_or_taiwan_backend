package com.example.chinaornotbackend.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ScoreRequest {
  private Long userId;
  private int totalScore;
  private int correctRate;

  @JsonProperty("user_id")
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @JsonProperty("total_score")
  public void setTotalScore(int totalScore) {
    this.totalScore = totalScore;
  }

  @JsonProperty("correct_rate")
  public void setCorrectRate(int correctRate) {
    this.correctRate = correctRate;
  }
}
