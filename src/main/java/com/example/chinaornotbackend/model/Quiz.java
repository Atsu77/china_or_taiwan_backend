package com.example.chinaornotbackend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "quizzes")
public class Quiz {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "question", nullable = false)
  private String question;

  @JsonProperty("image_url")
  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  // correctRateは%で表すためint
  @JsonProperty("correct_rate")
  @Column(name = "correct_rate")
  private int correctRate;

  @OneToMany(mappedBy = "quiz")
  private List<QuizResult> quizResults;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "answer_id")
  private Answer answer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "difficulty_id")
  private Difficulty difficulty;
}
