import java.util.*;

public abstract class Quiz {
    protected String ID;
    protected String name;
    protected String topic;
    protected String date_of_creation;
    protected double avg_score;
    protected double avg_time;
    protected String filepath;
    protected List<Question> questions = new ArrayList<>();
    protected int time_allotted;
    protected int number_of_questions;
    protected String username;
    //protected int Number_of_options;

    // Default constructor
    public Quiz() {
        this.ID = ""; // Initialize ID to an empty string
        this.name = ""; // Initialize name to an empty string
        this.topic = ""; // Initialize topic to an empty string
        this.date_of_creation = ""; // Initialize date_of_creation to an empty string
        this.avg_score = 0; // Initialize avg_score to an empty string
        this.avg_time = 0; // Initialize avg_time to an empty string
        this.filepath = ""; // Initialize filepath to an empty string

        // Initialize integer attributes to their default values
        this.time_allotted = 0; // Default to 0
        this.number_of_questions = 0; // Default to 0
        this.username = ""; // Initialize username to an empty string
    }

    // Parameterized Constructor 
    public Quiz(String ID, String name, String topic, String date_of_creation, 
                double avg_score, double avg_time,int time_allotted, int noOfQuestions) {
        this.ID = ID;
        this.name = name;
        this.topic = topic;
        this.date_of_creation = date_of_creation;
        this.avg_score = avg_score;
        this.avg_time = avg_time;
        this.time_allotted = time_allotted;
        this.number_of_questions = noOfQuestions;
    }


    // getter for thw whole object of quesiton

    // Getter and Setter for time_allotted
    public int getTimeAllotted() {
        return time_allotted;
    }

    public void setTimeAllotted(int time_allotted) {
        this.time_allotted = time_allotted;
    }

    // Getter and Setter for number_of_questions
    public int getNumberOfQuestions() {
        return number_of_questions;
    }

    public void setNumberOfQuestions(int number_of_questions) {
        this.number_of_questions = number_of_questions;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /// ===============================================

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

    public double getAvgScore() {
        return avg_score;
    }

    public double getAvgTime() {
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

    public void setAvgScore(double avg_score) {
        this.avg_score = avg_score;
    }

    public void setAvgTime(double avg_time) {
        this.avg_time = avg_time;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "ID: " + ID + ", Name: " + name + ", Topic: " + topic + 
               ", Date of Creation: " + date_of_creation + 
               ", Average Score: " + avg_score + " marks" + 
               ", Average Time Taken: " + avg_time + " mins" + 
               ", Time Allotted: " + time_allotted + " mins";
    }

    public void addQuestions(Question question) { // Added this -Ananya
        this.questions.add(question);
    }
}
