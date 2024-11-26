package makequiz;
import java.util.*;

// Class to handle editing questions in a quiz
public class EditQuestion {

    // Static scanner instance for user input
    static Scanner scanner = new Scanner(System.in);
   
    // Reference to the ExistingQuizzes object (newQuiz)
    private ExistingQuizzes quiz;

    // Default Constructior 
    public EditQuestion() {
    }

    // Method to set the reference to the quiz object
    public void setQobject(ExistingQuizzes refQuiz) {
        this.quiz = refQuiz;
    }

    // Method to edit questions in the quiz
    public void editQuestion() {
        while (true) {

            // Display instructions to the user
            System.out.println("\nEnter the question number to edit (1 to " + quiz.getQuestions().size() + "):");
            System.out.println("Enter 0 to exit.");

            int questionNo = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (questionNo == 0) {
                break; // Exit the loop
            }

            // Check if input in within range
            if (questionNo < 1 || questionNo > quiz.getQuestions().size()) {
                System.out.println("Invalid question number, please try again!");
                continue;
            }

            // Fetch the selected question and display it
            System.out.println("Selected Question: " + quiz.getQuestions().get(questionNo - 1));
            System.out.println("Options:");
            Map<Character, String> currentOptions = quiz.getQuestions().get(questionNo - 1).getOptions();
            for (Map.Entry<Character, String> entry : currentOptions.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            // Display edit menu
            System.out.println("\nChoose an option:");
            System.out.println("1. Edit question text");
            System.out.println("2. Edit question options");
            System.out.println("3. Freeze question, no changes needed");

            // Handle user choice
            int userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (userChoice == 1) {
                // Edit the question text
                System.out.println("Enter the new question text:");
                String new_text = scanner.nextLine();
                quiz.getQuestions().get(questionNo - 1).setQuestionText(new_text);
                System.out.println("Question text updated successfully.");

            } else if (userChoice == 2) {
                // Edit the question options
                while (true) {
                    System.out.println("\nCurrent Options:");
                    for (Map.Entry<Character, String> entry : currentOptions.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }

                    System.out.println("Choose an option label to edit or type 'F' to finish:");
                    String choice = scanner.nextLine().toUpperCase();

                    if (choice.equals("F")) {
                        System.out.println("Finished editing options.");
                        break;
                    }

                    if (choice.length() != 1 || !currentOptions.containsKey(choice.charAt(0))) {
                        System.out.println("Invalid option choice, please try again.");
                        continue;
                    }

                    // Edit the selected option
                    char optionKey = choice.charAt(0);
                    System.out.println("Enter new text for option " + optionKey + ":");
                    String newOptionText = scanner.nextLine();
                    currentOptions.put(optionKey, newOptionText);
                    System.out.println("Option " + optionKey + " updated successfully.");
                    }
            } else if (userChoice == 3) {
                System.out.println("Question frozen, no changes made.");
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
        // Indicate that editing is complete
        System.out.println("Editing completed.");
    }
}