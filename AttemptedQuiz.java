import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;


public class AttemptedQuiz extends Quiz{

    // Display all available topics
    public String displayTopics() {
        String filePath = "Topics.csv"; // Path to the topics file
        String line;
        Map<String, String> topics = new HashMap<>(); // Map to store TopicID and TopicName
    
        // Reading topics from the file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\n");
                if (values.length == 2) {
                    topics.put(values[0].trim(), values[1].trim());
                } else {
                    System.out.println("Skipping malformed line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading topics file: " + e.getMessage());
        }
    
        if (topics.isEmpty()) {
            System.out.println("No topics available.");
            return "BACK";
        }
    
        // Display the topics
        System.out.println("Available Topics:");
        for (Map.Entry<String, String> entry : topics.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
        System.out.println("If the topic you want is not available, type BACK to go to the main menu.");
    
        // Get user input
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
    
        // Validate user input
        if (topics.containsKey(userInput)) {
            return topics.get(userInput); // Return the selected topic name
        } else if (userInput.equalsIgnoreCase("BACK")) {
            return "BACK";
        } else {
            System.out.println("Invalid input. Please try again.");
            return displayTopics(); // Recursively call to allow retry
        }
    }
    

    // Display quizzes for the selected topic
    public List<String> displayQuizzes(String topicID) {
        String filePath = "PreviousQuizzez.csv";
        String line;
        List<String> quizIDs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[1].equalsIgnoreCase(topicID)) {
                    quizIDs.add(values[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quizIDs;
    }

    AttemptQuiz quiz = new AttemptQuiz();

    List<Question> questions = new ArrayList<>();
    List<String> chosenOptions = new ArrayList<>(Collections.nCopies(4, "-1"));
    int timeLimit;
    boolean quizSubmitted = false;
    int currentQuestionIndex = 0;

    // Load questions from a CSV file
    public void loadQuestions(String filePath) {
        questions.clear(); // Clear existing questions before loading new ones
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {

            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String quizId = values[0];
                String name = values[1];
                String text = values[0].trim();
                Map<Character, String> options = new HashMap<>();
                options.put('a', values[1].trim());
                options.put('b', values[2].trim());
                options.put('c', values[3].trim());
                options.put('d', values[4].trim());

                // Retrieve correctOption as a Character
                Character correctOption = values[5].trim().charAt(0);
                int marksForCorrect = 0, marksForWrong = 0;
                double averageScore = 0.0;

                try {
                    marksForCorrect = Integer.parseInt(values[6].trim());
                    marksForWrong = Integer.parseInt(values[7].trim());
                    averageScore = Double.parseDouble(values[8].trim());
                    this.timeLimit = Integer.parseInt(values[9].trim()); // Store time limit
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing numerical values for question: " + text);
                    continue;
                }

                questions.add(new Question(text, options, correctOption, marksForCorrect, marksForWrong, averageScore));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (questions.isEmpty()) {
            System.out.println("No questions loaded from the file.");
        } else {
            System.out.println("Questions loaded successfully.");
        }
    }
    
    public void displayQuestion(int questionIndex) {
        if (questionIndex < 0 || questionIndex >= questions.size()) {
            System.out.println("Invalid question index.");
            return;
        }
        
        Question currentQuestion = questions.get(questionIndex);
        System.out.println("Question " + (questionIndex + 1) + ": " + currentQuestion.getText());
        System.out.println("Options:");
        for (Map.Entry<Character, String> entry : currentQuestion.getOptions().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Choose an option (a, b, c, d), 'n' for next, 'p' for previous, 's' to submit, or enter a question number to jump.");
    }

    // Start the quiz
    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!quizSubmitted) {
                    System.out.println("Time is up!");
                    quizSubmitted = true;
                }
                timer.cancel();
            }
        }, timeLimit * 1000);

        while (!quizSubmitted) {
            
            displayQuestion(currentQuestionIndex);
            System.out.println("Enter your choice (a, b, c, d), 'n' for next, 'p' for previous, 's' to submit, or question number to jump:");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("n")) {
                currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
            } else if (input.equals("p")) {
                currentQuestionIndex = (currentQuestionIndex - 1 + questions.size()) % questions.size();
            } else if (input.equals("s")) {
                quizSubmitted = true;
                System.out.println("Quiz submitted successfully!");
                break;
            } else if (input.matches("[abcd]")) {
                chosenOptions.set(currentQuestionIndex, input);
            } else {
                try {
                    int questionNumber = Integer.parseInt(input);
                    if (questionNumber > 0 && questionNumber <= questions.size()) {
                        currentQuestionIndex = questionNumber - 1;
                    } else {
                        System.out.println("Invalid question number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please try again.");
                }
            }
        }
        QuizPlatform quizPlatform = new QuizPlatform();
        String email = quizPlatform.currentUser.getEmail();
        quizPlatform.currentUser.setAttempted()
        saveQuizResults(email);        
    }

    public void saveQuizResults(String userEmail) {
        String userFile = userEmail + "_quiz_results.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            writer.write("Quiz Attempted: " + new Date() + "\n");
            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                String chosenOption = chosenOptions.get(i);
                boolean isCorrect = question.getCorrectOption().toString().equals(chosenOption);
                int score = isCorrect ? question.getMarksForCorrect() : question.getMarksForWrong();
                writer.write("Question: " + question.getText() + "\n");
                writer.write("Options: " + question.getOptions() + "\n");
                writer.write("Chosen Option: " + chosenOption + ", Correct Option: " + question.getCorrectOption() + "\n");
                writer.write("Score: " + score + "\n");
            }
            writer.write("Time Taken: " + timeLimit + " seconds\n\n");
            System.out.println("Quiz results saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Main function to select topic, quiz, and start the quiz
    public void main() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("The available topics are:");
        String topicID = displayTopics();
        if (topicID.equalsIgnoreCase("BACK")) {
            System.out.println("Returning to main menu...");
            return;
        }

        System.out.println("Available quizzes under the selected topic:");
        List<String> quizIDs = displayQuizzes(topicID);

        if (!quizIDs.isEmpty()) {
            for (String quizID : quizIDs) {
                System.out.println(quizID);
            }

            System.out.print("Enter a Quiz ID from the list above: ");
            String quizID = scanner.nextLine();

            if (quizIDs.contains(quizID)) {
                System.out.println("You selected Quiz ID: " + quizID);
                String filename = quizID + ".csv";
                loadQuestions(filename);
                startQuiz();
            } else {
                System.out.println("Invalid Quiz ID entered.");
            }
        } else {
            System.out.println("No quizzes found for the selected topic.");
        }
    }
    public static void main(String[] args) {
        AttemptedQuiz attemptQuiz = new AttemptedQuiz();
        attemptQuiz.main();
    }
}

