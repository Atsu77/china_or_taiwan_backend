package com.example.chinaornotbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chinaornotbackend.controller.ScoreController.QuizResultRequest;
import com.example.chinaornotbackend.model.Quiz;
import com.example.chinaornotbackend.model.QuizResult;
import com.example.chinaornotbackend.model.repository.QuizRepository;
import com.example.chinaornotbackend.model.repository.QuizResultRepository;

@Service
public class QuizResultService {
  @Autowired
  private QuizRepository quizRepository;

  @Autowired
  private QuizResultRepository quizResultRepository;

  @Transactional
  public void processQuizResults(List<QuizResultRequest> quizResults) {
    quizResults.forEach(quizResult -> {
      QuizResult quizResultModel = new QuizResult();
      quizResultModel.setQuiz(quizRepository.findById(quizResult.getQuizId()).get());
      quizResultModel.setCorrect(quizResult.isCorrect());
      quizResultRepository.save(quizResultModel);
    });
  }

  public void updateCorrectRate(Long quizId) {
    List<QuizResult> quizResults = quizResultRepository.findByQuizId(quizId);
    int correctCount = (int)quizResults.stream().filter(result -> result.isCorrect()).count();
    int correctRate = (int)((double)correctCount / quizResults.size() * 100);
    Quiz quiz = quizRepository.findById(quizId).get();
    quiz.setCorrectRate(correctRate);
    quizRepository.save(quiz);
  }
}
