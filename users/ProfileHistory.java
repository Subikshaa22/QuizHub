package users;

import attemptedquiz.AttemptQuiz;
import attemptedquiz.AttemptedQuiz;
import QuizClasses.Question;
import QuizClasses.Quiz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ProfileHistory {
    private User user;  // User whose history we are tracking
    private Scanner scanner = new Scanner(System.in);
    private AttemptedQuiz attemptQuiz;

    public ProfileHistory(User user) {
        this.user = user;
    }

    // Display profile history
    public void displayProfileHistory() {
        System.out.println("Username: " + user.getUsername());
        System.out.println("Do you want to see your:\n1. Attempted Quizzes\n2. Created Quizzes\n");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                displayAttemptedQuizzes();
                break;
            case 2:
                displayCreatedQuizzes();
                break;
            //case 3:
                //displayQuizResultsHistory();  // New option for quiz results history
                //break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
                return;
        }
    }

    
    public void displayAttemptedQuizzes() {
        List<AttemptQuiz> quizzes = new ArrayList<>();
        for (Quiz quiz : user.getAttempted()) {
            if (quiz instanceof AttemptQuiz) {
                quizzes.add((AttemptQuiz) quiz);
            }
        }
    
        quizzes.sort(Comparator
                .comparing(AttemptQuiz::getDateAttempted)
                .thenComparing(AttemptQuiz::getTime)
                .reversed());
    
        System.out.println("Quizzes Attempted:");
        /* 
        for (AttemptQuiz quiz : quizzes) {
            System.out.println("Quiz Title: " + quiz.getName());
            System.out.println("Quiz ID: " + quiz.getID());
            System.out.println("Topic: " + quiz.getTopic());
            System.out.println("Date Attempted: " + quiz.getDateAttempted());
            System.out.println("Score: " + quiz.getTotalScore());
            System.out.println("Time Taken: " + quiz.getTime() + " mins");
            System.out.println("--------------");
        }
        */
    
        System.out.println("Available Quiz IDs:");
        List<String> quizIds = extractQuizIdsFromFile();
        for (String id : quizIds) {
            System.out.println(id);  // Print each Quiz ID
        }
    
        System.out.println("Enter the Quiz ID you want to review or type 'exit' to return:");
        String input = scanner.nextLine().trim(); // Trim input to remove extra spaces
    
        if (!input.equalsIgnoreCase("exit")) {
            // Ensure user input matches one of the quiz IDs exactly (trimmed input)
            boolean isValidId = false;
            for (String quizId : quizIds) {
                if (quizId.trim().equals(input)) {
                    isValidId = true;
                    break;
                }
            }
            //if (isValidId) {
                // Proceed with finding the quiz by ID and reviewing
                /*AttemptQuiz selectedQuiz = null;
                for (AttemptQuiz q : quizzes) {
                    if (String.valueOf(q.getID()).trim().equals(input)) {
                        selectedQuiz = q;
                        break;
                    }
                }
                if (selectedQuiz != null) {
                    reviewQuiz(selectedQuiz); // Proceed to review the selected quiz
                } else {
                    System.out.println("Here");
                    System.out.println("Invalid Quiz ID.");
                }
            }
                */
            if (!isValidId) {
                System.out.println("Invalid Quiz ID.");
            }
            else{
                displayQuizResultsHistory();
            }
        }
    }
        
    
    
    
    
    // Extract all quiz IDs from the quiz results file
    private List<String> extractQuizIdsFromFile() {
        List<String> quizIds = new ArrayList<>();
        String fileName = user.getEmail() + "_quiz_results.txt";  // Assuming the file is named after the user’s email
    
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Quiz ID:")) {
                    // Extract the Quiz ID from the line
                    String quizId = line.split("\\|")[0].split(":")[1].trim();
                    quizIds.add(quizId);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading quiz results file: " + e.getMessage());
        }
        return quizIds;
    }
    

    public static List<String> getListOfUserCreated(String userEmail) {
        String fileName = "PreviousQuizzez.csv";
        List<String> userCreatedQuiz = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Read the first line
            if ((line = reader.readLine()) != null) {
                // Split the first line by commas
                String[] parts = line.split(",");

                if (parts[3].equals(userEmail));
                {
                    userCreatedQuiz.add(parts[0]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return userCreatedQuiz;
    }

    public List<Quiz> displayQuizzes(String email) {
        String filePath = "PreviousQuizzez.csv";
        String line;
        //List<String> quizIDs = new ArrayList<>();
        List<Quiz> quizzes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                if (values[3].equalsIgnoreCase(email)) {
                    String quizID = values[0].trim();
                    String quizName = values[1].trim();
                    String quizTopic = values[2].trim();
                    String userMail = values[3].trim();
                    String dateOfCreation = values[4].trim();
                    double avgScore = Double.parseDouble(values[5].trim()); // Average score
                    int avgTimeTaken = Integer.parseInt(values[6].trim()); // Average time taken
                    int timeDuration = Integer.parseInt(values[7].trim()); // Time duration of quiz
                    int numQuestions = Integer.parseInt(values[8].trim()); // Number of questions
                    
                    // Create a new classes.Quiz object
                    Quiz quiz = new AttemptQuiz(quizID, quizName, quizTopic,"", dateOfCreation, avgScore, avgTimeTaken, timeDuration, numQuestions);
                    quizzes.add(quiz); // Add it to the list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    // Display created quizzes
    private void displayCreatedQuizzes() {
        List<Quiz> createdQuizzes = user.getQuizManager().getQuizzesByUser(user.getUsername()); // Retrieve quizzes created by the user

        System.out.println("Created Quizzes:");
        /*for (Quiz quiz : createdQuizzes) {
            System.out.println("Quiz ID: " + quiz.getID());
            System.out.println("Quiz Title: " + quiz.getName());
            System.out.println("Topic: " + quiz.getTopic());
            System.out.println("Date Created: " + quiz.getDateOfCreation());
            System.out.println("Average Score: " + quiz.getAvgScore());
            System.out.println("Average Time: " + quiz.getAvgTime() + " mins");
            System.out.println("--------------");
        }
            */
        List<String> createdIds = getListOfUserCreated(user.getEmail());
        List<Quiz> quizzes = displayQuizzes(user.getEmail());
        for (Quiz quiz : quizzes) {
            System.out.println(quiz); // This will call the toString method of classes.Quiz
        }

        System.out.println("Enter the Quiz ID you want to review or type 'exit' to return:");
        String input = scanner.nextLine().trim(); // Trim input to remove extra spaces

        if (!input.equalsIgnoreCase("exit")) {
            String filepath = null;
            for (String q : createdIds) {
                if (q.equals(input)) {
                    filepath = q + ".csv";
                    break; // Stop looping once the quiz is found
                }
            }

            if (filepath != null) {
                //reviewCreatedQuiz(filepath);
                displayQuestion(filepath);
            } else {
              System.out.println("Invalid Quiz ID.");
            }
        }
    }

    // Display quiz results history by reading from file
    private void displayQuizResultsHistory() {
        String fileName = user.getEmail() + "_quiz_results.txt";  // Construct the file name
        List<String> quizDetails = readQuizResults(fileName);

        if (quizDetails.isEmpty()) {
            System.out.println("No quiz results found for " + user.getEmail());
        } else {
            String currentQuizId = "";
            List<String> currentQuizDetails = new ArrayList<>();

            for (String line : quizDetails) {
                // Check for quiz ID and attempt date (e.g., "classes.Quiz ID: 743204495 | classes.Quiz Attempted: Tue Nov 26 20:45:50 IST 2024")
                if (line.startsWith("Quiz ID:")) {
                    if (!currentQuizDetails.isEmpty()) {
                        // Print the previous quiz details before processing the next one
                        printQuizDetails(currentQuizId, currentQuizDetails);
                        currentQuizDetails.clear();  // Clear the details for the next quiz
                    }
                    currentQuizId = line.split("\\|")[0].split(":")[1].trim();  // Extract Quiz ID
                }
                currentQuizDetails.add(line);  // Add current line to the quiz details
            }

            // Print the last quiz details if any
            if (!currentQuizDetails.isEmpty()) {
                printQuizDetails(currentQuizId, currentQuizDetails);
            }
        }
    }

    // Read quiz results from the file
    private List<String> readQuizResults(String fileName) {
        List<String> quizDetails = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                quizDetails.add(line);  // Read and store each line of the quiz result
            }
        } catch (IOException e) {
            System.out.println("Error reading quiz results file: " + e.getMessage());
        }
        return quizDetails;
    }

    // Print quiz details
    private void printQuizDetails(String quizId, List<String> quizDetails) {
        System.out.println("Quiz ID: " + quizId);

        // Print the quiz info (date, attempt, etc.)
        for (String detail : quizDetails) {
            if (detail.startsWith("Quiz Attempted:")) {
                System.out.println("Date Attempted: " + detail.split(":")[1].trim());
            } 
            else if (detail.contains("?")) {
                System.out.print("Question: " );
                String[] answers = detail.split(",");
                for (String answer : answers) {
                    System.out.println(answer.trim());
                }
            }

            else if (detail.startsWith("Quiz ID:"))
            {
                continue;
            }

            else if (detail.equals("------------------------------------------------"))
            {
                break;
            }

            else if (detail.startsWith("Total Score :"))
            {
                System.out.println("Total Score: " + detail.split(":")[1].trim());
            }

            else{
                String[] answers = detail.split(",");
                int i=0;
                for (String answer : answers) {
                    if (i>=3)
                    {
                        break;
                    }
                    if (i==0) {
                        if (answer.equals("-1")){
                            System.out.println("Not attempted");
                        }
                        else {
                            System.out.println("Your answer: " + answer.trim());
                        } 
                    }
                    else if (i==1) {
                        System.out.println("Correct answer: " + answer.trim());
                    }
                    else {
                        System.out.println("Marks scored: " + answer.trim());
                    }
                    i++;
                }
            }

        }

        System.out.println("--------------");
    }

    // Review a selected quiz (for attempted quizzes)
    /*private void reviewQuiz(AttemptQuiz quiz) {
        System.out.println("Options: [review] or [back]");
        String option = scanner.nextLine();

        if (option.equalsIgnoreCase("review")) {
            displayFilterOptions(quiz);
        }
    }
        */

    private void displayQuestion(String fileName)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            line = br.readLine();
            String[] header_line= line.split(",");
            int noOfQuestions = Integer.parseInt(header_line[8]);
            System.out.println("\nTotal no of questions: " + noOfQuestions + "\n");
            int j=0;

            while (j<noOfQuestions) {
                line = br.readLine();
                String[] values = line.split(",");

                // Parse the question and options
                String text = values[0].trim();
                int numOfOptions = Integer.parseInt(values[1].trim());  // Get the number of options
                Map<Character, String> options = new HashMap<>();
                // Parsing options from the CSV (A, B, C, D, etc.)
                for (int im = 0; im < numOfOptions; im++) {
                    char optionLabel = (char) ('A' + im);  // 'a', 'b', 'c', ...
                    options.put(optionLabel, values[1 + im * 2].trim());  // Text for each option
                }
                // Correct option, assuming it's the last value
                Character correctOption = values[2 + numOfOptions * 2].trim().charAt(0);
                int marksForCorrect = 0, marksForWrong = 0;
                try {
                    marksForCorrect = Integer.parseInt(values[2 + numOfOptions * 2 + 1].trim());
                    marksForWrong = Integer.parseInt(values[2 + numOfOptions * 2 + 2].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing numerical values for question: " + text);
                    continue;
                }

                System.out.println("Question: " + text + "\n" + "Options: " + options + "\n" + "Correct option: " + correctOption + "\n" + "Marks for correct option: " + marksForCorrect + "\n" + "Marks for wrong option: " + marksForWrong + "\n" );
                j++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Review a selected quiz (for created quizzes)
    /*private void reviewCreatedQuiz(String filepath) {
        List<Question> questions = quiz.getQuestions();
        int index = 0;
        while (index >= 0 && index < questions.size()) {
            Question question = questions.get(index);
            System.out.println("Question: " + question.getQuestionText());
            System.out.println("Options:");
            for (Map.Entry<Character, String> entry : question.getOptions().entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue());
            }
            System.out.println("Your Answer: " + (question.getAnswer() != null ? question.getAnswer() : "Not answered"));
            System.out.println("Correct Answer: " + question.getCorrectOption());

            String action = scanner.nextLine();

            switch (action.toLowerCase()) {
                case "next":
                    if (index < questions.size() - 1) {
                        index++;
                    } else {
                        System.out.println("This is the last question.");
                    }
                    break;
                case "previous":
                    if (index > 0) {
                        index--;
                    } else {
                        System.out.println("This is the first question.");
                    }
                    break;
                case "end review":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }*/

    // Display filter options for reviewing questions
    private void displayFilterOptions(AttemptQuiz quiz) {
        System.out.println("Filter by: [incorrect] [unattempted] [all]");
        String option = scanner.nextLine();

        switch (option.toLowerCase()) {
            case "incorrect":
                reviewQuestions(quiz, getIncorrectQuestions(quiz), "Incorrect");
                break;
            case "unattempted":
                reviewQuestions(quiz, getUnattemptedQuestions(quiz), "Unattempted");
                break;
            case "all":
                reviewQuestions(quiz, quiz.getQuestions(), "All");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    // Get incorrect questions
    private List<Question> getIncorrectQuestions(AttemptQuiz quiz) {
        List<Question> incorrectQuestions = new ArrayList<>();
        for (Question question : quiz.getQuestions()) {
            if (!question.getAnswer().equals(question.getCorrectOption())) {
                incorrectQuestions.add(question);
            }
        }
        return incorrectQuestions;
    }

    // Get unattempted questions
    private List<Question> getUnattemptedQuestions(AttemptQuiz quiz) {
        List<Question> unattemptedQuestions = new ArrayList<>();
        for (Question question : quiz.getQuestions()) {
            if (question.getAnswer() == null) {
                unattemptedQuestions.add(question);
            }
        }
        return unattemptedQuestions;
    }

    // Review filtered questions
    private void reviewQuestions(AttemptQuiz quiz, List<Question> questions, String filterOption) {
        System.out.println("Reviewing " + filterOption + " questions.");

        int index = 0;
        while (index >= 0 && index < questions.size()) {
            Question question = questions.get(index);
            System.out.println("Question: " + question.getQuestionText());
            System.out.println("Your Answer: " + question.getAnswer());
            System.out.println("Correct Answer: " + question.getCorrectOption());

            System.out.println("Options: [next] [previous] [end review]");
            String action = scanner.nextLine();

            switch (action.toLowerCase()) {
                case "next":
                    if (index < questions.size() - 1) {
                        index++;
                    } else {
                        System.out.println("This is the last question.");
                    }
                    break;
                case "previous":
                    if (index > 0) {
                        index--;
                    } else {
                        System.out.println("This is the first question.");
                    }
                    break;
                case "end review":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private void displayQuestionOptions(Question question, String filterOption) {
        System.out.println("Question: " + question.getQuestionText());
        System.out.println("Options: ");
        for (int i = 0; i < question.getOptions().size(); i++) {
            System.out.println((char) ('A' + i) + ": " + question.getOptions().get(i));
        }
        System.out.println("Filter: " + filterOption);
        System.out.println("Available Actions: [show answer] [next] [previous] [end review]");
    }

    private void showAnswer(Question question, AttemptQuiz quiz, String filterOption) {
        System.out.println("Correct Answer: " + question.getCorrectOption());
        System.out.println("Your Answer: " + quiz.getChosenOptions().get(quiz.getQuestions().indexOf(question)));
        System.out.println("Score: " + question.getScore());
        System.out.println("Filter: " + filterOption);
    }
}

/*
 
    # 1. Compile all Java files and place the output in the bin directory.
javac -d bin $(find . -name "*.java")

# 2. Generate JNI headers for MakeQuiz.java and place them in the bin/makequiz directory.
javac -h bin/makequiz makequiz/MakeQuiz.java

# 3. Generate JNI headers for ReviewQuiz.java and place them in the bin/makequiz directory.
javac -h bin/makequiz makequiz/ReviewQuiz.java

#4
export JAVA_HOME=$(/usr/libexec/java_home -v 17)

# 5. Compile the C++ code into a shared library (libmylib.dylib) for macOS.
g++ -std=c++11 -shared -fpic -o libmylib.dylib \
makequiz/TopicFunctions.cpp \
makequiz/MakeQuizFile.cpp \
makequiz/getCurrentDate.cpp \
-I"$JAVA_HOME/include" \
-I"$JAVA_HOME/include/darwin" \
-I"bin/makequiz"

# 6. Run the Java application, setting the library path to the current directory.
java -Djava.library.path=$(pwd) -cp bin quizEngine.QuizPlatform

 */