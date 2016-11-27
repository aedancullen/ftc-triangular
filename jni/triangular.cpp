
#include <jni.h>
#include <mosse.cpp>

Tracker tracker;


extern "C" JNIEXPORT jint JNICALL Java_io_github_aedancullen_triangular_system_NativePart_test(JNIEnv*, jobject, jint testValue) {
    return testValue * 2;
}


extern "C" JNIEXPORT void JNICALL Java_io_github_aedancullen_triangular_system_NativePart_InitTracker(JNIEnv*, jobject, jlong matAddr, jint x, jint y, jint w, jint h) {
    tracker.InitTracker(*(Mat*)matAddr, *new Rect(x, y, w, h));
}

extern "C" JNIEXPORT void JNICALL Java_io_github_aedancullen_triangular_system_NativePart_Track(JNIEnv*, jobject, jlong matAddr) {
    tracker.Track(*(Mat*)matAddr);
}