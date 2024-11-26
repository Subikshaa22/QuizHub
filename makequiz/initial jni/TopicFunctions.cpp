#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <memory>
#include <exception>

using namespace std;

  string filename = "topics.txt";

void displayTopics()
{
    // Function to load data from topics 
    ifstream infile(filename);

    // Check if file exists and can be opened 
    if(!infile.is_open())
        throw  runtime_error("Error: Unable to open. Please ensure the file exists.");

    string line;

    // Read and display the topics 
    while(getline(infile, line))
    {
        if(line.empty()) 
            continue; // Skip empty lines, if any

        // Extract details from the file 
        istringstream iss(line);
        string topic;
        getline(iss, topic, ','); // Read until the first comma
        cout << topic << endl; 
    }
}

bool CheckIfTopicExists(string topic)
{
    bool exists = false;
    string line;

    // check if file is opening 
    ifstream infile(filename);
    if(!infile.is_open())
        throw  runtime_error("Error: Unable to open. Please ensure the file exists.");

    // Read and display the topics 
    while(getline(infile, line))
    {
        if(line.empty()) 
            continue; // Skip empty lines, if any

        // Extract details from the file 
        istringstream iss(line);
        string file_topic;
         getline(iss, file_topic, ','); // Read until the first comma
        
        // convert to lower case to avoid case sensitivity 
        for (char& c : file_topic) 
            c = tolower(c);

        for (char& c : topic) 
            c = tolower(c);
            
        if (topic == file_topic)
        {
            exists = true;
            break;
        }
    }
    return exists;
}

void SaveTopic(string topic)
{
    // Open the file for writing
    ofstream outfile(filename, ios::app);

    // Check if the file was successfully opened, exit if cannot be opened
    if(!outfile.is_open())
    {
        cout << "Error: Unable to write to topics.txt" << endl;
        return;
    }

    // Write topic to file 
    outfile << topic << endl;
    
    // Close the file after writing
    outfile.close();
}

int main()
{
    // Example predefined topics and checks (you can modify or expand these as needed)
    string topicToCheck = "C++ Basics";
    string topicToAdd = "Advanced C++";

    cout << "--- Topic Management System ---\n";
    
    // 1. Displaying topics from file
    cout << "\nDisplaying Topics:\n";
    try
    {
        displayTopics();
    }
    catch (const exception& e)
    {
        cout << e.what() << endl;
    }

    // 2. Checking if a specific topic exists
    cout << "\nChecking if topic '" << topicToCheck << "' exists:\n";
    try
    {
        bool exists = CheckIfTopicExists(topicToCheck);
        if (exists)
        {
            cout << "Topic '" << topicToCheck << "' exists.\n";
        }
        else
        {
            cout << "Topic '" << topicToCheck << "' does not exist.\n";
        }
    }
    catch (const exception& e)
    {
        cout << e.what() << endl;
    }

    // 3. Adding a new topic if it doesn't already exist
    cout << "\nTrying to add new topic '" << topicToAdd << "':\n";
    try
    {
        bool exists = CheckIfTopicExists(topicToAdd);
        if (exists)
        {
            cout << "Topic '" << topicToAdd << "' already exists.\n";
        }
        else
        {
            SaveTopic(topicToAdd);
            cout << "Topic '" << topicToAdd << "' has been added.\n";
        }
    }
    catch (const exception& e)
    {
        cout << e.what() << endl;
    }

    cout << "\nExiting program.\n";
    return 0;
}
