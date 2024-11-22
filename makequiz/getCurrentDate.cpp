#include <iostream>
#include <ctime>
#include <iomanip>
#include <sstream>

#include "makequiz_MakeQuiz.h"
#include "makequiz_review.h"
using namespace std;

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