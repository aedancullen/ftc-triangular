package io.github.aedancullen.triangular.system;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;


public class VisionProcessor implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "VisionProcessor";
    private CameraBridgeViewBase cameraView;
    private Context appContext;
    private Activity activity;
    private LinearLayout cameraViewParent;

    // A hacky way to get a new VisionProcessor running on the UI thread
    private static VisionProcessor newVisionProcessor;

    public static VisionProcessor beginOnUiThread(final Context context) {
        Runnable starter = new Runnable() {
            public void run() {
                VisionProcessor.newVisionProcessor =  new VisionProcessor(context);
                synchronized (this) {
                    this.notify();
                }
            }
        };
        synchronized (starter) {
            ((Activity)context).runOnUiThread(starter);
            try {starter.wait();} catch(InterruptedException e) {}
            VisionProcessor returnedProcessor = newVisionProcessor;
            newVisionProcessor = null;
            return returnedProcessor;
        }
    }
    //

    private BaseLoaderCallback loaderCallback = new BaseLoaderCallback(activity) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    System.loadLibrary("triangular-native");
                    cameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public VisionProcessor(Context context) {
        appContext = context;
        activity = (Activity)context;
        cameraView = new JavaCameraView(activity, 0);
        //cameraView.setVisibility(SurfaceView.GONE);
        //cameraViewParent = (LinearLayout) activity.findViewById(R.id.cameraMonitorViewId);
        //cameraViewParent.addView(cameraView, new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY));
        //cameraViewParent.addView(cameraView);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        activity.setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);

        WindowManager wm = (WindowManager) appContext
                .getSystemService(Context.WINDOW_SERVICE);

        wm.addView(cameraView, params);

        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    public void start(int cameraId) {

        //cameraView.setVisibility(SurfaceView.VISIBLE);
        cameraView.setCameraIndex(cameraId);
        //cameraView.enableFpsMeter();
        cameraView.setCvCameraViewListener(this);

        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization of 3.1.0");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, activity, loaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void stop() {
        if (cameraView != null) {
            cameraView.disableView();
        }
    }

    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return inputFrame.rgba();
    }

}