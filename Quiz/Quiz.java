package QuizClasses;
import java.util.*;


public abstract class Quiz {
    // Define attributes of a quiz 
    protected String ID;
    protected String name;
    protected String topic;
    protected String dateOfCreation;
    protected double avgScore;
    protected double avgTime;
    protected String filepath;
    public List<Question> questions = new ArrayList<>();
    protected int timeAllotted;
    protected int numberOfQuestions;
    protected String username;

    // Default constructor
    public Quiz() {
        this.ID = ""; // Initialize ID to an empty string
        this.name = ""; // Initialize name to an empty string
        this.topic = ""; // Initialize topic to an empty string
        this.dateOfCreation = ""; // Initialize date_of_creation to an empty string
        this.avgScore = 0; // Initialize avg_score to an empty string
        this.avgTime = 0; // Initialize avg_time to an empty string
        this.filepath = ""; // Initialize filepath to an empty string
        this.timeAllotted = 0; // Default to 0
        this.numberOfQuestions = 0; // Default to 0
        this.username = ""; // Initialize username to an empty string
    }

    // Parameterized Constructor 
    public Quiz(String ID, String name, String topic, String date_of_creation, 
                double avg_score, double avg_time,int time_allotted, int noOfQuestions) {
        this.ID = ID;
        this.name = name;
        this.topic = topic;
        this.dateOfCreation = date_of_creation;
        this.avgScore = avg_score;
        this.avgTime = avg_time;
        this.timeAllotted = time_allotted;
        this.numberOfQuestions = noOfQuestions;
    }


    // Getter methods 
    public int getTimeAllotted() {
        return timeAllotted;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public String getUsername() {
        return username;
    }
    
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
        return dateOfCreation;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public double getAvgTime() {
        return avgTime;
    }

    public String getFilepath() {
        return filepath;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    // Setters 
    public void setTimeAllotted(int time_allotted) {
        this.timeAllotted = time_allotted;
    }  

    public void setNumberOfQuestions(int number_of_questions) {
        this.numberOfQuestions = number_of_questions;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
        this.dateOfCreation = date_of_creation;
    }

    public void setAvgScore(double avg_score) {
        this.avgScore = avg_score;
    }

    public void setAvgTime(double avg_time) {
        this.avgTime = avg_time;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    // Function to print the details of the quiz object 
    @Override
    public String toString() {
        return "ID: " + ID + ", Name: " + name + ", Topic: " + topic + 
               ", Date of Creation: " + dateOfCreation + 
               ", Average Score: " + avgScore + " marks" + 
               ", Average Time Taken: " + avgTime + " mins" + 
               ", Time Allotted: " + timeAllotted + " mins";
    }
    
    // Function to add Questions to a quiz 
    public void addQuestions(Question question) { 
        this.questions.add(question);
    }
}
