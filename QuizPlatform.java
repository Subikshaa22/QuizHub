import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class QuizPlatform {
    private static final String USERS_FILE = "users.csv";
    private static List<User> users = new ArrayList<>();  // List to store registered users
    private static User currentUser;  // Variable to keep track of the logged-in user

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Hi, welcome to QuizMaster!");
            System.out.println("Select an option: ");
            System.out.println("1. Sign In");
            System.out.println("2. Sign Up");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> signIn(scanner);
                case 2 -> signUp(scanner);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 3) {
                    String username = userData[0];
                    String email = userData[1];
                    String password = userData[2];
                    users.add(new User(username, email, password, new quizManager(new ArrayList<>())));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
 
        }
    }

    private static void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.println(user.getUsername() + "," + user.getEmail() + "," + user.getPassword());
            }
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        
        }
    }

    private static void signIn(Scanner scanner) {
        System.out.print("Enter username or email: ");
        String identifier = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Optional<User> user = users.stream()
                .filter(u -> (u.getUsername().equals(identifier) || u.getEmail().equals(identifier)) && u.getPassword().equals(password))
                .findFirst();

        if (user.isPresent()) {
            currentUser = user.get();
            System.out.println("Hi " + currentUser.getUsername() + "!");
            showMainMenu(scanner);
        } else {
            System.out.println("Incorrect username/email or password. Try again.");
        }
    }

    private static void signUp(Scanner scanner) {
        List<Quiz> quizList = new ArrayList<>(); // Initialize an empty quiz list
        quizManager quizMgr = new quizManager(quizList); // Create quizManager instance

        System.out.print("Choose a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Create a password: ");
        String password = scanner.nextLine();
        

        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
            System.out.println("Username already exists. Try again.");
            return;
        }

        if (users.stream().anyMatch(u -> u.getEmail().equals(email))) {
            System.out.println("Email already exists. Try again.");
            return;
        }

        User newUser = new User(username, email, password, quizMgr);
        users.add(newUser);
        System.out.println("User registered successfully! You can now sign in.");
    }

    private static void showMainMenu(Scanner scanner) {
        while (currentUser != null) {
            System.out.println("\nOptions:");
            System.out.println("1. Profile History");
            System.out.println("2. Continue Learning");
            System.out.println("3. Log Out");
            System.out.println("4. Delete Account");
    
            System.out.print("Choice: ");
            String input = scanner.nextLine();  // Read the whole line to handle invalid input better
            try {
                int choice = Integer.parseInt(input);  // Try to parse the input to an integer
    
                switch (choice) {
                    case 1 -> viewProfileHistory();
                    case 2 -> continueLearning();
                    case 3 -> logOut();
                    case 4 -> deleteAccount();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    

    private static void viewProfileHistory() {
        ProfileHistory profileHistory = new ProfileHistory(currentUser);
        profileHistory.displayProfileHistory();  // Call the method to display the profile history
    }

    private static void continueLearning() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Accessing Continue Learning...");
        System.out.println("1. Attempt Quiz");
        System.out.print("Choice: ");
        // Here you can integrate the `quizManager` or any other class functionality for quiz learning

        int choice2 = scanner.nextInt();
        scanner.nextLine();

        AttemptQuiz attemptQuiz = new AttemptQuiz();
        //MakeQuiz makeQuiz = new MakeQuiz();

        switch(choice2) {
            //case 1 -> makeQuiz.main();
            case 2 -> attemptQuiz.main();
            // case 3 -> GenerateQuiz();
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void logOut() {
        System.out.println("Logging out " + currentUser.getUsername());
        currentUser = null;  // Clear current user
    }

    private static void deleteAccount() {
        System.out.println("Deleting account for " + currentUser.getUsername());
        users.remove(currentUser);  // Remove user from the list
        currentUser = null;  // Clear current user
        System.out.println("Account deleted successfully.");
    }
}
