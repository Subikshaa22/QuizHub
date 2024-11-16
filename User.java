import java.util.*;

class User {
    private String username;
    private String email;      // Add an email field
    private String password;
    private List<Quiz> attempted;  // List of quizzes attempted by the user
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

    public List<Quiz> getAttempted() {
        return attempted;
    }

    public void setAttempted(List<Quiz> attempted) {
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
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", attempted=" + attempted +
                ", made=" + made +
                '}';
    }

}
