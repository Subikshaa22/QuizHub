
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
    public AttemptQuiz(String dateAttempted, List<Character> chosenOptions, List<Integer> scorePerQuestion,
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
