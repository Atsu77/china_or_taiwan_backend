package com.example.chinaornotbackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chinaornotbackend.model.Score;
import com.example.chinaornotbackend.model.repository.ScoreRepository;
import com.example.chinaornotbackend.response.RankingResponse;

@RestController
@RequestMapping("/api/ranking")
public class RankingController {
  @Autowired
  private ScoreRepository scoreRepository;

  @GetMapping
  public ResponseEntity<List<RankingResponse>> getRankings() {
    int topTen = 10;
    List<Score> topTenScores = scoreRepository.findTopScoresByOrderByTotalScoreDesc(topTen);
    List<RankingResponse> rankings = new ArrayList<>();

    int rank = 1;
    for (Score score : topTenScores) {
      rankings.add(new RankingResponse(score.getUser().getId(), score.getUser().getName(), score.getTotalScore(), rank++));
    }

    return new ResponseEntity<>(rankings, HttpStatus.OK);
  }

}
