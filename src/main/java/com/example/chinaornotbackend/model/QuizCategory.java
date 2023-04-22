package com.example.chinaornotbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "quiz_categories")
public class QuizCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // GenerationType.IDENTITYを指定すると、主キーの生成方法をデータベースに依存させることができる
  private Long id;

  @Column(nullable = false)
  private String name;
}
