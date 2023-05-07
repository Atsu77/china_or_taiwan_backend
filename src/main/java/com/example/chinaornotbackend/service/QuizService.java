package com.example.chinaornotbackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chinaornotbackend.model.Quiz;
import com.example.chinaornotbackend.model.QuizResult;
import com.example.chinaornotbackend.model.repository.QuizRepository;
import com.example.chinaornotbackend.model.repository.QuizResultRepository;
import com.example.chinaornotbackend.response.QuizResponse;

@Service
public class QuizService {
  @Autowired
  private QuizRepository quizRepository;

  @Autowired
  private QuizResultRepository quizResultRepository;

  public List<QuizResponse> getQuizzes(int limit) {
    List<Quiz> quizzes = quizRepository.findAllWithAnswers(limit);

    List<QuizResponse> quizResponses = new ArrayList<>();

    if (quizzes.isEmpty())
      return quizResponses;

    for (Quiz quiz : quizzes) {
      QuizResponse quizResponse = new QuizResponse(quiz.getId(), quiz.getQuestion(), quiz.getAnswer().getAnswer(),
          quiz.getImageUrl());
      quizResponses.add(quizResponse);
    }
    return quizResponses;
  }

  public QuizResponse createQuiz(@RequestBody Quiz quiz) {
    Quiz newQuiz = new Quiz();
    newQuiz.setQuestion(quiz.getQuestion());
    newQuiz.setImageUrl(quiz.getImageUrl());
    newQuiz.setAnswer(quiz.getAnswer());
    Quiz savedQuiz = quizRepository.save(newQuiz);
    QuizResponse quizResponse = new QuizResponse(savedQuiz.getId(), savedQuiz.getQuestion(),
        savedQuiz.getAnswer().getAnswer(), savedQuiz.getImageUrl());

    return quizResponse;
  }

  public int calculateCorrectRate(QuizResult quizResult) {
    quizResultRepository.save(quizResult);
    List<QuizResult> quizResults = quizResultRepository.findByQuizId(quizResult.getQuiz().getId());
    int quizResultCount = quizResults.size();
    int quizCorrectCount = (int) quizResults.stream()
        .filter(q -> q.isCorrect())
        .count();

    return (int) (quizCorrectCount / quizResultCount * 100);
  }
}
