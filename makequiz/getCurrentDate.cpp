#include <iostream>
#include <ctime>
#include <iomanip>
#include <sstream>

// Header files of java files using this native function
#include "makequiz_MakeQuiz.h"
#include "makequiz_ReviewQuiz.h"

using namespace std;

// Function to get the current time 
extern "C" JNIEXPORT jstring JNICALL Java_makequiz_MakeQuiz_getCurrentDate(JNIEnv *env, jobject obj)
{
    // Get the current time as a time_t object
    time_t t = time(nullptr);

    // Convert to local time structure
    tm* now = localtime(&t);

    // Use stringstream to format the date as YYYY-MM-DD
    ostringstream dateStream;
    dateStream << (now->tm_year + 1900) << '-'
               << setw(2) << setfill('0') << (now->tm_mon + 1) << '-'
               << setw(2) << setfill('0') << now->tm_mday;

    // Convert the formatted date (std::string) to a jstring
    string dateStr = dateStream.str();
    jstring result = env->NewStringUTF(dateStr.c_str()); // Convert std::string to jstring

    // Return the formatted date as jstring
    return result;
}
#include <iostream>
#include <jni.h>
#include <ctime>
#include <iomanip>
#include <sstream>

// Forward declaration of the JNI function
extern "C" {
    JNIEXPORT jstring JNICALL Java_makequiz_MakeQuiz_getCurrentDate(JNIEnv *env, jobject obj);
}

int main() {
    try {
        // Simulate creating a JNI environment and calling the getCurrentDate function
        JNIEnv* env = nullptr;  
        jobject obj = nullptr;  

       
        jstring currentDate = Java_makequiz_MakeQuiz_getCurrentDate(env, obj);

      
        const char* dateChars = env->GetStringUTFChars(currentDate, nullptr);
        std::cout << "Current Date: " << dateChars << std::endl;

        // Release the memory for the jstring
        env->ReleaseStringUTFChars(currentDate, dateChars);
    }
    catch (const std::exception& e) {
        std::cerr << "Error: " << e.what() << std::endl;
    }

    return 0;
}
