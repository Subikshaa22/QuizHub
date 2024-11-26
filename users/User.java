package users;
import attemptedquiz.AttemptQuiz;
import quizEngine.quizManager;

import java.util.*;
import java.util.regex.*;

public class User {
    private String username;
    private String email;      // Add an email field
    private String password;
    private List<AttemptQuiz> attempted;  // List of quizzes attempted by the user
    private List<String> made;     // List of quiz names created by the user
    private quizManager quizMgr;

    // Constructor
    public User(String username, String email, String password, quizManager quizMgr) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.attempted = new ArrayList<>();
        this.made = new ArrayList<>();
        this.quizMgr = quizMgr;
    }

    // Getters and setters

    public quizManager getQuizManager() {
        return quizMgr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AttemptQuiz> getAttempted() {
        return attempted;
    }

    public void setAttempted(List<AttemptQuiz> attempted) {
        this.attempted = attempted;
    }

    public List<String> getMade() {
        return made;
    }

    public void setMade(List<String> made) {
        this.made = made;
    }

    // ToString method for debugging purposes
    @Override
    public String toString() {
        return "users.User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", attempted=" + attempted +
                ", made=" + made +
                '}';
    }


    public static boolean validateEmail(String email) {
        String emailRegex = "^[\\w.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(emailRegex, email)) {
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        if (!Pattern.matches(passwordRegex, password)) {
            return false;
        }
        return true;
    }
}