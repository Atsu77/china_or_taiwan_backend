package com.example.chinaornotbackend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.chinaornotbackend.model.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
  List<Score> findByUserId(Long userId);

  @Query(value = "SELECT * FROM scores ORDER BY total_score DESC LIMIT :limit", nativeQuery = true)
  List<Score> findTopScoresByOrderByTotalScoreDesc(@Param("limit") int limit);

}
