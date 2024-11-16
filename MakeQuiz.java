import java.util.*;

public class MakeQuiz {

    static Scanner scanner = new Scanner(System.in);
    
    // Create a quiz object
    ExistingQuizzes newQuiz = new ExistingQuizzes();

    public native boolean CheckIfTopicExists(String topic);
    public native int SaveTopic(String topic);
    public native int displayTopics();
    public native String getCurrentDate();
    static { System.loadLibrary("mylib"); }

    public static void ananya(MakeQuiz remove_error)
    {
        
        // Now you can access non-static newQuiz through the instance
        enterquestion ananya = new enterquestion();
        ananya.setQobject(remove_error.newQuiz);  // Access newQuiz through remove_error instance
        ananya.EnterQuestions();

        System.out.println(remove_error.newQuiz.getQuestions().size());
    }

    public static void kavya(MakeQuiz remove_error)
    {
        System.out.println(remove_error.newQuiz.getQuestions().size());
        edit_no_par kavya = new edit_no_par();
        kavya.setQobject(remove_error.newQuiz);  // Access newQuiz through remove_error instance
        kavya.editQuestion();

    }

    public static void nainika(MakeQuiz remove_error, int quizID,String name,String topic, String date)
    {
        System.out.println("im in nainka");

        review nainika = new review();
        nainika.setQobject(remove_error.newQuiz);  // Access newQuiz through remove_error instance
        nainika.reviewq(quizID,name,topic, date);
    }


    public static void main() {
        String topic="";

        OUTER: for (int i = 0; i < 5; i++) {
            System.out.println("The following topics are available:");
            MakeQuiz qz = new MakeQuiz();
            qz.displayTopics();
            System.out.println("Choose an option:");
            System.out.println("1. Select a topic ");
            System.out.println("2. Create a new topic ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: // select a topic
                {
                    System.out.println("Enter topic selected: ");
                    topic = scanner.nextLine();
                    boolean exists = true;
                   
                    // call cpp fxn to check if exists or not instead of using true, send topic as

                    exists = qz.CheckIfTopicExists(topic);
                    if (exists == true)
                        break OUTER;
                    else {
                        System.out.println("Invalid Topic");
                        continue OUTER;
                    }
                }
                case 2: // Create a new topic
                {
                    System.out.println("Enter new topic: ");
                    topic = scanner.nextLine();
                    
                    // call cpp save to file with topic as parameter

                    int done = qz.SaveTopic(topic);
                    break OUTER;
                }
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }

        // Generate a random quiz id and display it to user
        Random random = new Random();
        int quizID = random.nextInt(Integer.MAX_VALUE); // any positive int
        System.out.println(quizID + " is your Quiz id! ");
        String id = String.valueOf(quizID);

        // call cpp fxn -- Take date created of quiz 
        MakeQuiz qz = new MakeQuiz();
        String date_created = qz.getCurrentDate();

        System.out.println("Date is :" + date_created);

        // Start Forming Quiz
        System.out.println("Enter Quiz name:");
        String name = scanner.nextLine();
        boolean finish_making_quiz = false;

        // Create an instance of MakeQuiz
        MakeQuiz remove_error = new MakeQuiz();

        N: while (finish_making_quiz != true) {
            System.out.println("Choose an Option:");
            System.out.println("1. Enter new Question");
            System.out.println("2. Edit Existing Question");
            System.out.println("3. Finish Questions");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: {
                    // call ananya
                    ananya(remove_error );
                    break;
                }
                // enter new question
                case 2: {
                    // call kavya
                   kavya(remove_error );
                    break;// edit questions ka fxn
                }
                case 3: {
                    // call review fxn
                    nainika(remove_error, quizID,name,topic, date_created);
                    System.out.println("my class is done bye");
                    break N; // review fxn - time and to esit or discard or save

                }
                default:
                    System.out.println("Invalid choice , try again! ");
            }
        }

    }
}
// end of class

