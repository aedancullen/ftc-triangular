package io.github.aedancullen.triangular;

import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import io.github.cn04.triangular.R;

public class VisionProcessor implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static FtcRobotControllerActivity robotControllerActivity;
    private static final String TAG = "VisionProcessor";
    private CameraBridgeViewBase mOpenCvCameraView;

    public static VisionProcessor fromHardwareMap(HardwareMap hardwareMap) {
        try {
            return ((FtcRobotControllerActivity) hardwareMap.appContext).visionProcessor;
        }
        catch (Exception e) {
            throw new UnsupportedOperationException("The modified FtcRobotControllerActivity with Triangular support is not running");
        }
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(robotControllerActivity) {
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

    public VisionProcessor() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");

        LinearLayout cameraViewParent = (LinearLayout) robotControllerActivity.findViewById(R.id.cameraMonitorViewId);
        mOpenCvCameraView = new JavaCameraView(robotControllerActivity, 0);
        cameraViewParent.addView(mOpenCvCameraView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.enableFpsMeter();

        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    public void onPause()
    {
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onResume()
    {
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization of 2.4.13");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_13, robotControllerActivity, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
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