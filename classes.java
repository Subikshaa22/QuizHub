import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class User {
    private String username;
    private String email;      // Add an email field
    private String password;
    private List<Quiz> attempted;  // List of quizzes attempted by the user
    private List<String> made;     // List of quiz names created by the user
    private quizManager quizMgr;

    // Constructor
    public User(String username, String email, String password, quizManager quizMgr) {
        this.username = username;
        this.email = email; 
        this.password = password;
        this.attempted = new ArrayList<>();
        this.made = new ArrayList<>();
        this.quizMgr = quizMgr;
    }

    // Getters and setters

    public quizManager getQuizManager() {
        return quizMgr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {      
        return email;
    }

    public void setEmail(String email) { 
        this.email = email;
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

    // Method to retrieve quizzes created by a specific user
    public List<Quiz> getQuizzesByUser(String username) {
        return quizList.stream()
                .filter(quiz -> username.equals(quiz.getCreatedBy()))
                .collect(Collectors.toList());
    }

    // Method to retrieve quizzes by topic
    public List<Quiz> getQuizzesByTopic(String topic) {
        return quizList.stream()
                .filter(quiz -> topic.equals(quiz.getTopic()))
                .collect(Collectors.toList());
    }

    /*public void attemptQuiz();

    public void createQuiz(); 

    public void displayTopics();

    public void displayQuizzes(); //displaying quizzes with the same topic

    public void generateQuizzes();*/

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
    protected String createdBy;
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

    public String getCreatedBy() { 
        return createdBy;
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

    public void setCreatedBy(String createdBy) { 
        this.createdBy = createdBy;
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
