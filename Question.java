import java.util.Map;

public class Question {
    private String text;
    private Map<Character , String> options;
    private Character correctOption;
    private int marksForCorrect;
    private int marksForWrong;

    public Question() {
        // initialize default values here
        this.text = "";
        this.correctOption = '\0'; // Default to null character, can be set later
    }

    // Constructor for Question class
    public Question(String text, Map<Character, String> options, Character correctOption, int marksForCorrect,
            int marksForWrong) {
        this.text = text;
        this.options = options;
        this.correctOption = correctOption;
        this.marksForCorrect = marksForCorrect;
        this.marksForWrong = marksForWrong;
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


    // setters
    public void setQuestionText(String questionText) {
        this.text = questionText;
    }

    public void setCorrectOption(char correctAnswer) {
        this.correctOption = correctAnswer;
    }

    public void setMarksForCorrect(int marks) {
        this.marksForCorrect = marks;
    }

    public void setMarksForWrong(int marks) {
        this.marksForWrong = marks;
    }

    public void setOptions(Map<Character, String> options) {
        this.options = options;
    }
    
}
