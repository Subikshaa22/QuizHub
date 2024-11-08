import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import java.util.*;


class User {
    private String username;
    private String password;
    private List<Quiz> attempted;  // List of quizzes attempted by the user
    private List<String> made;     // List of quiz names created by the user

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.attempted = new ArrayList<>();
        this.made = new ArrayList<>();
    }

    // Native methods (to be implemented in C++)
    public native boolean login(String username, String password); // Native login
    public native void register(); // Native registration
    public native void viewProfile(); // Native profile view
    public native void addAttemptedQuiz(Quiz quiz); // Native method to add attempted quiz
    public native void addMadeQuiz(String quizName); // Native method to add created quiz

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Quiz> getAttempted() {
        return attempted;
    }

    public void setAttempted(List<Quiz> attempted) {
        this.attempted = attempted;
    }

    public List<String> getMade() {
        return made;
    }

    public void setMade(List<String> made) {
        this.made = made;
    }

    // ToString method for debugging purposes
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", attempted=" + attempted +
                ", made=" + made +
                '}';
    }

    // Load the C++ library at runtime
    static {
        System.loadLibrary("UserJNI"); // Assuming your C++ compiled library is named "UserJNI"
    }
}

class quizManager {

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

    public native void attemptQuiz();

    public native void createQuiz(); 

    public native void displayTopics();

    public native void displayQuizzes(); //displaying quizzes with the same topic

    public native void generateQuizzes();

}

abstract class Quiz
{
    protected String ID;
    protected String name;
    protected String topic;
    protected String date_of_creation;
    protected String avg_score;
    protected String avg_time;
    protected String filepath;
    protected List<Question> questions= new ArrayList<> ();
    
    // Getters
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }

    public String getDateOfCreation() {
        return date_of_creation;
    }

    public String getAvgScore() {
        return avg_score;
    }

    public String getAvgTime() {
        return avg_time;
    }

    public String getFilepath() {
        return filepath;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    // Setters
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setDateOfCreation(String date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public void setAvgScore(String avg_score) {
        this.avg_score = avg_score;
    }

    public void setAvgTime(String avg_time) {
        this.avg_time = avg_time;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    // Method to print all quiz details
    public void printDetails() {
        System.out.println("Quiz Details:");
        System.out.println("ID: " + ID);
        System.out.println("Name: " + name);
        System.out.println("Topic: " + topic);
        System.out.println("Date of Creation: " + date_of_creation);
        System.out.println("Average Score: " + avg_score);
        System.out.println("Average Time: " + avg_time);
        System.out.println("Filepath: " + filepath);
        System.out.println("Questions: " + questions.size() + " total");
        for (Question question : questions) {
            // Assuming Question class has a toString() method
            System.out.println(" - " + question.toString());
        }
    }
}

class ExistingQuizzes extends Quiz{
    private int score;

    //getter setter
    public int getScore(){
        return score;
    }
    public void setScore(int score){
        this.score=score;
    }
}

class AttemptedQuiz extends Quiz {
    private String dateAttempted;
    private List<Character> chosenOptions;
    private List<Integer> scorePerQuestion;
    private List<String> incorrectQuesIDs;
    private List<String> unattemptedQuesIDs;
    private int totalScore;
    private int time;

    // Constructor
    public AttemptedQuiz(String dateAttempted, List<Character> chosenOptions, List<Integer> scorePerQuestion,
                         List<String> incorrectQuesIDs, List<String> unattemptedQuesIDs,
                         int totalScore, int time) {
        this.dateAttempted = dateAttempted;
        this.chosenOptions = chosenOptions;
        this.scorePerQuestion = scorePerQuestion;
        this.incorrectQuesIDs = incorrectQuesIDs;
        this.unattemptedQuesIDs = unattemptedQuesIDs;
        this.totalScore = totalScore;
        this.time = time;
    }

    // Getters and Setters

    public String getDateAttempted() {
        return dateAttempted;
    }

    public void setDateAttempted(String dateAttempted) {
        this.dateAttempted = dateAttempted;
    }

    public List<Character> getChosenOptions() {
        return chosenOptions;
    }

    public void setChosenOptions(List<Character> chosenOptions) {
        this.chosenOptions = chosenOptions;
    }

    public List<Integer> getScorePerQuestion() {
        return scorePerQuestion;
    }

    public void setScorePerQuestion(List<Integer> scorePerQuestion) {
        this.scorePerQuestion = scorePerQuestion;
    }

    public List<String> getIncorrectQuesIDs() {
        return incorrectQuesIDs;
    }

    public void setIncorrectQuesIDs(List<String> incorrectQuesIDs) {
        this.incorrectQuesIDs = incorrectQuesIDs;
    }

    public List<String> getUnattemptedQuesIDs() {
        return unattemptedQuesIDs;
    }

    public void setUnattemptedQuesIDs(List<String> unattemptedQuesIDs) {
        this.unattemptedQuesIDs = unattemptedQuesIDs;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

abstract class Question {

    protected String questionText;          // The question text
    protected char correctAnswer;           // Correct answer as 'a', 'b', 'c', 'd' for MCQ or 'T'/'F' for True/False

    // Constructor
    public Question(String questionText, char correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    // Native method declarations (to be implemented in C++)
    public abstract void displayQuestion();
    public abstract boolean checkAnswer(char answer);

    // Getters and setters
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(char correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}

// Concrete class for MCQ questions
class MCQQuestion extends Question {
    private List<String> options;  // List of options for the question

    public MCQQuestion(String questionText, List<String> options, char correctAnswer) {
        super(questionText, correctAnswer);
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    // Native method declarations (implemented in C++)
    @Override
    public native void displayQuestion();
    @Override
    public native boolean checkAnswer(char answer);
}

// Concrete class for True/False questions
class TrueFalseQuestion extends Question {

    public TrueFalseQuestion(String questionText, char correctAnswer) {
        super(questionText, correctAnswer);
    }

    // Native method declarations (implemented in C++)
    @Override
    public native void displayQuestion();
    @Override
    public native boolean checkAnswer(char answer);
}

class QuizStorage {
    private String filePath;
    private String name;

    // Constructor
    public QuizStorage(String filePath, String name) 
    {
        this.filePath = filePath;
        this.name = name;
    }

    // Method to save the quiz to a file
    public native void saveQuiz(String content);

    // Method to load quiz details from a file
    public native String loadQuiz();
    // Method to list all quizzes in a specified directory
    public native void listOfAllQuizzes();

}