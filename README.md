# QuizHub
QuizHub is a command-line interface (CLI) platform developed as a course project, showcasing Object-Oriented Programming (OOP) principles in Java and C++. It provides a dynamic and user-friendly solution for creating, attempting, and managing quizzes. Designed for individual users, educators, and administrators, it enables seamless quiz creation, participation, and progress tracking while demonstrating the power of OOP concepts in real-world applications.

## Contributors
- Ananya Vundavalli - IMT2023537
- Harshita Bansal - IMT2023035
- Kavya Gupta - IMT2023016
- Nainika Agrawal - IMT2023034
- Subikshaa Sakthivel - IMT2023020
- Talla Likitha Reddy - IMT2023550
- Vriddhi Agrawal - IMT2023611

## Goal :dart: 
QuizHub aims to offer a streamlined and engaging platform for creating and managing quizzes through a command-line interface. It leverages Object-Oriented Programming to implement practical solutions. The project focuses on enhancing user experience for both learning and teaching scenarios.

## Features
- :lock_with_ink_pen: User Registration:
  - Users can sign up with a username, email, and password.
  - User data (username, email, password) is stored in a CSV file.
- :key: User Login:
  - Users can log in using their email and password.
  - Validates credentials against the stored CSV file to authenticate users.
- :adult:‍:briefcase: Profile History:
  - The history shows two main categories:
    - Attempted Quizzes: Quizzes the user has taken, including details such as score, time taken, and date of attempt.
    - Created Quizzes: Quizzes created by the user, including quiz title, average score, and average time.
  - Quiz Review: Users can review individual quizzes they’ve attempted or created, with options for filtering questions (e.g., showing incorrect or unattempted questions).
-  :heavy_plus_sign: Quiz Creation:
    - Users can create quizzes, adding questions to the quiz.
    - A quiz includes details such as name, topic, and questions.
    - Each question has multiple-choice answers, and the correct answer is specified.
- :stopwatch: Quiz Attempt:
  - Users can attempt quizzes by selecting answers to questions.
  - Quizzes are timed, and users are scored based on their answers.
  - After attempting the quiz, the platform shows the results, including the score and time taken.
- :memo: Attempted Quizzes Tracking:
  - The system tracks quizzes attempted by the user, storing their performance (score, time taken, etc.).
  - Filtering of Attempted Quizzes: Users can filter their attempts by:
    - All: All attempted quizzes.
    - Incorrect: Only the questions the user got wrong.
    - Unattempted: Questions that were not answered by the user.
- :bar_chart: Quiz Statistics: For created quizzes, the platform calculates and displays statistics such as:
Average score of all users who attempted the quiz and average time taken to complete the quiz.
- :calendar: Review Quizzes:
  - Attempted Quizzes Review: After attempting a quiz, users can review their answers, see which ones were incorrect, and view the correct answers.
  - Created Quizzes Review: Users can review the questions in quizzes they have created, with the option to navigate between questions.
  - Navigation: Users can move between questions in the quiz review (next, previous).
 
- :card_index_dividers: CSV Storage:
  - User data (username, email, password) and quiz data is stored in a CSV file.
 
- :technologist: User Data Management:
  - A quizManager class manages quizzes, allowing them to be added and retrieved.
  - User-created quizzes are linked to their accounts for future access and management.
  

## Tech Stack
- Languages
  - C++
  - Java

- Interoperability
  - Java Native Interface (JNI) : Facilitates seamless interoperability between C++ and Java, enabling efficient cross-language communication and integration of native and high-level components.

- Data Storage
  - CSV Files : Used as a lightweight, portable solution for storing and managing quiz and user data in a comma-separated format.

- Tools
  - IntelliJ IDEA
  - Visual Studio Code
  - Github
 
## Setting Up and Running the Project
### Steps to Set Up `JAVA_HOME` for JNI Project

1. **Install Java Development Kit (JDK):**
   - Download and install JDK from [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or [OpenJDK](https://openjdk.org/).
   - Note the installation directory (e.g., `C:\Program Files\Java\jdk-XX.X.X` on Windows or `/usr/lib/jvm/java-XX-openjdk` on Linux).

2. **Install C++ Compiler:**
   - **Windows**: Install [MinGW](https://www.mingw-w64.org/) or use Visual Studio build tools.
   - **macOS/Linux**: Use `gcc` or `clang` (usually pre-installed).

3. **Set Up `JAVA_HOME` Environment Variable:**

   - **On Windows:**
     1. Open **System Properties** > **Advanced System Settings** > **Environment Variables**.
     2. Click **New** under **System Variables**:
        - **Variable Name**: `JAVA_HOME`
        - **Variable Value**: Full path to JDK installation (e.g., `C:\Program Files\Java\jdk-XX.X.X`).
     3. Add `%JAVA_HOME%\bin` to the `Path` system variable.

   - **On macOS/Linux:**
     1. Open your shell configuration file (`~/.bashrc`, `~/.zshrc`, etc.).
     2. Add:
        ```sh
        export JAVA_HOME=/path/to/jdk
        export PATH=$JAVA_HOME/bin:$PATH
        ```
     3. Save and reload the shell config: `source ~/.bashrc`.

4. **Verify `JAVA_HOME`:**
   - **Windows**: Run `echo %JAVA_HOME%` in Command Prompt.
   - **macOS/Linux**: Run `echo $JAVA_HOME` in the terminal.


This sets up `JAVA_HOME` for your JNI project. You can now proceed with the compilation and execution steps.

## Instructions for Compiling and Running the JNI Project

Follow these steps to compile and run the JNI project. This involves compiling Java classes, generating JNI headers, compiling C++ code into a shared library, and running the application.

### 1. Compile All Java Classes
To compile all the Java files in your project and place the compiled `.class` files in the `bin` directory, use the following command:

```sh
javac -d bin $(find . -name "*.java")
```

Once the Java files are compiled, generate the JNI header files for the Java classes that interact with the C++ code. Use the following command for `MakeQuiz.java`:
```sh
javac -h bin/makequiz makequiz/MakeQuiz.java
```
Similarly, generate the JNI header for `ReviewQuiz.java`:
```sh
javac -h bin/makequiz makequiz/ReviewQuiz.java
```

Now, compile the C++ source files into a shared library using the following command:


Linux:
```sh
g++ -shared -fpic -o libmylib.so makequiz/TopicFunctions.cpp makequiz/MakeQuizFile.cpp makequiz/getCurrentDate.cpp -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -I"bin/makequiz"
```

Windows:
```sh
g++ -shared -o libmylib.dll makequiz/TopicFunctions.cpp makequiz/MakeQuizFile.cpp makequiz/getCurrentDate.cpp -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/win32" -I"bin/makequiz"
```

After successfully compiling the shared library, you can run your Java application. Use the following command to run the application and link the JNI library:
```sh
java -Djava.library.path=$(pwd) -cp bin quizEngine.QuizPlatform
```


## Further Improvements
- Implementing features like buzzer rounds and leaderboards.
- Adding more complex quiz statistics such as question-wise performance analytics for the user, and SWOT analysis.
- Given a topic, auto create a quiz using API calls.
- Enhance its functionality by introducing a graphical user interface (GUI) in the future, making the platform more intuitive and visually engaging for users.
