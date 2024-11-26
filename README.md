# QuizHub
QuizHub is a command-line interface (CLI) platform developed as a course project, showcasing Object-Oriented Programming (OOP) principles in Java and C++. It provides a dynamic and user-friendly solution for creating, attempting, and managing quizzes. Designed for individual users, educators, and administrators, it enables seamless quiz creation, participation, and progress tracking while demonstrating the power of OOP concepts in real-world applications.

## Contributors
- Ananya Vundavalli
- Harshita Bansal
- Kavya Gupta
- Nainika Agrawal
- Subikshaa Sakthivel
- Talla Likitha Reddy
- Vriddhi Agrawal

## Goal

## Features
- User Registration:
  - Users can sign up with a username, email, and password.
  - User data (username, email, password) is stored in a CSV file.
- User Login:
  - Users can log in using their email and password.
  - Validates credentials against the stored CSV file to authenticate users.
- Profile History:
  - The history shows two main categories:
    - Attempted Quizzes: Quizzes the user has taken, including details such as score, time taken, and date of attempt.
    - Created Quizzes: Quizzes created by the user, including quiz title, average score, and average time.
  - Quiz Review: Users can review individual quizzes they’ve attempted or created, with options for filtering questions (e.g., showing incorrect or unattempted questions).
-  Quiz Creation:
  - Users can create quizzes, adding questions to the quiz.
  - A quiz includes details such as name, topic, and questions.
  - Each question has multiple-choice answers, and the correct answer is specified.
- Quiz Attempt:
  - Users can attempt quizzes by selecting answers to questions.
  - Quizzes are timed, and users are scored based on their answers.
  - After attempting the quiz, the platform shows the results, including the score and time taken.
- Attempted Quizzes Tracking:
  - The system tracks quizzes attempted by the user, storing their performance (score, time taken, etc.).
  - Filtering of Attempted Quizzes: Users can filter their attempts by:
    - All: All attempted quizzes.
    - Incorrect: Only the questions the user got wrong.
    - Unattempted: Questions that were not answered by the user.
- Quiz Statistics: For created quizzes, the platform calculates and displays statistics such as:
Average score of all users who attempted the quiz and average time taken to complete the quiz.
- Review Quizzes:
  - Attempted Quizzes Review: After attempting a quiz, users can review their answers, see which ones were incorrect, and view the correct answers.
  - Created Quizzes Review: Users can review the questions in quizzes they have created, with the option to navigate between questions.
  - Navigation: Users can move between questions in the quiz review (next, previous).
 
- CSV Storage:
  - User data (username, email, password) and quiz data is stored in a CSV file.
 
- User Data Management:
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
 

## File Structure and Design Choices

## Setting Up and Running the Project

## Applications

## Further Improvements
