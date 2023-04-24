package com.example.chinaornotbackend.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QuizResponse {
  private Long id;
  private String question;
  private String answer;
  @JsonProperty("image_url")
  private String imageUrl;

  public QuizResponse(Long id, String question, String answer, String imageUrl) {
    this.id = id;
    this.question = question;
    this.answer = answer;
    this.imageUrl = imageUrl;
  }
}
