
import java.util.*;

public class AttemptQuiz extends Quiz {
    private String dateAttempted;
    private List<Character> chosenOptions;
    private List<Integer> scorePerQuestion;
    private List<String> incorrectQuesIDs;
    private List<String> unattemptedQuesIDs;
    private int totalScore;
    private int time;

    // Constructor
    public AttemptQuiz(String id, String name, String topic, String username, String doc, double avgScore, double avgTime, int timeAlloted, int noQues)
    {
        super.setID(id);
        super.setName(name);
        super.setTopic(topic);
        super.setUsername(username);
        super.setDateOfCreation(doc);
        super.setAvgScore(avgScore);
        super.setAvgTime(avgTime);
        super.setTimeAllotted(timeAlloted);
        super.setNumberOfQuestions(noQues);
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