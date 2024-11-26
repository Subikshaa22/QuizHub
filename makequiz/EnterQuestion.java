package makequiz;

import QuizClasses.Question;
import java.util.*;

// Class to handle adding questions to a quiz
public class EnterQuestion {

    // Hold the newQuiz object created in MakeQuiz 
    private ExistingQuizzes refQuiz;

    // Static Scanner instance for taking user input
    static Scanner scanner = new Scanner(System.in);
    
    // Default Constructor 
    public EnterQuestion() {
    }

    // Method to set the reference to the quiz object
    public void setQobject(ExistingQuizzes refQuiz) {
      this.refQuiz = refQuiz;
    }

    // Method to repeatedly allow the user to add questions until they choose to stop
    public void EnterQuestions() {
        boolean stopNow = false;
        while(!stopNow) {
            if(refQuiz.getQuestions().size()<1){
                EnterNewQuestion();
            }
            else{
                // Add a new question
                System.out.println("If you are done entering questions Enter Yes, else enter No");

                String stop = scanner.nextLine().trim();
                stop=stop.toLowerCase();
                if(stop.equals("yes")){
                    stopNow = true;
                }
                else if(stop.equals("no")){
                    stopNow=false;
                    EnterNewQuestion();
                }
                else {
                    System.out.println("Invalid input. Please enter 'Yes' or 'No'.");
                }
            }
        }
    }

    // Method to enter a new question 
    public void EnterNewQuestion() {
        // Create a new Question object
        Question newQuestion = new Question();

        // Store option labels to ensure they follow alphabetical order
        List<Character> labels = new ArrayList<Character>();
        Map<Character, String> options = new HashMap<>();

        // Enter the content of the Question
        String questionText = "";
        System.out.println("Enter Question text :");
 
        // Loop for entering/editing the question text
        while (true) {
            String text = scanner.nextLine().trim();
            if (text.isEmpty()) {
                System.out.println("Question text cannot be empty. Please enter valid text.");
                continue;
            }
            if (text.equalsIgnoreCase("freeze")) {
                if (questionText.isEmpty()) {
                    System.out.println("You cannot freeze without entering question text. Please add the text first.");
                    continue;
                }
                System.out.println("Question has been frozen.");
                break;
            }
            
            questionText = text;
            System.out.println("To change question text enter question text");
            System.out.println("Enter freeze to Freeze the Question");
        }

        // Loop for entering options
        String optionInput;
        System.out.println("Enter Option label followed by the option text (Eg. A text):");
        while (true) {
            optionInput = scanner.nextLine().trim();

            // Check if the user wants to freeze the options
            if (optionInput.equalsIgnoreCase("freeze")) {
                
                // Check if the options are empty 
                if (options.isEmpty()) {
                    System.out.println("You cannot freeze without adding any options. Please add at least one.");
                    continue;
                }
                System.out.println("Options have been frozen.");
                break;
            }

            String[] parts = optionInput.split(" ", 2);

            if (parts.length == 2) {
                char Option_Label = parts[0].toUpperCase().charAt(0); // Normalize to Uppercase
                String Option_Text = parts[1];

                // Check if this is the first label or the next label alphabetically
                if (labels.isEmpty() && Option_Label == 'A') {
                    labels.add(Option_Label);
                    options.put(Option_Label, Option_Text);
                    System.out.println("Enter Option label followed by the option text (Eg. A text):");
                    System.out.println("Enter Option label followed by the option text to change");
                    System.out.println("Enter freeze to Freeze the options");
                } else if ((!labels.isEmpty() && labels.get(labels.size() - 1) == Option_Label - 1)||labels.contains(Option_Label)) {
                    // The current label is the next one alphabetically
                    labels.add(Option_Label);
                    options.put(Option_Label, Option_Text);
                    System.out.println("Enter Option label followed by the option text (Eg. A text):");
                    System.out.println("Enter Option label followed by the option text to change");
                    System.out.println("Enter freeze to Freeze the options");
                } else {
                    // Invalid label entry: should follow the last entered label.
                    char expectedLabel = (labels.isEmpty()) ? 'A' : (char) (labels.get(labels.size() - 1) + 1);
                    System.out.println("Invalid label. Please enter label " + expectedLabel);
                }

            } else {
                System.out.println("Invalid input. Please enter an option label followed by option text with a space in between.");
            }
        }

        // Enter the correct answer
        char CorrectAnswer = '\0';
        System.out.println("Enter Correct Answer(Eg. A) :");
        while (true) {
            String CorrectAnswerInput = scanner.nextLine();

            // Check if the user wants to freeze the correct answer
            if (CorrectAnswerInput.equalsIgnoreCase("freeze")) {
                System.out.println("Correct Answer has been frozen.");
                break;
            }
            if (CorrectAnswerInput.length() == 1) {
                CorrectAnswer = Character.toUpperCase(CorrectAnswerInput.charAt(0));
                if (options.containsKey(CorrectAnswer)) {  // Validate against entered options
                    System.out.println("Enter Correct Answer to change");
                    System.out.println("Enter freeze to Freeze the Correct Answer");
                } else {
                    System.out.println("Invalid correct answer. Please choose from the entered options.");
                }
            } 
            else {
                System.out.println("Invalid input. Enter a single character as the correct answer.");
            }
        }

        // Enter marks for correct answers, initialized with default value
        int marksForCorrect = 1;
        System.out.println("Enter marks for the correct answer (default is 1)\nEnter freeze to keep the default value:");
        while (true) {
            String marksGiven = scanner.nextLine().trim();
            // Check if the user wants to freeze the correct answer
            if (marksGiven.equalsIgnoreCase("freeze")) {
                System.out.println("Marks for correct answer has been frozen.");
                break;
            }
            try {
                int marks = Integer.parseInt(marksGiven);
                if (marks >= 1) {
                    marksForCorrect = marks;
                    System.out.println("Enter marks for the correct answer to change");
                    System.out.println("Enter freeze to Freeze the Correct Answer");
                } else {
                    System.out.println("Marks must be >= 1.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please choose an integer >=1 or type freeze.");
            }
        }

        // Enter marks for wrong answers
        int marksForWrong = 0;
        System.out.println("Enter marks for the wrong answer (default is 0)\nEnter freeze to keep the default value:");
        while (true) {
            String marksGiven = scanner.nextLine();
            // Check if the user wants to freeze the correct answer
            if (marksGiven.equalsIgnoreCase("freeze")) {
                System.out.println("marks for wrong answer has been frozen.");
                break;
            }
            try {
                int marks = Integer.parseInt(marksGiven);
                if (marks <= 0) {
                    marksForWrong = marks;
                    System.out.println("Enter marks for the wrong answer to change");
                    System.out.println("Enter freeze to Freeze the Wrong Answer");
                } else {
                    System.out.println("Invalid input. Please choose an integer <=0 or type freeze.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter an integer <= 0 or type 'freeze'.");
            }
        }

        // Set question details to Question object 
        newQuestion.setQuestionText(questionText);
        newQuestion.setCorrectOption(CorrectAnswer);
        newQuestion.setOptions(options);
        newQuestion.setMarksForCorrect(marksForCorrect);
        newQuestion.setMarksForWrong(marksForWrong);

        // Add question to the quiz Object 
        refQuiz.addQuestions(newQuestion);
    }

}

