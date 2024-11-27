#include <jni.h>
#include <fstream>
#include <iostream>
#include <string>
#include <list>
#include <string>

// Include the header files of the java files that are using these functions 
#include "makequiz_MakeQuiz.h"
#include "makequiz_ReviewQuiz.h"

using namespace std;

extern "C" {

    // Function that makes a quiz file whose name is the quizID
    JNIEXPORT jint Java_makequiz_ReviewQuiz_MakeIQuizFile(JNIEnv *env, jobject obj, jobject newQuiz, jstring filename, jint id, jstring name, jstring topic, jstring username, jint timeAllo, jint nq, jstring date)
    {
        // Convert jstring filename to C++ string --correct
        const char *filenameChars = env->GetStringUTFChars(filename, NULL);
        string cppFilename(filenameChars);
        env->ReleaseStringUTFChars(filename, filenameChars);

        // Convert the int 'id' to a string
        string idString = to_string(id);
        // Convert the C++ string to a jstring
        jstring idJString = env->NewStringUTF(idString.c_str());

        // Get method IDs for setters in the Java class
        jclass quizClass = env->GetObjectClass(newQuiz);
        jmethodID setIDMethod = env->GetMethodID(quizClass, "setID", "(Ljava/lang/String;)V");
        jmethodID setNameMethod = env->GetMethodID(quizClass, "setName", "(Ljava/lang/String;)V");
        jmethodID setTopicMethod = env->GetMethodID(quizClass, "setTopic", "(Ljava/lang/String;)V");
        jmethodID setUsernameMethod = env->GetMethodID(quizClass, "setUsername", "(Ljava/lang/String;)V");
        jmethodID setDateMethod = env->GetMethodID(quizClass, "setDateOfCreation", "(Ljava/lang/String;)V");
        jmethodID setTimeAllottedMethod = env->GetMethodID(quizClass, "setTimeAllotted", "(I)V");
        jmethodID setNumberOfQuestionsMethod = env->GetMethodID(quizClass, "setNumberOfQuestions", "(I)V");

        // Call setter methods on the Java object
        env->CallVoidMethod(newQuiz, setIDMethod, idJString);
        env->CallVoidMethod(newQuiz, setDateMethod, date);
        env->CallVoidMethod(newQuiz, setNameMethod, name);
        env->CallVoidMethod(newQuiz, setTopicMethod, topic);
        env->CallVoidMethod(newQuiz, setUsernameMethod, username);
        env->CallVoidMethod(newQuiz, setTimeAllottedMethod, timeAllo);
        env->CallVoidMethod(newQuiz, setNumberOfQuestionsMethod, nq);

        // Check if any method is missing 
        if (!setIDMethod || !setNameMethod || !setTopicMethod || !setUsernameMethod || !setTimeAllottedMethod || !setNumberOfQuestionsMethod || !setDateMethod) {
            cerr << "Error: Could not find setter methods" << endl;
            return -1;
        }

        // Open file for writing
        ofstream outfile(cppFilename, ios::out);
        
        // Check if file has opened correctly 
        if (!outfile.is_open()) {
            cerr << "Error opening file for writing." << std::endl;
            return -1;  
        }

        // Get getters from the Java object
        jmethodID getIDMethod = env->GetMethodID(quizClass, "getID", "()Ljava/lang/String;");
        jmethodID getNameMethod = env->GetMethodID(quizClass, "getName", "()Ljava/lang/String;");
        jmethodID getTopicMethod = env->GetMethodID(quizClass, "getTopic", "()Ljava/lang/String;");
        jmethodID getUsernameMethod = env->GetMethodID(quizClass, "getUsername", "()Ljava/lang/String;");
        jmethodID getDateMethod = env->GetMethodID(quizClass, "getDateOfCreation", "()Ljava/lang/String;");
        jmethodID getAvgScoreMethod = env->GetMethodID(quizClass, "getAvgScore", "()D");
        jmethodID getAvgTimeMethod = env->GetMethodID(quizClass, "getAvgTime", "()D");
        jmethodID getTimeAllottedMethod = env->GetMethodID(quizClass, "getTimeAllotted", "()I");
        jmethodID getNumberOfQuestionsMethod = env->GetMethodID(quizClass, "getNumberOfQuestions", "()I");

        // Call getter methods to retrieve values
        jstring nameVal = (jstring) env->CallObjectMethod(newQuiz, getNameMethod);
        jstring topicVal = (jstring) env->CallObjectMethod(newQuiz, getTopicMethod);
        jstring usernameVal = (jstring) env->CallObjectMethod(newQuiz, getUsernameMethod);
        jstring dateOfCreationVal = (jstring) env->CallObjectMethod(newQuiz, getDateMethod);
        jdouble avgScoreVal = env->CallDoubleMethod(newQuiz, getAvgScoreMethod);
        jdouble avgTimeVal = env->CallDoubleMethod(newQuiz, getAvgTimeMethod);
        jint timeAllottedVal = env->CallIntMethod(newQuiz, getTimeAllottedMethod);
        jint numOfQuestionsVal = env->CallIntMethod(newQuiz, getNumberOfQuestionsMethod);

        // Convert the jstring to a C++ string
        const char *idCString = env->GetStringUTFChars(idJString, NULL);
        std::string idCppString(idCString);
        env->ReleaseStringUTFChars(idJString, idCString);
        // Convert the C++ string to an int
        int idVal = std::stoi(idCppString); // Safe since we validated it



        // Convert Java strings to C++ strings
        const char *nameStr = env->GetStringUTFChars(nameVal, NULL);
        const char *topicStr = env->GetStringUTFChars(topicVal, NULL);
        const char *usernameStr = env->GetStringUTFChars(usernameVal, NULL);
        const char *dateOfCreationStr = env->GetStringUTFChars(dateOfCreationVal, NULL);

        // Write data to file
        outfile << idVal << "," << nameStr << "," << topicStr<< "," << usernameStr<< ","<< dateOfCreationStr<< ","<< avgScoreVal<< ","<< avgTimeVal<< ","<< timeAllottedVal<< ","<< numOfQuestionsVal<< ","<<endl;

        // Release memory for Java strings
        env->ReleaseStringUTFChars(nameVal, nameStr);
        env->ReleaseStringUTFChars(topicVal, topicStr);
        env->ReleaseStringUTFChars(usernameVal, usernameStr);
        env->ReleaseStringUTFChars(dateOfCreationVal, dateOfCreationStr);

        // Close the file
        outfile.close(); 

        return 0; // Successfully created and written to quizID.csv 
    }


    // Function to add the quiz details to the previousquizzez file 
    JNIEXPORT jint JNICALL Java_makequiz_ReviewQuiz_AddToPrevQuizFile(JNIEnv *env, jobject obj, jobject newQuiz)
    {
        // The file storing list of all quizzez made till now 
        string filename = "PreviousQuizzez.csv"; 

        // Open the file for writing in append mode
        ofstream outfile(filename, ios::app);

        // Check if the file was successfully opened, exit if cannot be opened
        if (!outfile.is_open()) {
            cout << "Error: Unable to write to " << filename << endl;
            return -1;  
        }

        // Get newQuiz object from obj in JNI context
        jclass quizClass = env->GetObjectClass(newQuiz);

        // Get getters from the Java object
        jmethodID getID = env->GetMethodID(quizClass, "getID", "()Ljava/lang/String;");
        jmethodID getName= env->GetMethodID(quizClass, "getName", "()Ljava/lang/String;");
        jmethodID getEmail = env->GetMethodID(quizClass, "getUsername", "()Ljava/lang/String;");
        jmethodID getTopic = env->GetMethodID(quizClass, "getTopic", "()Ljava/lang/String;");
        jmethodID getDate= env->GetMethodID(quizClass, "getDateOfCreation", "()Ljava/lang/String;");
        jmethodID getAvgScore = env->GetMethodID(quizClass, "getAvgScore", "()D");
        jmethodID getAvgTime = env->GetMethodID(quizClass, "getAvgTime", "()D");
        jmethodID getTimeAllotted= env->GetMethodID(quizClass, "getTimeAllotted", "()I");
        jmethodID getNumberOfQuestions = env->GetMethodID(quizClass, "getNumberOfQuestions", "()I");


        // Ensure the getter methods exist 
        if (!getID || !getEmail || !getName || !getTopic || !getDate || !getAvgScore || !getAvgTime || !getTimeAllotted || !getNumberOfQuestions) {
            cerr << "Error: Could not find necessary getter methods for newQuiz" << endl;
            return -1;  // Return error code if any method is missing
        }


        // for id we need conversion also
        jstring idInJString = (jstring) env->CallObjectMethod(newQuiz, getID);
        // Convert the jstring to a C++ string
        const char *idString = env->GetStringUTFChars(idInJString, NULL);
        string idCppString(idString);
        env->ReleaseStringUTFChars(idInJString, idString);
        // Convert the C++ string to an int
        int id = stoi(idCppString); // Safe since we validated it


        jstring name = (jstring)env->CallObjectMethod(newQuiz, getName);
        jstring email= (jstring)env->CallObjectMethod(newQuiz, getEmail);
        jstring topic = (jstring)env->CallObjectMethod(newQuiz, getTopic);
        jstring dateOfCreation = (jstring)env->CallObjectMethod(newQuiz, getDate);
        jdouble avgScore = env->CallDoubleMethod(newQuiz, getAvgScore);
        jdouble avgTime = env->CallDoubleMethod(newQuiz, getAvgTime);
        jint timeAllotted = env->CallIntMethod(newQuiz, getTimeAllotted);
        jint numberOfQuestions = env->CallIntMethod(newQuiz, getNumberOfQuestions);

        // Convert Java strings to C++ strings
        const char* nameStr = env->GetStringUTFChars(name, 0);
         const char* emailStr = env->GetStringUTFChars(email, 0);
        const char* topicStr = env->GetStringUTFChars(topic, 0);
        const char* dateStr = env->GetStringUTFChars(dateOfCreation, 0);

        // Write to file
        outfile << id << "," << nameStr << "," << topicStr << "," << emailStr << "," << dateStr << ","
                << avgScore << "," << avgTime << "," << timeAllotted << ","
                << numberOfQuestions << "," << endl;

        // Release memory for Java strings
        env->ReleaseStringUTFChars(name, nameStr);
        env->ReleaseStringUTFChars(topic, topicStr);
        env->ReleaseStringUTFChars(dateOfCreation, dateStr);

        // Close the file
        outfile.close();

        cout << "Quiz added to previous quizzes file!" << endl;

        return 0;  // Successfully added 
    }

    // Function to write questions to the made quiz file 
    JNIEXPORT jint JNICALL Java_makequiz_ReviewQuiz_WriteToQuizFile(JNIEnv *env, jobject obj, jobject newQuiz, jstring filename) {
        
        // Convert the jstring filename to a C++ string
        const char* filename_cstr = env->GetStringUTFChars(filename, 0);

        // Open file in append mode
        ofstream outfile(filename_cstr, ios::app); 

        // Check if the file was opened successfully
        if (!outfile.is_open()) {
            cerr << "Error: Unable to open file " << endl;
            env->ReleaseStringUTFChars(filename, filename_cstr); // Release the filename string
            return -1;
        }

        // Get newQuiz object and access its methods via JNI
        jclass quizClass = env->GetObjectClass(newQuiz);

        // Now get list of questions in jni context
        jmethodID getQuestions = env->GetMethodID(quizClass, "getQuestions", "()Ljava/util/List;");

        jobject questionsList = env->CallObjectMethod(newQuiz, getQuestions);
        jclass listClass = env->GetObjectClass(questionsList);

        jmethodID listSize = env->GetMethodID(listClass, "size", "()I");
        jint listSizeInt = env->CallIntMethod(questionsList, listSize);


        //iterate through the list of questions
        for (int i = 0; i < listSizeInt; ++i)
        {
            // Get question object
            jmethodID getMethod = env->GetMethodID(listClass, "get", "(I)Ljava/lang/Object;");
            jobject questionObj = env->CallObjectMethod(questionsList, getMethod, i);

            // Same for questionObj
            if (questionObj == nullptr) {
                std::cerr << "questionObj is not exist oops " << std::endl;
            }

            // Get the question class
            jclass questionClass = env->GetObjectClass(questionObj);

            // Get details of the question object
            jmethodID getText = env->GetMethodID(questionClass, "getText", "()Ljava/lang/String;");
            jmethodID getMarksForCorrect = env->GetMethodID(questionClass, "getMarksForCorrect", "()I");
            jmethodID getMarksForWrong = env->GetMethodID(questionClass, "getMarksForWrong", "()I");
           

            if (!getText || !getMarksForCorrect || !getMarksForWrong) {
                cerr << "Error: Method not found!" << endl;
                return -1;
            }

            // Get all the details 
            jint marksForCorrect = env->CallIntMethod(questionObj, getMarksForCorrect);
            jint marksForWrong = env->CallIntMethod(questionObj, getMarksForWrong);
            jstring questionText = (jstring)env->CallObjectMethod(questionObj, getText);

            // Convert question text to a C++ string
            const char* questionTextStr = env->GetStringUTFChars(questionText, 0);

            // Write question text to file
            outfile << questionTextStr <<",";
            env->ReleaseStringUTFChars(questionText, questionTextStr);

            // Get the correct option 
            jmethodID getCorrectAnswer = env->GetMethodID(questionClass, "getCorrectOption", "()Ljava/lang/Character;");
            jobject correctAnswerObj = env->CallObjectMethod(questionObj, getCorrectAnswer);
            jchar correctAnswer = env->CallCharMethod(correctAnswerObj, env->GetMethodID(env->GetObjectClass(correctAnswerObj), "charValue", "()C"));

            // Get map of options and the size of the map
            jmethodID getOptions = env->GetMethodID(questionClass, "getOptions", "()Ljava/util/Map;");
            jobject optionsMap = env->CallObjectMethod(questionObj, getOptions);

            jclass mapClass = env->GetObjectClass(optionsMap);
            jmethodID sizeMethod = env->GetMethodID(mapClass, "size", "()I");
            jint mapSizeInt = env->CallIntMethod(optionsMap, sizeMethod);
            jint numberOfOptions = env->CallIntMethod(optionsMap, sizeMethod);

            // write the number of options 
             outfile << numberOfOptions <<",";

            // Chec if there are options in the optionsMap
            if (optionsMap == nullptr) {
                cout << "No options provided" << endl;
            }

            // Get the keySet method and call it to get the Set of keys 
            jmethodID keySetMethod = env->GetMethodID(mapClass, "keySet", "()Ljava/util/Set;");
            jobject keySet = env->CallObjectMethod(optionsMap, keySetMethod);

            // Get the Set class and its 'iterator' method
            jclass setClass = env->GetObjectClass(keySet);
            jmethodID iteratorMethod = env->GetMethodID(setClass, "iterator", "()Ljava/util/Iterator;");
            jobject iterator = env->CallObjectMethod(keySet, iteratorMethod);

            // Get the Iterator class and its 'hasNext' and 'next' methods
            jclass iteratorClass = env->GetObjectClass(iterator);
            jmethodID hasNextMethod = env->GetMethodID(iteratorClass, "hasNext", "()Z");
            jmethodID nextMethod = env->GetMethodID(iteratorClass, "next", "()Ljava/lang/Object;");

            //iterate over the keyset using the iterator
            while (env->CallBooleanMethod(iterator, hasNextMethod))
            {
               // Get the next key 
               jobject keyObj = env->CallObjectMethod(iterator, nextMethod);

               // Convert the key - Character to jchar
               jchar keyChar = env->CallCharMethod(keyObj, env->GetMethodID(env->FindClass("java/lang/Character"), "charValue", "()C"));

               // Now get the corresponding value from the map using the get method
               jmethodID getMethod = env->GetMethodID(mapClass, "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
               jobject mapValue = env->CallObjectMethod(optionsMap, getMethod, keyObj);

               // Convert the map value String to a C++ string
               const char* optionValueStr = env->GetStringUTFChars((jstring)mapValue, nullptr);

                // Write the option to file
                outfile << (char)keyChar << "," << optionValueStr << ",";

                // Release resources for the strings
                env->ReleaseStringUTFChars((jstring)mapValue, optionValueStr);
           }
            // End iterating through options

            // Write correct answer to file 
            outfile << (char)correctAnswer << ",";

            // write marks for wrogn n right 
            outfile << marksForCorrect << "," << marksForWrong;

            outfile << endl;
        }
        // Stop iterating thorugh the Questions list

        // Close the file
        outfile.close();

        // Release resources
        env->ReleaseStringUTFChars(filename, filename_cstr);
        
        cout << "Quiz Data appended successfully to quiz file!" << endl;
       
        return 0;  // Successfully written questions 
    }
}


/*
int main() {
       try {
        // Example file name and quiz data
        jstring filename = env->NewStringUTF("quiz123.csv");
        jint quizID = 123;
        jstring name = env->NewStringUTF("Sample Quiz");
        jstring topic = env->NewStringUTF("General Knowledge");
        jstring username = env->NewStringUTF("user123");
        jint timeAllo = 30;  // Time allowed for the quiz in minutes
        jint nq = 10;  // Number of questions in the quiz
        jstring date = env->NewStringUTF("2024-11-27");

        // Create an object of the ReviewQuiz class
        jclass quizClass = env->FindClass("makequiz/ReviewQuiz");
        if (quizClass == nullptr) {
            cerr << "Error: Class not found!" << endl;
            return -1;
        }

        jobject newQuiz = env->NewObject(quizClass, env->GetMethodID(quizClass, "<init>", "()V"));
        if (newQuiz == nullptr) {
            cerr << "Error: Unable to create object!" << endl;
            return -1;
        }

        // Call MakeIQuizFile function to create the quiz file
        jint result = Java_makequiz_ReviewQuiz_MakeIQuizFile(env, nullptr, newQuiz, filename, quizID, name, topic, username, timeAllo, nq, date);
        if (result != 0) {
            cerr << "Error: Failed to create quiz file!" << endl;
            return -1;
        }

        // Add the quiz to the previous quiz file
        result = Java_makequiz_ReviewQuiz_AddToPrevQuizFile(env, nullptr, newQuiz);
        if (result != 0) {
            cerr << "Error: Failed to add quiz to previous quizzes!" << endl;
            return -1;
        }

        // Write questions to the quiz file
        result = Java_makequiz_ReviewQuiz_WriteToQuizFile(env, nullptr, newQuiz, filename);
        if (result != 0) {
            cerr << "Error: Failed to write quiz questions!" << endl;
            return -1;
        }

        cout << "Quiz data processed successfully!" << endl;

    } catch (const exception& e) {
        cerr << "Error: " << e.what() << endl;
        return -1;
    }

    return 0;  // Success
}
*/
