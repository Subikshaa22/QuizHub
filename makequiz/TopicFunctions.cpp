#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <memory>
#include <exception>
#include <algorithm>
#include <cctype>
#include<cstring>
#include "makequiz_MakeQuiz.h"
#include "makequiz_review.h"

using namespace std;

string filename = "topics.txt";

extern "C" {

    // Function to convert std::string to jstring and convert to lowercase

    JNIEXPORT jstring JNICALL Java_makequiz_MakeQuiz_convertToLowerCase(JNIEnv *env, jobject obj, jstring input)
    {
        // Step 1: Convert jstring to std::string
        const char* utfChars = env->GetStringUTFChars(input, nullptr);
        std::string str(utfChars);

        // Step 2: Convert the string to lowercase using std::transform
        std::transform(str.begin(), str.end(), str.begin(),
                       [](unsigned char c) { return std::tolower(c); });

        // Step 3: Convert std::string back to jstring
        jstring result = env->NewStringUTF(str.c_str());

        // Release the original jstring memory
        env->ReleaseStringUTFChars(input, utfChars);

        return result;
    }

    // Function to display the topics from the file
    JNIEXPORT jint JNICALL Java_makequiz_MakeQuiz_displayTopics(JNIEnv *env, jobject obj)
    {
        
        // Function to load data from topics
        ifstream infile("Topics.csv");

        // Check if file exists and can be opened
        if(!infile.is_open())
            throw runtime_error("Error: Unable to open. Please ensure the file exists.");

        string line;

        // Read and display the topics
        while(getline(infile, line)) {
            if(line.empty())
                continue; // Skip empty lines, if any

            // Extract details from the file
            istringstream iss(line);
            string topic;
            getline(iss, topic, ','); // Read until the first comma
            cout << topic << endl;
        }
        return 1;
    }

    // Function to check if a topic exists in the file
    JNIEXPORT jboolean JNICALL Java_makequiz_MakeQuiz_CheckIfTopicExists(JNIEnv *env, jobject obj, jstring topic)
    {
        bool exists = false;
        string line;

        // Check if the file is opening
        ifstream infile("Topics.csv");

        if(!infile.is_open())
            throw runtime_error("Error: Unable to open. Please ensure the file exists.");

        // Read and display the topics
        while(getline(infile, line)) {
            if(line.empty())
                continue; // Skip empty lines, if any

            // Extract details from the file
            istringstream iss(line);
            string file_topic;
            getline(iss, file_topic, ','); // Read until the first comma

            // Convert both topics to lowercase to avoid case sensitivity
            jstring low_file_topic = Java_makequiz_MakeQuiz_convertToLowerCase(env, obj, env->NewStringUTF(file_topic.c_str()));
            jstring low_topic = Java_makequiz_MakeQuiz_convertToLowerCase(env, obj, topic);

            // Convert the result jstrings back to C++ strings to compare
            const char* file_topic_final = env->GetStringUTFChars(low_file_topic, nullptr);
            const char* topic_final = env->GetStringUTFChars(low_topic, nullptr);

            // Compare the lowercase strings
            if (strcmp(topic_final, file_topic_final) == 0) {
                exists = true;
                break;
            }

            // Release the memory for the jstrings
            env->ReleaseStringUTFChars(low_file_topic, file_topic_final);
            env->ReleaseStringUTFChars(low_topic, topic_final);
            env->DeleteLocalRef(low_file_topic);
            env->DeleteLocalRef(low_topic);
        }
        return exists;
    }

    // Function to save a topic to the file
    JNIEXPORT jint JNICALL Java_makequiz_MakeQuiz_SaveTopic(JNIEnv *env, jobject obj, jstring topic)
    {
        // Convert jstring topic to std::string
        const char* topic_chars = env->GetStringUTFChars(topic, nullptr);
        string topic_str(topic_chars);

        // Open the file for writing
        ofstream outfile("Topics.csv", ios::app);

        // Check if the file was successfully opened, exit if cannot be opened
        if(!outfile.is_open()) 
        {
            cout << "Error: Unable to write to topics.txt" << endl;
            return 0; // Failure
        }

        // Write topic to file
        outfile << topic_str << endl;

        // Close the file after writing
        outfile.close();

        // Release the memory for the jstring
        env->ReleaseStringUTFChars(topic, topic_chars);

        return 1; // Success
    }
}

