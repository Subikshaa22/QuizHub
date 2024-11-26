package makequiz;
import java.util.*;

public class MakeQuiz {

    // Static Scanner instance for taking user input
    static Scanner scanner = new Scanner(System.in);

    // Create a Quiz Object representing the new Quiz being created
    // Each MakeQuiz instance will have its own newQuiz instance 
    ExistingQuizzes newQuiz = new ExistingQuizzes();

    // Declare the native functions  to interact with C++ library
    public native boolean checkIfTopicExists(String topic);
    public native int saveTopic(String topic);
    public native int displayTopics();
    public native String getCurrentDate();

    // Load the native library to access the C++ functions 
    static {
        System.loadLibrary("mylib");
    }


    // Method to add questions to the quiz.
    public static void callEnterQuestion(MakeQuiz makeQuizInstance) {
        // Access newQuiz through makeQuizInstance instance and the functions from enterQuestion class
        EnterQuestion callEnterQuestion = new EnterQuestion();
        callEnterQuestion.setQobject(makeQuizInstance.newQuiz);
        callEnterQuestion.EnterQuestions();
    }

    // Method to edit existing questions in the quiz.
    public static void callEditQuestion(MakeQuiz makeQuizInstance) {
        // Access newQuiz through makeQuizInstance instance and the functions from editQuestion class
        EditQuestion callEditQuestion = new EditQuestion();
        callEditQuestion.setQobject(makeQuizInstance.newQuiz);
        callEditQuestion.editQuestion();
    }

    // Method to review the quiz before finalizing it.
    public static void callReview(MakeQuiz makeQuizInstance, String quizID,String name,String topic, String date) {
        // Access newQuiz through makeQuizInstance instance and the functions from reviewQuiz class
        ReviewQuiz callReview = new ReviewQuiz();
        callReview.setQobject(makeQuizInstance.newQuiz);
        // Convert the id to a integer and send it to function
        int quizIdInt = Integer.parseInt(quizID);
        callReview.reviewQ(quizIdInt, name, topic, date);
    }

    public static void main(String[] args) {
        String topic = ""; // Variable to store the topic of the quiz

        // Outer loop for selecting or creating a topic
        OUTER: for (int i = 0; i < 5; i++) {
            System.out.println("The following topics are available:");

            // Create a new instance of MakeQuiz to access cpp function to read and display the available topics
            MakeQuiz quizInstance = new MakeQuiz();
            quizInstance.displayTopics();

            System.out.println("Choose an option:");
            System.out.println("1. Select a topic ");
            System.out.println("2. Create a new topic ");

            // Check if an integer is input
            int choice = -1;
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.print("Enter a number: ");
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    validInput = true; // If no exception, input is valid
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }


            // Make decisions based on users choice
            switch (choice) {
                case 1: {  // Select a topic already in database
                    System.out.println("Enter topic selected: ");
                    topic = scanner.nextLine();

                    // Check if the topic exists using native method
                    boolean exists = true;
                    exists = quizInstance.checkIfTopicExists(topic);
                    if (exists == true) {
                        break OUTER; // If topic exists, exit the loop
                    }
                    else {
                        System.out.println("Invalid Topic");
                        continue OUTER; // Continue to next iteration if topic doesn't exist
                    }
                }
                case 2: { // Create a new topic and add it to database
                    System.out.println("Enter new topic: ");
                    topic = scanner.nextLine();

                    // Save the new topic using native method
                    quizInstance.saveTopic(topic);
                    break OUTER; // Exit the loop after creating a new topic
                }
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } // End of OUTER

        // Generate a random unque quiz ID and display it to user
        Random random = new Random();
        int ID = random.nextInt(Integer.MAX_VALUE); // any positive int
        System.out.println(ID + " is your Quiz id! ");
        String quizID = String.valueOf(ID);

        // Get the current date using the native method
        MakeQuiz quizInstance = new MakeQuiz();
        String dateCreated = quizInstance.getCurrentDate();
        System.out.println("Today's date is :" + dateCreated);

        // Start Forming Quiz
        System.out.println("Enter Quiz name:");
        String name = scanner.nextLine();

        // Boolean flag to track if the quiz creation is complete
        boolean finishMakingQuiz = false;

        // Create an instance of MakeQuiz
        MakeQuiz makeQuizInstance = new MakeQuiz();

        // Loop to handle quiz creation options
        COUTER: while (finishMakingQuiz != true) {
            System.out.println("Choose an Option:");
            System.out.println("1. Enter new Question");
            System.out.println("2. Edit Existing Question");
            System.out.println("3. Finish Questions");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1: {
                    // Add a new Question
                    callEnterQuestion(makeQuizInstance );
                    break;
                }
                case 2: {
                    // Edit a existing question 
                    callEditQuestion(makeQuizInstance);
                    break;
                }
                case 3: {
                    // Review the quiz and save/discard it 
                    callReview(makeQuizInstance, quizID, name,topic, dateCreated);
                    break COUTER;
                }
                default:
                    System.out.println("Invalid choice , try again! ");
            }
        } // End of COUTER
    } // End of main
} // End of class
