import java.util.Map;

public class Question {
    private String text;
    private Map<Character, String> options;
    private Character correctOption;
    private int marksForCorrect;
    private int marksForWrong;
    private double averageScore;

    // Constructor for Question class
    public Question(String text, Map<Character, String> options, Character correctOption, int marksForCorrect, int marksForWrong, double averageScore) {
        this.text = text;
        this.options = options;
        this.correctOption = correctOption;
        this.marksForCorrect = marksForCorrect;
        this.marksForWrong = marksForWrong;
        this.averageScore = averageScore;
    }

    // Getters for question attributes
    public String getText() {
        return text;
    }

    public Map<Character, String> getOptions() {
        return options;
    }

    public Character getCorrectOption() {
        return correctOption;
    }

    public int getMarksForCorrect() {
        return marksForCorrect;
    }

    public int getMarksForWrong() {
        return marksForWrong;
    }

    public double getAverageScore() {
        return averageScore;
    }
}
