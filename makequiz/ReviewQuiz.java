package makequiz;
import java.util.*;

public class ReviewQuiz {
    // Declare the native functions  to interact with C++ library
    public native int MakeIQuizFile(ExistingQuizzes refQuiz, String filename, int quizID, String name, String topic, String username, int time, int q, String date);
    public native int WriteToQuizFile(ExistingQuizzes refQuiz, String filename);
    public native int AddToPrevQuizFile(ExistingQuizzes refQuiz);

    // Load the native library to access the C++ functions 
    static {
        System.loadLibrary("mylib");
    }

    // Default constructor 
    public ReviewQuiz() {
    }

    // Static Scanner instance for taking user input
    static Scanner scanner = new Scanner(System.in);
    
    // Hold the newQuiz object created in MakeQuiz 
    private ExistingQuizzes refQuiz;

    // Method to set the reference to the quiz object
    public void setQobject(ExistingQuizzes refQuiz) {
        this.refQuiz = refQuiz;
    }

    // Method to review and finalize the quiz.
    public void reviewQ(int quizID, String name,String topic ,String date) {
        int time = 0; // Variable to hold the time for the quiz in minutes
        int noOfQues = refQuiz.questions.size(); // Get the number of questions in the quiz
        // Set the number of questions in the quiz object
        refQuiz.setNumberOfQuestions(noOfQues);

        // Ask for time duration of the Quiz 
        System.out.println("Enter the time duration for the quiz in minutes:");
        time = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()

        int choice = -1; // Store menu choice 
        OUTER: while (choice != 4) {
            System.out.println("Choose an option:");
            System.out.println("1. Edit Questions ");
            System.out.println("2. Save Quiz ");
            System.out.println("3. Discard Quiz ");
            System.out.print("Enter a number: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline char

            switch (choice) {
                case 1: {
                    // Edit the questions in the quiz
                    EditQuestion callEditQuestion = new EditQuestion();
                    callEditQuestion.setQobject(refQuiz);
                    callEditQuestion.editQuestion();
                    break;
                }
                case 2: {
                    // Create a filename for the quiz based on its ID and extension
                    String quizFilename = String.valueOf(quizID);
                    String fileExtension = ".csv";
                    String filename = quizFilename + fileExtension;
                    
                    // Create an instance of the review class to interact with C++ functions
                    ReviewQuiz callCppFunctions = new ReviewQuiz();

                    // Get the username 
                    String user = refQuiz.getUsername();
                  
                    // Make and write to quiz file using cpp functions 
                    callCppFunctions.MakeIQuizFile(refQuiz, filename, quizID, name, topic, user , time, noOfQues, date);
                    callCppFunctions.WriteToQuizFile(refQuiz, filename);
                    callCppFunctions.AddToPrevQuizFile(refQuiz);
                    choice = 4; // Exit the loop
                    break OUTER; // Exit the outer loop and finish the review
                }
                case 3: {
                    // Discard the quiz after confirming with user 
                    System.out.println("Are you sure you want to discard the quiz?");
                    System.out.println("1. Yes ");
                    System.out.println("2. No ");

                    // Check if int is entered 
                    int dis = -1;
                    boolean disValidInput = false;
                    while (!disValidInput) {
                        try {
                            System.out.print("Enter a number: ");
                            choice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline character 
                            disValidInput = true; // If no exception, input is valid
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            scanner.nextLine(); // Clear the invalid input
                        }
                    }

                    switch (dis) {
                        case 1: {
                            return; // Go back to continue learning main menu 
                        }
                        case 2: {
                            continue OUTER; // Invalid input 
                        }
                    }
                }
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}
