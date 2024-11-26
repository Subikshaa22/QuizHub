package attemptedquiz;
import quizEngine.QuizPlatform;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import QuizClasses.Question;
import QuizClasses.Quiz;

public class AttemptedQuiz extends Quiz {

    private Map<String, String> topics = new HashMap<>();
    private List<String> quizIDs = new ArrayList<>();

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
    public List<Quiz> displayQuizzes(String topicID) {
        String filePath = "PreviousQuizzez.csv";
        String line;
        //List<String> quizIDs = new ArrayList<>();
        List<Quiz> quizzes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                
                if (values[2].equalsIgnoreCase(topicID)) {
                    String quizID = values[0].trim();
                    String quizName = values[1].trim();
                    String quizTopic = values[2].trim();
                    String dateOfCreation = values[3].trim();
                    int avgScore = Integer.parseInt(values[4].trim()); // Average score
                    int avgTimeTaken = Integer.parseInt(values[5].trim()); // Average time taken
                    int timeDuration = Integer.parseInt(values[6].trim()); // Time duration of quiz
                    int numQuestions = Integer.parseInt(values[7].trim()); // Number of questions
                    
                    // Create a new classes.Quiz object
                    Quiz quiz = new AttemptQuiz(quizID, quizName, quizTopic,"", dateOfCreation, avgScore, avgTimeTaken, timeDuration, numQuestions);
                    quizzes.add(quiz); // Add it to the list
                    quizIDs.add(values[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quizzes;
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
            // Regex pattern to match lines containing the classes.Quiz ID
            Pattern quizIdPattern = Pattern.compile("Quiz ID: (\\d+)");
            
            while ((line = reader.readLine()) != null) {
                // Check if the line contains "classes.Quiz ID:"
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
    public void startQuiz(String quizID, int duration) {
        Scanner scanner = new Scanner(System.in);
        Timer timer = new Timer();
        quizSubmitted = false; // Reset quiz submission flag
    
        // Schedule the timer task to submit the quiz after 30 seconds
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!quizSubmitted) {
                    System.out.println("\nTime is up! The quiz is being submitted automatically.");
                    quizSubmitted = true;
                    QuizPlatform quizPlatform = new QuizPlatform();
                    String email = quizPlatform.currentUser.getEmail();
                    saveQuizResults(email, quizID);
                }
                timer.cancel(); // Stop the timer after submission
            }
        }, duration * 60 * 1000); // duration is in mins, so converting to ms
    
        // Start the quiz loop
        while (!quizSubmitted) {
            displayQuestion(currentQuestionIndex);
    
            String input = scanner.nextLine().trim();
    
            if (quizSubmitted) {
                break; // Exit loop if quiz was submitted automatically
            }
    
            switch (input.toLowerCase()) {
                case "n":
                    currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
                    break;
                case "p":
                    currentQuestionIndex = (currentQuestionIndex - 1 + questions.size()) % questions.size();
                    break;
                case "s":
                    quizSubmitted = true;
                    System.out.println("Quiz submitted successfully!");
                    timer.cancel(); // Cancel the timer manually
                    break;
                default:
                    if (input.matches("[A-Za-z]")) {
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
        }
    
        // Save quiz results after submission
        quizSubmitted = true;
        QuizPlatform quizPlatform = new QuizPlatform();
        String email = quizPlatform.currentUser.getEmail();
        saveQuizResults(email, quizID);
    }

    public void saveQuizResults(String userEmail, String quizID) {
        String userFile = userEmail + "_quiz_results.txt";
    
        // Open the file in append mode
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
    
            // Write classes.Quiz ID and Attempt Time
            writer.write("classes.Quiz ID: " + quizID + " | classes.Quiz Attempted: " + new Date() + "\n");
    
            // Loop through each question and write the result
            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                String chosenOption = chosenOptions.get(i);
                boolean isCorrect = question.getCorrectOption().toString().equals(chosenOption);
                int score = isCorrect ? question.getMarksForCorrect() : question.getMarksForWrong();
    
                // Get the options and format them without curly braces
                Map<Character, String> options = question.getOptions();
                String formattedOptions = formatOptions(options);
    
                writer.write(question.getText() + "," + formattedOptions + "," + chosenOption + ", " + question.getCorrectOption() + "," + score + "\n");
            }
    
            // Optionally, write a line to indicate the end of this particular quiz attempt
            writer.write("------------------------------------------------\n\n");
    
            System.out.println("classes.Quiz results saved successfully.");
    
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    // Helper method to format the HashMap without curly braces
    private String formatOptions(Map<Character, String> options) {
        StringBuilder formattedOptions = new StringBuilder();
        
        for (Map.Entry<Character, String> entry : options.entrySet()) {
            formattedOptions.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
    
        // Remove the last comma and space if present
        if (formattedOptions.length() > 0) {
            formattedOptions.setLength(formattedOptions.length() - 2);
        }
    
        return formattedOptions.toString();
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
        List<Quiz> quizzes = displayQuizzes(topicID);

        if (!quizzes.isEmpty()) {
            int count=1;
            for (Quiz quiz : quizzes) {
                System.out.println("Quiz " + count + ":");
                count++;
                System.out.println(quiz); // This will call the toString method of classes.Quiz
            }

            System.out.print("Enter a Quiz ID from the list above: ");
            String quizID = scanner.nextLine();
            //System.out.println(quizEngine.QuizPlatform.currentUser.getAttempted());
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
                int duration = Integer.parseInt(attributes.get(7));
                AttemptQuiz attemptQuiz = new AttemptQuiz(quizID, attributes.get(1), attributes.get(2), attributes.get(3), attributes.get(4), Double.parseDouble(attributes.get(5)), Double.parseDouble(attributes.get(6)), Integer.parseInt(attributes.get(7)), Integer.parseInt(attributes.get(8)));
                QuizPlatform.currentUser.getAttempted().add(attemptQuiz);
                loadQuestions(quizID+".csv");
                startQuiz(quizID, duration);
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