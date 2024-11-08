import java.util.List;

public abstract class Question {

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
