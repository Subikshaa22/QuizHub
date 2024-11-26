#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <list>
#include <map>

using namespace std;

void MakeQuizFile(string filename, string id, string name, string topic, string username, int timeAllo, int nq)
// date of creation also when liki gives 
// map questions annaya 
{
    newQuiz.setID(id);
    newQuiz.setName(name);
    newQuiz.setTopic(topic);
    newQuiz.setUsername(username);
    newQuiz.setTimeAllotted(timeAllo);
    newQuiz.setNumberOfQuestions(nq);

    //set ques 

    ofstream outfile(filename); // Create an ofstream object to write to the file

    // Check if the file was opened successfully
    if (!outfile.is_open()) 
    {
        cerr << "Error: Unable to open quiz file " << endl;
        return ; // Exit with error
    }

    // Close the file
    outfile.close();
    cout << "CSV Quiz file created successfully!" << endl;
}

void AddToPrevQuizFile()
{
    string filename = "PreviousQuizzez.txt";

    // Open the file for writing
    ofstream outfile(filename, ios::app);

    // Check if the file was successfully opened, exit if cannot be opened
    if(!outfile.is_open())
    {
        cout << "Error: Unable to write to topics.txt" << endl;
        return;
    }

    // Write to file - topic, id, name, avg score n time, time alloted, no of quesitons 
    outfile << newQuiz.getID() << newQuiz.getName() << newQuiz.getTopic() << newQuiz.getDateOfCreation() << newQuiz.getAvgScore() << newQuiz.getAvgTime() newQuiz.getTimeAllotted() << newQuiz.getNumberOfQuestions() ;
    
    // Close the file
    outfile.close();

    cout << "Quiz added to prev file" << endl;
}

void WriteToQuizFile(string filename)
{
    ofstream outfile(filename, ios::app); // Open file in append mode

    // Check if the file was opened successfully
    if (!outfile.is_open()) {
        cerr << "Error: Unable to open file " << endl;
        return ; // Exit with error
    }

    // Append details of quiz  
    outfile << newQuiz.getID() << newQuiz.getName() << newQuiz.getTopic() << newQuiz.getDateOfCreation() << newQuiz.getAvgScore() << newQuiz.getAvgTime() << newQuiz.getTimeAllotted() << newQuiz.getNumberOfQuestions() << newQuiz.getUsername();
    
    // Append Questions using their objects 
    list <Question> myq = newQuiz.getQuestions();
    int question_number = 1;

    for(auto q : myq)
    {
        outfile << question_number << q.getQuestionText() ;
        
        // Append the options 
        map<char,string> options = q.getOptions();
        
        for (const auto& option : options) 
        {
            string ekOp = option.first + option.second;
            outfile << ekOp; 
        }

        // Append the correct option 
        outfile << q.getCorrectAnswer();

        question_number++;
    }

    // Close the file
    outfile.close();

    cout << "Quiz Data appended successfully!" << endl;

    // Now add the quiz to prev quizzez file 
    AddToPrevQuizFile();
}

int main()
{
    string id, name, topic, username,filename;
    int timeAllo, nq;
    
    ExistingQuizzes newQuiz = new ExistingQuizzes();

    cout << "make quiz:"<<endl;
    cout <<" enter id"<<endl;
    cin >> id;
    cout <<" enter topic"<<endl;
    cin >> topic;
    cout <<" enter name"<<endl;
    cin >> name;
    cout <<" enter username"<<endl;
    cin >> username;
    cout <<" enter tieAllo"<<endl;
    cin >> timeAllo;
    cout <<" enter noofques"<<endl;
    cin >> nq;
    cout << "enter filename ";
    cin >>filename;

    MakeQuizFile(filename, id, name, topic, username, timeAllo, nq);
    WriteToQuizFile(filename);

    return 0;
}