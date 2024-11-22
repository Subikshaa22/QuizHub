package makequiz;
import Quiz.Question;
import java.util.*;

// To make a question and add it to the quiz class
public class enterquestion {

    private ExistingQuizzes refQuiz;

    //Setter
    public void setQobject(ExistingQuizzes refQuiz)
    {
      this.refQuiz = refQuiz;
   }

    // ------------------------------------------- Ananya

    //to take user input to set question details
    static Scanner scanner = new Scanner(System.in);


    public void EnterQuestions()
    {
        boolean stop_now=false;
        while(!stop_now){
            EnterNewQuestion();
            System.out.println("If you are done entering questions Enter Yes, else enter No");
            String Stop = scanner.nextLine();
            if(Stop.equals("Yes")){
                stop_now=true;
            }
        }
    }
    public void EnterNewQuestion() {

        // Create question object
        Question newQuestion = new Question();
        // Storing option labels in a separate file so that the user enters options with option labels going alphabetically
        List<Character> labels = new ArrayList<Character>();
        //to store the options
        Map<Character, String> options = new HashMap<>();

        String Question_text = "";
        System.out.println("Enter classes.Question text :");

    //save or edit inside and enter question outside
        while (true) {
            String text = scanner.nextLine();
            // set text

            // Check if the user wants to freeze the question
            if (text.equalsIgnoreCase("freeze")) {
                System.out.println("classes.Question has been frozen.");
                break;
            }
            else{
                Question_text=text;
            }
            System.out.println("To change question text enter question text");
            System.out.println("Enter freeze to Freeze the classes.Question");
        }

        String Option_Input;
        System.out.println("Enter Option label followed by the option text (Eg. A text):");
        while (true) {
            Option_Input = scanner.nextLine();
            // Check if the user wants to freeze the options
            if (Option_Input.equalsIgnoreCase("freeze")) {
                System.out.println("Options have been frozen.");
                break;
            }

            String[] parts = Option_Input.split(" ", 2);
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

        char CorrectAnswer = '\0';
        System.out.println("Enter Correct Answer(Eg. A) :");
        while (true) {
            String CorrectAnswerInput = scanner.nextLine();

            // set text

            // Check if the user wants to freeze the correct answer
            if (CorrectAnswerInput.equalsIgnoreCase("freeze")) {
                System.out.println("Correct Answer has been frozen.");
                break;
            }
            if (CorrectAnswerInput.length() == 1) {
                CorrectAnswer = Character.toUpperCase(CorrectAnswerInput.charAt(0));

                if (options.containsKey(CorrectAnswer)) {
                    System.out.println("Enter Correct Answer to change");
                    System.out.println("Enter freeze to Freeze the Correct Answer");
                    ;
                } else {
                    System.out.println("Invalid correct answer. Please choose from the entered options.");
                }

            } else {
                System.out.println("Invalid input. Enter a single character as the correct answer.");
            }
        }
        int marksForCorrect=1;
        int marksForWrong=0;
        System.out.println("Enter marks for the correct answer (default is 1)\nEnter freeze to keep the default value:");
        while (true) {
            String marksGiven = scanner.nextLine();
            // Check if the user wants to freeze the correct answer
            if (marksGiven.equalsIgnoreCase("freeze")) {
                System.out.println("marks for correct answer has been frozen.");
                break;
            }
            int cMarks=Integer.parseInt(marksGiven);
            if (cMarks>=1) {
                marksForCorrect = cMarks;
                System.out.println("Enter marks for the correct answer to change");
                System.out.println("Enter freeze to Freeze the Correct Answer");
            }
            else {
                System.out.println("Invalid input. Please choose an integer >=1 or type freeze.");
            }
        }
        System.out.println("Enter marks for the wrong answer (default is 0)\nEnter freeze to keep the default value:");
        while (true) {
            String marksGiven = scanner.nextLine();
            // Check if the user wants to freeze the correct answer
            if (marksGiven.equalsIgnoreCase("freeze")) {
                System.out.println("marks for wrong answer has been frozen.");
                break;
            }
            int wMarks=Integer.parseInt(marksGiven);
            if (wMarks<=0) {
                marksForWrong = wMarks;
                System.out.println("Enter marks for the wrong answer to change");
                System.out.println("Enter freeze to Freeze the Correct Answer");
            }
            else {
                System.out.println("Invalid input. Please choose an integer <=0 or type freeze.");
            }
        }

        // Set question details to classes.Question class
        newQuestion.setQuestionText(Question_text);
        newQuestion.setCorrectOption(CorrectAnswer);
        newQuestion.setOptions(options);
        newQuestion.setMarksForCorrect(marksForCorrect);
        newQuestion.setMarksForWrong(marksForWrong);
        //Add question to the quiz class
        refQuiz.addQuestions(newQuestion);
    }

}
