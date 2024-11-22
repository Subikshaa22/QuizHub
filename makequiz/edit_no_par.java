package makequiz;

import java.util.*;

public class edit_no_par {
    static Scanner scanner = new Scanner(System.in);
    
    //static makequiz.ExistingQuizzes quiz = new makequiz.ExistingQuizzes();
   
    private ExistingQuizzes quiz;

    public void setQobject(ExistingQuizzes refQuiz)
    {
        this.quiz = refQuiz;
    }

    //remove parameters
    public void editQuestion() {
        while (true) {
            
            System.out.println("\nEnter the question number to edit (1 to " + quiz.getQuestions().size() + "):");
            System.out.println("Enter 0 to exit.");
            int question_no = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (question_no == 0) {
                break; // Exit the loop
            }

            if (question_no < 1 || question_no > quiz.getQuestions().size()) {
                System.out.println("Invalid question number, please try again.");
                continue;
            }

            // Display the selected question
            System.out.println("Selected classes.Question: " + quiz.getQuestions().get(question_no - 1));
            System.out.println("Options:");
            Map<Character, String> currentOptions = quiz.getQuestions().get(question_no - 1).getOptions();
            for (Map.Entry<Character, String> entry : currentOptions.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            // Edit options
            System.out.println("\nChoose an option:");
            System.out.println("1. Edit question text");
            System.out.println("2. Edit question options");
            System.out.println("3. Freeze question, no changes needed");

            int user_choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (user_choice == 1) {
                // Edit the question text
                System.out.println("Enter the new question text:");
                String new_text = scanner.nextLine();
                quiz.getQuestions().get(question_no - 1).setQuestionText(new_text);
                System.out.println("classes.Question text updated successfully.");

            } else if (user_choice == 2) {
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
            } else if (user_choice == 3) {
                System.out.println("classes.Question frozen, no changes made.");
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }

        System.out.println("Editing completed.");
    }

}
