package io.github.aedancullen.triangular.system;

/**
 * Created by aedan on 11/26/16.
 */

public class NativePart {

    public native int test(int testValue);


    public native void InitTracker(long matAddr, int x, int y, int w, int h);

    public native void Track(long matAddr);

}
