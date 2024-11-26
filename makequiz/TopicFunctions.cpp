#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <memory>
#include <exception>
#include <algorithm>
#include <cctype>
#include <cstring>

// Include the headers of the java files calling these functions 
#include "makequiz_MakeQuiz.h"
#include "makequiz_ReviewQuiz.h"

using namespace std;

extern "C" {

    // Function to convert string to lowercase
    JNIEXPORT jstring JNICALL Java_makequiz_MakeQuiz_convertToLowerCase(JNIEnv *env, jobject obj, jstring input)
    {
        // Convert jstring to cpp string
        const char* utfChars = env->GetStringUTFChars(input, nullptr);
        std::string str(utfChars);

        // Convert the entire cpp string to lowercase using std::transform
        std::transform(str.begin(), str.end(), str.begin(),
                       [](unsigned char c) { return std::tolower(c); });

        // Convert cpp string back to jstring
        jstring result = env->NewStringUTF(str.c_str());

        // Release the original jstring memory
        env->ReleaseStringUTFChars(input, utfChars);

        // Return the converted string 
        return result;
    }

    // Function to display the topics from the file
    JNIEXPORT jint JNICALL Java_makequiz_MakeQuiz_displayTopics(JNIEnv *env, jobject obj)
    {
        // Open the file for reading 
        ifstream infile("Topics.csv");

        // Check if file exists and can be opened
        if(!infile.is_open())
            throw runtime_error("Error: Unable to open Topics.csv. Please ensure the file exists.");

        // Store each line from file 
        string line;

        // Read and display the topics
        while(getline(infile, line)) {
            // Skip empty lines, if any
            if(line.empty())
                continue; 

            // Extract details from the file
            istringstream iss(line);
            string topic;
            getline(iss, topic, ','); // Read until the first comma
            cout << topic << endl;
        }
        return 1; // Successfully read the topics 
    }

    // Function to check if a topic exists in the database 
    JNIEXPORT jboolean JNICALL Java_makequiz_MakeQuiz_checkIfTopicExists(JNIEnv *env, jobject obj, jstring topic)
    {
        bool exists = false;
        string line;

        // Check if the file is opening
        ifstream infile("Topics.csv");
        if(!infile.is_open())
            throw runtime_error("Error: Unable to open. Please ensure the file exists.");

        // Read and display the topics
        while(getline(infile, line)) {
            // Skip empty lines, if any
            if(line.empty())
                continue; 

            // Extract details from the file
            istringstream iss(line);
            string fileTopic;
            getline(iss, fileTopic, ','); // Read until the first comma

            // Convert both topics to lowercase to avoid case sensitivity
            jstring lowFileTopic = Java_makequiz_MakeQuiz_convertToLowerCase(env, obj, env->NewStringUTF(fileTopic.c_str()));
            jstring lowTopic = Java_makequiz_MakeQuiz_convertToLowerCase(env, obj, topic);

            // Convert the result jstrings back to C++ strings to compare
            const char* fileTopicFinal = env->GetStringUTFChars(lowFileTopic, nullptr);
            const char* topicFinal = env->GetStringUTFChars(lowTopic, nullptr);

            // Compare the lowercase strings to check if topic is there 
            if (strcmp(topicFinal, fileTopicFinal) == 0) {
                exists = true;
                break;
            }

            // Release the memory for the jstrings
            env->ReleaseStringUTFChars(lowFileTopic, fileTopicFinal);
            env->ReleaseStringUTFChars(lowTopic, topicFinal);
            env->DeleteLocalRef(lowFileTopic);
            env->DeleteLocalRef(lowTopic);
        }
        return exists;
    }

    // Function to save a topic to the file
    JNIEXPORT jint JNICALL Java_makequiz_MakeQuiz_saveTopic(JNIEnv *env, jobject obj, jstring topic)
    {
        // Convert jstring topic to cpp string
        const char* topicChars = env->GetStringUTFChars(topic, nullptr);
        string topicStr(topicChars);

        // Open the file for writing, in append mode 
        ofstream outfile("Topics.csv", ios::app);

        // Check if the file was successfully opened, exit if cannot be opened
        if(!outfile.is_open()) {
            cout << "Error: Unable to write to topics.txt" << endl;
            return 0; // Failure
        }
        
        // Write topic to file
        outfile << topicStr << endl;

        // Close the file after writing
        outfile.close();

        // Release the memory for the jstring
        env->ReleaseStringUTFChars(topic, topicChars);

        return 1; // Successfully written the topic to the file 
    }
}
/*
int main() {
    try {
        // Simulate calling Java_makequiz_MakeQuiz_displayTopics
        cout << "Displaying topics from file:" << endl;
        Java_makequiz_MakeQuiz_displayTopics(nullptr, nullptr);

        // Simulate checking if a topic exists
        string topicToCheck = "Math";
        cout << "\nChecking if topic '" << topicToCheck << "' exists:" << endl;
        
        // Convert the topic to jstring 
        JNIEnv* env = nullptr; 
        jobject obj = nullptr;
        jstring topicJString = env->NewStringUTF(topicToCheck.c_str());
        
        bool exists = Java_makequiz_MakeQuiz_checkIfTopicExists(env, obj, topicJString);
        cout << (exists ? "Topic exists." : "Topic does not exist.") << endl;

        // Simulate saving a topic to file
        string topicToSave = "Physics";
        jstring saveTopicJString = env->NewStringUTF(topicToSave.c_str());
        cout << "\nSaving topic '" << topicToSave << "' to file..." << endl;
        
        Java_makequiz_MakeQuiz_saveTopic(env, obj, saveTopicJString);
        cout << "Topic saved successfully!" << endl;

    } catch (const exception& e) {
        cerr << "Error: " << e.what() << endl;
    }

    return 0;
}
*/
