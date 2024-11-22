package makequiz;
import java.util.*;

// nain-------------------------------------------------------------------------------------------------------------
public class review {

    public native int MakeIQuizFile(ExistingQuizzes refQuiz, String filename, int quizID, String name, String topic, String username, int time, int q, String date);
    public native int WriteToQuizFile(ExistingQuizzes refQuiz, String filename);
    public native int AddToPrevQuizFile(ExistingQuizzes refQuiz);

    static { System.loadLibrary("mylib"); }


    static Scanner scanner = new Scanner(System.in);
    
    private ExistingQuizzes refQuiz;

    public void setQobject(ExistingQuizzes refQuiz)
    {
        this.refQuiz = refQuiz;
    }

    
    public void reviewq(int quizID, String name,String topic ,String date)
    {
        int time =0;
        int no_of_ques = refQuiz.getQuestions().size();

        System.out.println("Enter the time duration for the quiz in minutes:");
        time = scanner.nextInt();
        scanner.nextLine();
        int choice = 0;

        OUTER: while (choice != 4) {
            System.out.println("Choose an option:");
            System.out.println("1. Edit Questions ");
            System.out.println("2. Save classes.Quiz ");
            System.out.println("3. Discard classes.Quiz ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: {
                    // call kav fxn
                    edit_no_par kavya = new edit_no_par();
                    kavya.setQobject(refQuiz);
                    kavya.editQuestion();
                    break;
                }
                case 2: {
                    // Make a quiz file
                    String quiz_file_name = String.valueOf(quizID);
                    String file_extension = ".csv";
                    String filename = quiz_file_name + file_extension;

                    int q = refQuiz.getQuestions().size();
                    refQuiz.setNumberOfQuestions(q);


                    // create and write to quiz file
                    review access_cpp =new review();

                    String user = refQuiz.getUsername();
                  
                    int check = access_cpp.MakeIQuizFile(refQuiz, filename, quizID, name, topic, "nainika" , time, q, date);
                    int check2 = access_cpp.WriteToQuizFile(refQuiz, filename);
                    access_cpp.AddToPrevQuizFile(refQuiz);
                    choice = 4;
                    break OUTER;
                }
                case 3: {
                    System.out.println("Are you sure you want to discard the quiz?");
                    System.out.println("1. Yes ");
                    System.out.println("2. No ");
                    int dis = scanner.nextInt();
                    scanner.nextLine();

                    switch (dis) {
                        case 1: {
                            choice = 4;// go back to main menu --subi
                            return;
                        }
                        case 2: {
                            continue OUTER;
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
