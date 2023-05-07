package com.example.chinaornotbackend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.chinaornotbackend.model.Answer;
import com.example.chinaornotbackend.model.Quiz;
import com.example.chinaornotbackend.model.QuizCategory;
import com.example.chinaornotbackend.model.Score;
import com.example.chinaornotbackend.model.User;
import com.example.chinaornotbackend.model.repository.AnswerRepository;
import com.example.chinaornotbackend.model.repository.QuizCategoryRepository;
import com.example.chinaornotbackend.model.repository.QuizRepository;
import com.example.chinaornotbackend.model.repository.ScoreRepository;
import com.example.chinaornotbackend.model.repository.UserRepository;
import com.example.chinaornotbackend.util.JsonFileReader;
import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private QuizCategoryRepository quizCategoryRepository;

    @Override
    public void run(ApplicationArguments args) {
        List<User> users = JsonFileReader.readJsonFile("src/main/resources/data/users.json",
                new TypeReference<List<User>>() {
                });
        users.stream()
                .filter(user -> userRepository.findByName(user.getName()) == null)
                .forEach(user -> userRepository.save(user));

        List<Answer> answers = JsonFileReader.readJsonFile("src/main/resources/data/answers.json",
                new TypeReference<List<Answer>>() {
                });
        answers.stream()
                .filter(answer -> answerRepository.findByAnswer(answer.getAnswer()).isEmpty())
                .forEach(answer -> answerRepository.save(answer));

        List<Quiz> quizzes = JsonFileReader.readJsonFile("src/main/resources/data/quizzes.json",
                new TypeReference<List<Quiz>>() {
                });
        quizzes.stream()
                .filter(quiz -> quizRepository.findByQuestion(quiz.getQuestion()).isEmpty())
                .forEach(quiz -> quizRepository.save(quiz));

        for (Quiz quiz : quizzes) {
            Answer relatedAnswer = answers.stream()
                    .filter(answer -> answer.getId() == quiz.getAnswer().getId())
                    .findFirst()
                    .orElse(null);

            if (relatedAnswer != null) {
                quiz.setAnswer(relatedAnswer);
            }
        }

        List<Score> scores = JsonFileReader.readJsonFile("src/main/resources/data/scores.json",
                new TypeReference<List<Score>>() {
                });
        scores.stream()
                .forEach(score -> scoreRepository.save(score));

        List<QuizCategory> quizCategories = JsonFileReader.readJsonFile("src/main/resources/data/quizCategories.json",
                new TypeReference<List<QuizCategory>>() {
                });
        quizCategories.stream()
                .filter(quizCategory -> quizCategoryRepository.findByName(quizCategory.getName()).isEmpty())
                .forEach(quizCategory -> quizCategoryRepository.save(quizCategory));
    }
}