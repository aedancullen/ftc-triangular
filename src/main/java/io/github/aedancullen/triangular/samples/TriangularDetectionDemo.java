package io.github.aedancullen.triangular.samples;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import io.github.aedancullen.triangular.system.VisionProcessor;


@TeleOp(name="triangular: Detection Demo", group="Triangular")
//@Disabled
public class TriangularDetectionDemo extends OpMode {

    VisionProcessor visionProcessor;

    public void init() {
        visionProcessor = VisionProcessor.beginOnUiThread(hardwareMap.appContext);
        visionProcessor.start(0);
    }

    public void loop() {}

    public void stop() {
        visionProcessor.stop();
    }

}
