package com.example.chinaornotbackend.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chinaornotbackend.model.QuizCategory;

@Repository
public interface QuizCategoryRepository extends JpaRepository<QuizCategory, Long> {
  Optional<QuizCategory> findByName(String name);
}
