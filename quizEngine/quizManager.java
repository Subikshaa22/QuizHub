package quizEngine;

import java.util.*;
import java.util.stream.Collectors;

import QuizClasses.Quiz;

public class quizManager {

    private List <Quiz> quizList;

    public quizManager(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public List <Quiz> getQuizList(){
        return quizList;
    }

    public void addQuiz(Quiz Q){
        quizList.add(Q);
    }

    // Method to retrieve quizzes created by a specific user
    public List<Quiz> getQuizzesByUser(String username) {
        return quizList.stream()
                .filter(quiz -> username.equals(quiz.getUsername()))
                .collect(Collectors.toList());
    }

    // Method to retrieve quizzes by topic
    public List<Quiz> getQuizzesByTopic(String topic) {
        return quizList.stream()
                .filter(quiz -> topic.equals(quiz.getTopic()))
                .collect(Collectors.toList());
    }
}
