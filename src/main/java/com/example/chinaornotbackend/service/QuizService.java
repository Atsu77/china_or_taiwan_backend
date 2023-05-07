package com.example.chinaornotbackend.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chinaornotbackend.controller.Difficulty;
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

  public List<QuizResponse> getQuizzes(int limit, Difficulty difficulty) {
    List<Quiz> quizzes = quizRepository.findAll(Sort.by(Sort.Direction.ASC, "correctRate"));

    int halfIndex = quizzes.size() / 2;
    List<Quiz> filteredQuizzes;

    if (difficulty == Difficulty.MEDIUM) {
      // 正答率の降順に50%を取得
      filteredQuizzes = quizzes.subList(halfIndex, quizzes.size());
    } else {
      // 正答率の昇順に50%を取得
      filteredQuizzes = quizzes.subList(0, halfIndex);
    }

    // 上位50%からランダムにlimit件取得
    Collections.shuffle(filteredQuizzes);
    List<Quiz> selectedQuizzes = filteredQuizzes.stream().limit(limit).collect(Collectors.toList());

    // QuizResponseのリストに変換
    List<QuizResponse> quizResponses = selectedQuizzes.stream()
        .map(quiz -> new QuizResponse(quiz.getId(), quiz.getQuestion(), quiz.getAnswer().getAnswer(),
            quiz.getImageUrl(), quiz.getCorrectRate()))
        .collect(Collectors.toList());

    return quizResponses;
  }

  public QuizResponse createQuiz(@RequestBody Quiz quiz) {
    Quiz newQuiz = new Quiz();
    newQuiz.setQuestion(quiz.getQuestion());
    newQuiz.setImageUrl(quiz.getImageUrl());
    newQuiz.setAnswer(quiz.getAnswer());
    Quiz savedQuiz = quizRepository.save(newQuiz);
    QuizResponse quizResponse = new QuizResponse(savedQuiz.getId(), savedQuiz.getQuestion(),
        savedQuiz.getAnswer().getAnswer(), savedQuiz.getImageUrl(), savedQuiz.getCorrectRate());
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
