package users;

import attemptedquiz.AttemptQuiz;
import attemptedquiz.AttemptedQuiz;

import java.util.*;

import QuizClasses.Question;
import QuizClasses.Quiz;

public class ProfileHistory {
    private User user;  // classes.User whose history we are tracking
    private Scanner scanner = new Scanner(System.in);
    private AttemptedQuiz attemptQuiz;

    public ProfileHistory(User user) {
        this.user = user;
    }

    // Display profile history
    public void displayProfileHistory() {
        System.out.println("Username: " + user.getUsername());
        System.out.println("Do you want to see your:\n1. Attempted Quizzes\n2. Created Quizzes");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                displayAttemptedQuizzes();
                break;
            case 2:
                displayCreatedQuizzes();
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
                return;
        }
    }

    // Display attempted quizzes
    private void displayAttemptedQuizzes() {
        List<AttemptQuiz> quizzes = new ArrayList<>();
        for (Quiz quiz : user.getAttempted()) {
            if (quiz instanceof AttemptQuiz) {
                quizzes.add((AttemptQuiz) quiz);
            }
        }

        quizzes.sort(Comparator
                .comparing(AttemptQuiz::getDateAttempted)
                .thenComparing(AttemptQuiz::getTime)
                .reversed());

        System.out.println("Quizzes Attempted:");
        for (AttemptQuiz quiz : quizzes) {
            System.out.println("classes.Quiz Title: " + quiz.getName());
            System.out.println("classes.Quiz ID: " + quiz.getID());
            System.out.println("Topic: " + quiz.getTopic());
            System.out.println("Date Attempted: " + quiz.getDateAttempted());
            System.out.println("Score: " + quiz.getTotalScore());
            System.out.println("Time Taken: " + quiz.getTime() + " mins");
            System.out.println("--------------");
        }

        System.out.println("Enter the classes.Quiz ID you want to makequiz.review or type 'exit' to return:");
        String input = scanner.nextLine();

        if (!input.equalsIgnoreCase("exit")) {
            AttemptQuiz selectedQuiz = null;
            for (AttemptQuiz q : quizzes) {
                String stringId = String.valueOf(q.getID());
                if (stringId.equals(input)) {
                    selectedQuiz = q;
                    break; // Stop looping once the quiz is found
                }
            }


            if (selectedQuiz != null) {
                reviewQuiz(selectedQuiz);
            } else {
                System.out.println("Invalid classes.Quiz ID.");
            }
        }
    }

    // Display created quizzes
    private void displayCreatedQuizzes() {
        List<Quiz> createdQuizzes = user.getQuizManager().getQuizzesByUser(user.getUsername()); // Retrieve quizzes created by the user

        System.out.println("Created Quizzes:");
        for (Quiz quiz : createdQuizzes) {
            System.out.println("classes.Quiz ID: " + quiz.getID());
            System.out.println("classes.Quiz Title: " + quiz.getName());
            System.out.println("Topic: " + quiz.getTopic());
            System.out.println("Date Created: " + quiz.getDateOfCreation());
            System.out.println("Average Score: " + quiz.getAvgScore());
            System.out.println("Average Time: " + quiz.getAvgTime() + " mins");
            System.out.println("--------------");
        }

        System.out.println("Enter the classes.Quiz ID you want to makequiz.review or type 'exit' to return:");
        String input = scanner.nextLine();

        if (!input.equalsIgnoreCase("exit")) {
            Quiz selectedQuiz = null;
            for (Quiz q : createdQuizzes) {
                String stringId = String.valueOf(q.getID());
                if (stringId.equals(input)) {
                    selectedQuiz = q;
                    break; // Stop looping once the quiz is found
                }
            }

            if (selectedQuiz != null) {
                reviewCreatedQuiz(selectedQuiz);
            } else {
                System.out.println("Invalid classes.Quiz ID.");
            }
        }
    }

    // Review a selected quiz (for attempted quizzes)
    private void reviewQuiz(AttemptQuiz quiz) {
        System.out.println("Options: [makequiz.review] or [back]");
        String option = scanner.nextLine();

        if (option.equalsIgnoreCase("makequiz.review")) {
            displayFilterOptions(quiz);
        }
    }

    // Review a selected quiz (for created quizzes)
    private void reviewCreatedQuiz(Quiz quiz) {

        List<Question> questions = quiz.getQuestions();
        int index = 0;
        while (index >= 0 && index < questions.size()) {

            attemptQuiz.displayQuestion(index);

            String action = scanner.nextLine();

            switch (action.toLowerCase()) {
                case "next":
                    if (index < questions.size() - 1) {
                        index++;
                    } else {
                        System.out.println("This is the last question.");
                    }
                    break;
                case "previous":
                    if (index > 0) {
                        index--;
                    } else {
                        System.out.println("This is the first question.");
                    }
                    break;
                case "end makequiz.review":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Display filter options
    private void displayFilterOptions(AttemptQuiz quiz) {
        System.out.println("Choose a filter: [all] [incorrect] [unattempted]");
        String filterOption = scanner.nextLine();
        List<Question> questions = filterQuestions(quiz, filterOption);

        if (questions != null) {
            reviewQuestions(quiz, questions, filterOption);
        } else {
            System.out.println("Invalid filter option.");
        }
    }

    // Filter questions based on user input
    private List<Question> filterQuestions(AttemptQuiz quiz, String filterOption) {
        switch (filterOption.toLowerCase()) {
            case "all":
                return quiz.getQuestions();
            case "incorrect":
                return getIncorrectQuestions(quiz);
            case "unattempted":
                return getUnattemptedQuestions(quiz);
            default:
                return null;
        }
    }

    private List<Question> getIncorrectQuestions(AttemptQuiz quiz) {
        List<Question> incorrectQuestions = new ArrayList<>();
        List<Question> questions = quiz.getQuestions();
        List<Character> userAnswers = quiz.getChosenOptions();

        for (int i = 0; i < questions.size(); i++)
        {
            Question question = questions.get(i);
            Character userAnswer = userAnswers.get(i);

            if (!userAnswer.equals(question.getCorrectOption())) {
                incorrectQuestions.add(question);
            }
        }
        return incorrectQuestions;
    }

    private List<Question> getUnattemptedQuestions(AttemptQuiz quiz) {
        List<Question> unattemptedQuestions = new ArrayList<>();
        List<Question> questions = quiz.getQuestions();
        List<Character> userAnswers = quiz.getChosenOptions();

        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers.get(i) == ' ') {  // Assuming ' ' denotes unattempted
                unattemptedQuestions.add(questions.get(i));
            }
        }
        return unattemptedQuestions;
    }

    // Review questions
    private void reviewQuestions(AttemptQuiz quiz, List<Question> questions, String filterOption) {
        int index = 0;
        while (index >= 0 && index < questions.size()) {
            Question currentQuestion = questions.get(index);
            displayQuestionOptions(currentQuestion, filterOption);

            String action = scanner.nextLine();

            switch (action.toLowerCase()) {
                case "show answer":
                    showAnswer(currentQuestion, quiz, filterOption);
                    break;
                case "next":
                    if (index < questions.size() - 1) {
                        index++;
                    } else {
                        System.out.println("This is the last question.");
                    }
                    break;
                case "previous":
                    if (index > 0) {
                        index--;
                    } else {
                        System.out.println("This is the first question.");
                    }
                    break;
                case "end makequiz.review":
                    return;
                case "filter":
                    displayFilterOptions(quiz);
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Display question options
    private void displayQuestionOptions(Question question, String filterOption) {
        System.out.println("classes.Question: " + question.getText());

        if (filterOption.equals("incorrect") || filterOption.equals("unattempted")) {
            System.out.println("Options: [show answer] [next] [previous] [end makequiz.review] [filter]");
        } else {
            System.out.println("Options: [show answer] [next] [previous] [end makequiz.review] [filter]");
        }
    }

    // Show the answer
    private void showAnswer(Question question, AttemptQuiz quiz, String filterOption) {
        System.out.println("Correct Answer: " + question.getCorrectOption());
        if (!filterOption.equals("unattempted")) {
            System.out.println("Your Answer: " + getUserAnswer(quiz, question));
        }
    }

    private char getUserAnswer(AttemptQuiz quiz, Question question) {
        int questionIndex = quiz.getQuestions().indexOf(question);
        return quiz.getChosenOptions().get(questionIndex);
    }
}
