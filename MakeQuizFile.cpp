#include <jni.h>
#include <fstream>
#include <iostream>
#include <string>
#include <list>
#include <string>

#include "MakeQuiz.h"
#include "review.h"

using namespace std;

extern "C" {

    JNIEXPORT jint Java_review_MakeIQuizFile(JNIEnv *env, jobject obj, jobject newQuiz, jstring filename, jint id, jstring name, jstring topic, jstring username, jint timeAllo, jint nq, jstring date)
    {
        cout<<"hello in makefilecpp"<<endl;

        // Convert jstring filename to C++ string --correct
        const char *filenameChars = env->GetStringUTFChars(filename, NULL);
        string cppFilename(filenameChars);

        cout <<"file after conversion " + cppFilename<<endl;

        env->ReleaseStringUTFChars(filename, filenameChars);

        // Get method IDs for setters in the Java class
        jclass quizClass = env->GetObjectClass(newQuiz);
        jmethodID setIDMethod = env->GetMethodID(quizClass, "setID", "(I)V");
        jmethodID setNameMethod = env->GetMethodID(quizClass, "setName", "(Ljava/lang/String;)V");
        jmethodID setTopicMethod = env->GetMethodID(quizClass, "setTopic", "(Ljava/lang/String;)V");
        jmethodID setUsernameMethod = env->GetMethodID(quizClass, "setUsername", "(Ljava/lang/String;)V");
        jmethodID setDateMethod = env->GetMethodID(quizClass, "setDateOfCreation", "(Ljava/lang/String;)V");
        jmethodID setTimeAllottedMethod = env->GetMethodID(quizClass, "setTimeAllotted", "(I)V");
        jmethodID setNumberOfQuestionsMethod = env->GetMethodID(quizClass, "setNumberOfQuestions", "(I)V");

        // Call setter methods on the Java object
        env->CallVoidMethod(newQuiz, setIDMethod, id);
        env->CallVoidMethod(newQuiz, setDateMethod, date);
        env->CallVoidMethod(newQuiz, setNameMethod, name);
        env->CallVoidMethod(newQuiz, setTopicMethod, topic);
        env->CallVoidMethod(newQuiz, setUsernameMethod, username);
        env->CallVoidMethod(newQuiz, setTimeAllottedMethod, timeAllo);
        env->CallVoidMethod(newQuiz, setNumberOfQuestionsMethod, nq);

        if (!setIDMethod || !setNameMethod || !setTopicMethod || !setUsernameMethod || !setTimeAllottedMethod || !setNumberOfQuestionsMethod || !setDateMethod) {
            cerr << "Error: Could not find setter methods" << endl;
            return -1;
        }

        // Open file for writing
        ofstream outfile(cppFilename, ios::out);
        
        if (!outfile.is_open()) {
            cerr << "Error opening file for writing." << std::endl;
            return -1;  
        }

        // Get getters from the Java object
        jmethodID getIDMethod = env->GetMethodID(quizClass, "getID", "()I");
        jmethodID getNameMethod = env->GetMethodID(quizClass, "getName", "()Ljava/lang/String;");
        jmethodID getTopicMethod = env->GetMethodID(quizClass, "getTopic", "()Ljava/lang/String;");
        jmethodID getUsernameMethod = env->GetMethodID(quizClass, "getUsername", "()Ljava/lang/String;");
        jmethodID getDateMethod = env->GetMethodID(quizClass, "getDateOfCreation", "()Ljava/lang/String;");
        jmethodID getAvgScoreMethod = env->GetMethodID(quizClass, "getAvgScore", "()D");
        jmethodID getAvgTimeMethod = env->GetMethodID(quizClass, "getAvgTime", "()D");
        jmethodID getTimeAllottedMethod = env->GetMethodID(quizClass, "getTimeAllotted", "()I");
        jmethodID getNumberOfQuestionsMethod = env->GetMethodID(quizClass, "getNumberOfQuestions", "()I");

        // Call getter methods to retrieve values
        jint idVal = env->CallIntMethod(newQuiz, getIDMethod);
        jstring nameVal = (jstring) env->CallObjectMethod(newQuiz, getNameMethod);
        jstring topicVal = (jstring) env->CallObjectMethod(newQuiz, getTopicMethod);
        jstring usernameVal = (jstring) env->CallObjectMethod(newQuiz, getUsernameMethod);
        jstring dateOfCreationVal = (jstring) env->CallObjectMethod(newQuiz, getDateMethod);
        jdouble avgScoreVal = env->CallDoubleMethod(newQuiz, getAvgScoreMethod);
        jdouble avgTimeVal = env->CallDoubleMethod(newQuiz, getAvgTimeMethod);
        jint timeAllottedVal = env->CallIntMethod(newQuiz, getTimeAllottedMethod);
        jint numOfQuestionsVal = env->CallIntMethod(newQuiz, getNumberOfQuestionsMethod);

        // Convert Java strings to C++ strings
        const char *nameStr = env->GetStringUTFChars(nameVal, NULL);
        const char *topicStr = env->GetStringUTFChars(topicVal, NULL);
        const char *usernameStr = env->GetStringUTFChars(usernameVal, NULL);
        const char *dateOfCreationStr = env->GetStringUTFChars(dateOfCreationVal, NULL);

        // Write data to file
        outfile << idVal << "," << nameStr << "," << topicStr<< "," << usernameStr<< ","<< dateOfCreationStr<< ","<< avgScoreVal<< ","<< avgTimeVal<< ","<< timeAllottedVal<< ","<< numOfQuestionsVal <<",";

        // Release memory for Java strings
        env->ReleaseStringUTFChars(nameVal, nameStr);
        env->ReleaseStringUTFChars(topicVal, topicStr);
        env->ReleaseStringUTFChars(usernameVal, usernameStr);
        env->ReleaseStringUTFChars(dateOfCreationVal, dateOfCreationStr);

        cout<<"file made"<<endl;

        // Close the file
        outfile.close(); 

        return 0; 
    }
















    JNIEXPORT jint JNICALL Java_review_AddToPrevQuizFile(JNIEnv *env, jobject obj, jobject newQuiz)
    {

        string filename = "PreviousQuizzez.csv"; 

        // Open the file for writing in append mode
        ofstream outfile(filename, ios::app);

        // Check if the file was successfully opened, exit if cannot be opened
        if (!outfile.is_open())
        {
            cerr << "Error: Unable to write to " << filename << endl;
            return -1;  
        }

        // Get newQuiz object from obj in JNI context
        jclass quizClass = env->GetObjectClass(newQuiz);

       // Get getters from the Java object
               jmethodID getID = env->GetMethodID(quizClass, "getID", "()I");
               jmethodID getName= env->GetMethodID(quizClass, "getName", "()Ljava/lang/String;");
               jmethodID getTopic = env->GetMethodID(quizClass, "getTopic", "()Ljava/lang/String;");

               jmethodID getDate= env->GetMethodID(quizClass, "getDateOfCreation", "()Ljava/lang/String;");
               jmethodID getAvgScore = env->GetMethodID(quizClass, "getAvgScore", "()D");
               jmethodID getAvgTime = env->GetMethodID(quizClass, "getAvgTime", "()D");
               jmethodID getTimeAllotted= env->GetMethodID(quizClass, "getTimeAllotted", "()I");
               jmethodID getNumberOfQuestions = env->GetMethodID(quizClass, "getNumberOfQuestions", "()I");


        // Ensure the methods exist
        if (!getID || !getName || !getTopic || !getDate || !getAvgScore || !getAvgTime || !getTimeAllotted || !getNumberOfQuestions)
        {
            cerr << "Error: Could not find necessary getter methods for newQuiz" << endl;
            return -1;  // Return error code if any method is missing
        }

        // Call getters for jni values
        jint id = env->CallIntMethod(newQuiz, getID);
        jstring name = (jstring)env->CallObjectMethod(newQuiz, getName);
        jstring topic = (jstring)env->CallObjectMethod(newQuiz, getTopic);
        jstring dateOfCreation = (jstring)env->CallObjectMethod(newQuiz, getDate);
        jdouble avgScore = env->CallDoubleMethod(newQuiz, getAvgScore);
        jdouble avgTime = env->CallDoubleMethod(newQuiz, getAvgTime);
        jint timeAllotted = env->CallIntMethod(newQuiz, getTimeAllotted);
        jint numberOfQuestions = env->CallIntMethod(newQuiz, getNumberOfQuestions);



        // Convert Java strings to C++ strings
        const char* nameStr = env->GetStringUTFChars(name, 0);
        const char* topicStr = env->GetStringUTFChars(topic, 0);
        const char* dateStr = env->GetStringUTFChars(dateOfCreation, 0);

        // Write to file
        outfile << id << "," << nameStr << "," << topicStr << "," << dateStr << ","
                << avgScore << "," << avgTime << "," << timeAllotted << ","
                << numberOfQuestions << "," << endl;


        // Release memory for Java strings
        env->ReleaseStringUTFChars(name, nameStr);
        env->ReleaseStringUTFChars(topic, topicStr);
        env->ReleaseStringUTFChars(dateOfCreation, dateStr);

        // Close the file
        outfile.close();

        cout << "Quiz added to previous quizzes file!" << endl;

        return 0;  
    }

    JNIEXPORT jint JNICALL Java_review_WriteToQuizFile(JNIEnv *env, jobject obj, jobject newQuiz, jstring filename)
    {
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

        cout <<"question lit toh mil gaya" <<endl;
        cout << "lsit size" << listSizeInt <<endl;


        //iterate through the list of questions
        for (int i = 0; i < listSizeInt; ++i)
        {
            // get question object
            jmethodID getMethod = env->GetMethodID(listClass, "get", "(I)Ljava/lang/Object;");
            jobject questionObj = env->CallObjectMethod(questionsList, getMethod, i);

            // Same for questionObj
            if (questionObj == nullptr) {
                std::cerr << "questionObj is not exist oops " << std::endl;
            }

            // Get the question class
            jclass questionClass = env->GetObjectClass(questionObj);

            // get details of the question object
            jmethodID getQuestionText = env->GetMethodID(questionClass, "getText", "()Ljava/lang/String;");

         // call getters for getting jni value
            jstring questionText = (jstring)env->CallObjectMethod(questionObj, getQuestionText);

            // Convert question text to a C++ string
            const char* questionTextStr = env->GetStringUTFChars(questionText, 0);

            // Write question text to file
            outfile << questionTextStr <<",";
            env->ReleaseStringUTFChars(questionText, questionTextStr);

            cout <<" questions detailsss mil gyiii excpet correct ans "<<endl;

            jmethodID getCorrectAnswer = env->GetMethodID(questionClass, "getCorrectOption", "()Ljava/lang/Character;");
            jobject correctAnswerObj = env->CallObjectMethod(questionObj, getCorrectAnswer);
            jchar correctAnswer = env->CallCharMethod(correctAnswerObj, env->GetMethodID(env->GetObjectClass(correctAnswerObj), "charValue", "()C"));


            cout <<"got correct option" <<endl;

            // Get map of options and the size of the map
            jmethodID getOptions = env->GetMethodID(questionClass, "getOptions", "()Ljava/util/Map;");
            jobject optionsMap = env->CallObjectMethod(questionObj, getOptions);

            jclass mapClass = env->GetObjectClass(optionsMap);
            jmethodID sizeMethod = env->GetMethodID(mapClass, "size", "()I");
            jint mapSizeInt = env->CallIntMethod(optionsMap, sizeMethod);

            // And for optionsMap
            if (optionsMap == nullptr) {
                  std::cerr << "optionsMap is null!" << std::endl;
            }

            cout <<"got options map"<<endl;


            // Get the 'keySet' method and call it to get the Set of keys (option labels)
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

            cout <<" go tkeys n iterator" <<endl;

            //iterate over the keyset using the iterator
               while (env->CallBooleanMethod(iterator, hasNextMethod))
               {
                   // Get the next key (which is a Character)
                   jobject keyObj = env->CallObjectMethod(iterator, nextMethod);

                   // Convert the key (Character) to jchar
                   jchar keyChar = env->CallCharMethod(keyObj, env->GetMethodID(env->FindClass("java/lang/Character"), "charValue", "()C"));

                   // Now get the corresponding value from the map using the 'get' method
                   jmethodID getMethod = env->GetMethodID(mapClass, "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
                   jobject mapValue = env->CallObjectMethod(optionsMap, getMethod, keyObj);

                   // Convert the map value (String) to a C++ string
                   const char* optionValueStr = env->GetStringUTFChars((jstring)mapValue, nullptr);

                    // Write the option to file (you can format it as needed, such as "A: 'Option Text'")
                    outfile << (char)keyChar << "," << optionValueStr << ",";

                    cout <<"wrote option"<< endl;
                    // Release resources for the strings
                    env->ReleaseStringUTFChars((jstring)mapValue, optionValueStr);

                    // Write correct answer
                    outfile << (char)correctAnswer << ","<< endl;
               }
               // end iterating through options


        }
        // stop iterating thorugh the questions list

        // Close the file
        outfile.close();

        // Release resources
        env->ReleaseStringUTFChars(filename, filename_cstr);
        
        cout << "Quiz Data appended successfully!" << endl;
        //Java_review_AddToPrevQuizFile(env, obj);

        return 0;  // Success
    }

}

