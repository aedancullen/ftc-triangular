package io.github.aedancullen.triangular.system;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.NativeCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import io.github.cn04.triangular.R;

public class VisionProcessor implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "VisionProcessor";
    private CameraBridgeViewBase mOpenCvCameraView;
    private Context appContext;
    private Activity activity;

    //
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
            return newVisionProcessor;
        }
    }
    //

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(activity) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
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
        Log.i(TAG, "Instantiated new " + this.getClass());

        LinearLayout cameraViewParent = (LinearLayout) activity.findViewById(R.id.cameraMonitorViewId);
        mOpenCvCameraView = new JavaCameraView(activity, 0);
        cameraViewParent.addView(mOpenCvCameraView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.enableFpsMeter();

        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    public void start()
    {
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization of 2.4.13");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_13, activity, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void stop() {
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return inputFrame.rgba();
    }

}