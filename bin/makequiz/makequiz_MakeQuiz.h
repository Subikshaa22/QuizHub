/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class makequiz_MakeQuiz */

#ifndef _Included_makequiz_MakeQuiz
#define _Included_makequiz_MakeQuiz
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     makequiz_MakeQuiz
 * Method:    checkIfTopicExists
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_makequiz_MakeQuiz_checkIfTopicExists
  (JNIEnv *, jobject, jstring);

/*
 * Class:     makequiz_MakeQuiz
 * Method:    saveTopic
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_makequiz_MakeQuiz_saveTopic
  (JNIEnv *, jobject, jstring);

/*
 * Class:     makequiz_MakeQuiz
 * Method:    displayTopics
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_makequiz_MakeQuiz_displayTopics
  (JNIEnv *, jobject);

/*
 * Class:     makequiz_MakeQuiz
 * Method:    getCurrentDate
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_makequiz_MakeQuiz_getCurrentDate
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
