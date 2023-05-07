package com.example.chinaornotbackend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chinaornotbackend.model.QuizResult;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
  List<QuizResult> findByQuizId(Long quizId);
}
