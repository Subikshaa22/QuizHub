package QuizClasses;

import java.util.Map;

public class Question {
   private String text;
   private Map<Character, String> options;
   private Character correctOption;
   private Character userAnswer; // New field for user's answer
   private int marksForCorrect;
   private int marksForWrong;
   private int score;

   public Question() {
      this.text = "";
      this.correctOption = '\u0000';
   }

   public Question(String text, Map<Character, String> options, Character correctOption, int marksForCorrect, int marksForWrong, int score) {
      this.text = text;
      this.options = options;
      this.correctOption = correctOption;
      this.marksForCorrect = marksForCorrect;
      this.marksForWrong = marksForWrong;
      this.score = score;
   }

   public Question(String text, Map<Character, String> options, char correctOption, int marksForCorrect, int marksForWrong) {
    this.text = text;
    this.options = options;
    this.correctOption = correctOption;
    this.marksForCorrect = marksForCorrect;
    this.marksForWrong = marksForWrong;
}

   // Existing getters
   public String getText() {
      return this.text;
   }

   public Map<Character, String> getOptions() {
      return this.options;
   }

   public Character getCorrectOption() {
      return this.correctOption;
   }

   public int getScore() {
        return score;
   }

   public int getMarksForCorrect() {
      return this.marksForCorrect;
   }

   public int getMarksForWrong() {
      return this.marksForWrong;
   }

   // New getters and setters
   public String getQuestionText() {
      return this.getText();
   }

   public Character getAnswer() {
      return this.userAnswer;
   }

   public void setAnswer(Character userAnswer) {
      this.userAnswer = userAnswer;
   }

   // Existing setters
   public void setQuestionText(String text) {
      this.text = text;
   }

   public void setCorrectOption(char correctOption) {
      this.correctOption = correctOption;
   }

   public void setMarksForCorrect(int marksForCorrect) {
      this.marksForCorrect = marksForCorrect;
   }

   public void setMarksForWrong(int marksForWrong) {
      this.marksForWrong = marksForWrong;
   }

   public void setOptions(Map<Character, String> options) {
      this.options = options;
   }
}
