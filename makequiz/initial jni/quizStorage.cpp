#include <iostream>
#include <fstream>
#include <string>

using namespace std;

class QuizStorage {
private:
    string filePath;
    string name;

public:
    // Constructor to initialize the filePath and name
    QuizStorage(string filePath, string name) {
        this->filePath = filePath;
        this->name = name;
    }

    // Method to save the quiz to a file
    void saveQuiz(const string& content) {
        // Open the file for writing
        ofstream outFile(filePath, ios::out | ios::trunc);
        if (outFile.is_open()) {
            outFile << content;
            outFile.close();
            cout << "Quiz saved successfully!" << endl;
        } else {
            cout << "Error: Unable to save quiz to file." << endl;
        }
    }

    // Method to load quiz details from a file
    string loadQuiz() {
        string content;
        ifstream inFile(filePath);

        if (inFile.is_open()) {
            string line;
            while (getline(inFile, line)) {
                content += line + "\n";
            }
            inFile.close();
        } else {
            content = "Error: Unable to load quiz.";
        }

        return content;
    }
};

// Example usage
int main() {
    // Creating an instance of QuizStorage
    QuizStorage quizStorage("quiz.txt", "Sample Quiz");

    // Saving a quiz to the file
    string quizContent = "Question 1: What is 2 + 2?\nAnswer: 4";
    quizStorage.saveQuiz(quizContent);

    // Loading the quiz from the file
    string loadedQuiz = quizStorage.loadQuiz();
    cout << "Loaded Quiz: \n" << loadedQuiz << endl;

    return 0;
}