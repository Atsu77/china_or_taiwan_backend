package com.example.chinaornotbackend.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chinaornotbackend.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

  Optional<Answer> findByAnswer(String answer);
}
