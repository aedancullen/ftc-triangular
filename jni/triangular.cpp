
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

extern "C" JNIEXPORT jboolean JNICALL Java_io_github_aedancullen_triangular_system_NativePart_isInitialized(JNIEnv*, jobject) {
    return tracker.isInitialized();
}

extern "C" JNIEXPORT jintArray JNICALL Java_io_github_aedancullen_triangular_system_NativePart_getPosition(JNIEnv*, jobject) {
    Rect rect =  tracker.getPosition();
    int array [4];
    array[0] = rect.x;
    array[1] = rect.y;
    array[2] = rect.width;
    array[3] = rect.height;
    return (jintArray)array;
}

extern "C" JNIEXPORT jint JNICALL Java_io_github_aedancullen_triangular_system_NativePart_getState(JNIEnv*, jobject) {
    return tracker.getState();
}