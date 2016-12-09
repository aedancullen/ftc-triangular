package io.github.aedancullen.triangular;


public class NativePart {

    public native int test(int testValue);


    public native void InitTracker(long matAddr, int x, int y, int w, int h);

    public native void Track(long matAddr);

    public native boolean isInitialized();

    public native int[] getPosition();

    public native int getState();

}
