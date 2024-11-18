import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class AttemptedQuiz extends Quiz{

    private Map<String, String> topics = new HashMap<>();
    // Display all available topics
    public String displayTopics() {
        String filePath = "Topics.csv"; // Path to the topics file
        String line;
        topics.clear(); // Clear existing topics before loading new ones

        // Reading topics from the file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int index = 1;  // Start numbering topics from 1
            while ((line = br.readLine()) != null) {
                line = line.trim();  // Trim any extra spaces or newlines
                if (!line.isEmpty()) {  // Skip empty lines
                    topics.put(String.valueOf(index), line);  // Use an index as the key, and topic name as the value
                    index++;  // Increment the index for the next topic
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
        String userInput = scanner.nextLine().trim();

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

    public static List<String> getWordsFromFirstLine(String filePath) {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the first line of the file
            String line = reader.readLine();

            // If the first line exists, split it by commas (assuming CSV format)
            if (line != null) {
                String[] columns = line.split(",");  // Split by comma
                for (String column : columns) {
                    words.add(column.trim());  // Add each word/column to the list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  // Handle IOExceptions (e.g., file not found)
        }

        return words;  // Return the list of words (columns)
    }

    // Display quizzes for the selected topic
    public List<String> displayQuizzes(String topicID) {
        String filePath = "PreviousQuizzez.csv";
        String line;
        List<String> quizIDs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[2].equalsIgnoreCase(topicID)) {
                    quizIDs.add(values[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quizIDs;
    }

    List<Question> questions = new ArrayList<>();
    List<String> chosenOptions = new ArrayList<>(Collections.nCopies(4, "-1"));
    int timeLimit;
    boolean quizSubmitted = false;
    int currentQuestionIndex = 0;

    // Function to return a list of QuizIDs 
    public static List<String> extractQuizIds(String filePath) {
        List<String> quizIds = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Regex pattern to match lines containing the Quiz ID
            Pattern quizIdPattern = Pattern.compile("Quiz ID: (\\d+)");
            
            while ((line = reader.readLine()) != null) {
                // Check if the line contains "Quiz ID:"
                Matcher matcher = quizIdPattern.matcher(line);
                if (matcher.find()) {
                    // If a match is found, extract the quiz ID
                    quizIds.add(matcher.group(1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quizIds;
    }

    // Load questions from a CSV file
    public void loadQuestions(String filePath) {
        questions.clear(); // Clear existing questions before loading new ones
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the header line
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 3) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                // Parse the question and options
                String text = values[0].trim();
                int numOfOptions = Integer.parseInt(values[1].trim());  // Get the number of options
                Map<Character, String> options = new HashMap<>();

                // Parsing options from the CSV (A, B, C, D, etc.)
                for (int i = 0; i < numOfOptions; i++) {
                    char optionLabel = (char) ('A' + i);  // 'a', 'b', 'c', ...
                    options.put(optionLabel, values[1 + i * 2].trim());  // Text for each option
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

                questions.add(new Question(text, options, correctOption, marksForCorrect, marksForWrong));
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

        System.out.println("Choose an option (A,B,C,D, ...), 'n' for next, 'p' for previous, 's' to submit, or enter a question number to jump.");
    }
    // Start the quiz
    public void startQuiz(String quizID) {
        Scanner scanner = new Scanner(System.in);

        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!quizSubmitted) {
                    System.out.println("Time is up!");
                    quizSubmitted = true;
                }
                timer.cancel();
            }
        }, timeLimit * 1000);*/

        /*long startTime = System.currentTimeMillis(); // Record the start time
        long duration = 30 * 1000; // Duration in milliseconds (30 seconds)*/

        while (!quizSubmitted) {
            // Check if the time limit has been reached
            /*if (System.currentTimeMillis() - startTime > duration) {
                System.out.println("Time is up! The quiz is being submitted automatically.");
                quizSubmitted = true;
                break;
            }*/

            displayQuestion(currentQuestionIndex);
            String input = scanner.nextLine().trim();

            if (input.equals("n")) {
                currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
            } else if (input.equals("p")) {
                currentQuestionIndex = (currentQuestionIndex - 1 + questions.size()) % questions.size();
            } else if (input.equals("s")) {
                quizSubmitted = true;
                System.out.println("Quiz submitted successfully!");
                break;
            } else if (input.matches("[QWERTYUIOPLKJHGFDSAZXCBVNM]")) {
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
        saveQuizResults(email,quizID);
    }

    public void saveQuizResults(String userEmail, String quizID) {
        String userFile = userEmail + "_quiz_results.txt";

        // Open the file in append mode
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {

            // Write Quiz ID and Attempt Time
            writer.write("Quiz ID: " + quizID + " | Quiz Attempted: " + new Date() + "\n");

            // Loop through each question and write the result
            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                String chosenOption = chosenOptions.get(i);
                boolean isCorrect = question.getCorrectOption().toString().equals(chosenOption);
                int score = isCorrect ? question.getMarksForCorrect() : question.getMarksForWrong();

                writer.write(question.getText() + "," + question.getOptions() + "," + chosenOption + ", " + question.getCorrectOption() + "," + score + "\n");
            }

            // Optionally, write a line to indicate the end of this particular quiz attempt
            writer.write("------------------------------------------------\n\n");

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
            //System.out.println(QuizPlatform.currentUser.getAttempted());
            int alreadyAttempted = 0;
            QuizPlatform quizPlatform = new QuizPlatform();
            Path filePath1 = Paths.get(quizPlatform.currentUser.getEmail()+"_quiz_results.txt");
            // Check if the file exists
            if (Files.exists(filePath1))
            {
                List<String> quizIds = extractQuizIds(quizPlatform.currentUser.getEmail()+"_quiz_results.txt");

                for (String i:quizIds)
                {
                    if (i.equals(quizID))
                    {
                        alreadyAttempted = 1;
                        break;
                    }
                }
            }

            if (alreadyAttempted == 1)
            {
                System.out.println("You have already attempted this quiz.");
            }

            else if (quizIDs.contains(quizID)) {
                System.out.println("You selected Quiz ID: " + quizID);
                String filePath = quizID+".csv";  // Path to your CSV file
                List<String> attributes = getWordsFromFirstLine(filePath);
                AttemptQuiz attemptQuiz = new AttemptQuiz(Integer.parseInt(quizID), attributes.get(1), attributes.get(2), attributes.get(3), attributes.get(4), Double.parseDouble(attributes.get(5)), Double.parseDouble(attributes.get(6)), Integer.parseInt(attributes.get(7)), Integer.parseInt(attributes.get(8)));
                QuizPlatform.currentUser.getAttempted().add(attemptQuiz);
                loadQuestions(quizID+".csv");
                startQuiz(quizID);
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