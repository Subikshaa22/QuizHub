import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Collections;

public class AttemptQuiz {
    //call the topics page and display all of the topics
    public String displayTopics() {
        //display all the topics
        String x="";
        System.out.println("If the topic u want is not available then go back to main menu and make a quiz. Type BACK to go back to main menu");
        return x;
    }
    
    //once they select topic display all the quizzes in that topic
    public List<String> displayQuizzes(String topicID) {
        String filePath = "QuizFile.csv";

        String line;
        List<String> quizIDs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                // Check if the topic matches
                if (values[1].equalsIgnoreCase(topicID)) {
                    quizIDs.add(values[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quizIDs;
    }

    List<Question> questions = new ArrayList<>();
    List<String> chosenOptions = new ArrayList<>(Collections.nCopies(7, "-1"));

    int timeLimit;
    boolean quizSubmitted = false;
    int currentQuestionIndex = 0;

    public void loadQuestions(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String text = values[0].trim();
                String[] options = {values[1].trim(), values[2].trim(), values[3].trim(), values[4].trim()};
                String correctOption = values[5].trim();
                int marksForCorrect = Integer.parseInt(values[6].trim());
                int marksForWrong = Integer.parseInt(values[7].trim());
                double averageScore = Double.parseDouble(values[8].trim());
                timeLimit = Integer.parseInt(values[9].trim()); // Get time limit from file

                questions.add(new Question(text, options, correctOption, marksForCorrect, marksForWrong, averageScore));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void displayQuestions() {
        System.out.println("Question: " + text);
        System.out.println("a: " + options[0]);
        System.out.println("b: " + options[1]);
        System.out.println("c: " + options[2]);
        System.out.println("d: " + options[3]);
    }
    //once they select quiz display the questions one by one
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
            questions.get(currentQuestionIndex).displayQuestion();
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
                // Process the answer (a, b, c, or d)
                chosenOptions.set(currentQuestionIndex, input); 
            }else {
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

            while(input.matches("[abcd]"))

                input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("n")) {
                    currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
                } else if (input.equals("p")) {
                    currentQuestionIndex = (currentQuestionIndex - 1 + questions.size()) % questions.size();
                } else if (input.equals("s")) {
                    quizSubmitted = true;
                    System.out.println("Quiz submitted successfully!");
                    break;
                } else if (input.matches("[abcd]")) {
                    // Process the answer (a, b, c, or d)
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
            timer.cancel();
            scanner.close();
        }


    public void main()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("The available topics are: ");
        String topicID = displayTopics();
        if(topicID.equals("BACK"))
        {
            //go back to main menu
        }
        System.out.println("The available quizzes under your chosen topic are: ");

        List<String> quizIDs = displayQuizzes(topicID);

        if (!quizIDs.isEmpty()) {
            System.out.println("Quiz IDs for topic \"" + topicID + "\":");
            for (String quizID : quizIDs) {
                System.out.println(quizID);
            }

            String quizID;
            System.out.print("Enter a Quiz ID from the list above: ");
            quizID = scanner.nextLine();

            if (quizIDs.contains(quizID)) {
                System.out.println("You selected Quiz ID: " + quizID); 
                // Save `selectedQuizID` in a variable or use it as needed
                // Example: String quizIDVariable = selectedQuizID;
            } else {
                System.out.println("Invalid Quiz ID entered.");
            }
            loadQuestions("QuestionQ1.csv");
            startQuiz();

        } else {
            System.out.println("No quizzes found with topic \"" + topicID + "\".");
        }

        scanner.close();
    }
}