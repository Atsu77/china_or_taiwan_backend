package com.example.chinaornotbackend.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.chinaornotbackend.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

  Optional<Quiz> findByQuestion(String question);

  @Query(value = "SELECT * FROM quizzes ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
  List<Quiz> findAllWithAnswers(@Param("limit") int limit);
}
