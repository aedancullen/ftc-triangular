package io.github.aedancullen.triangular;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.Arrays;

import io.github.aedancullen.fruity.FruityController;
import io.github.aedancullen.fruity.MotorConfigurations;

/**
 * ----- Fruity Omnidirectional Control System for FTC -----
 *
 * FruityGamepadDriving.java
 * A fun demo of omnidirectional driving capability, made simple!
 *
 * (c) 2016 Aedan Cullen. Distributed under the GNU GPLv3 license.
 */

@TeleOp(name="fruity: Gamepad Driving Demo", group="Fruity")
//@Disabled

public class FruityGamepadDriving extends OpMode {

    FruityController fruity;

    public void init() {
        fruity = new FruityController(hardwareMap, telemetry, "",
                Arrays.asList(
                        hardwareMap.dcMotor.get("dcOmni0"),
                        hardwareMap.dcMotor.get("dcOmni90"),
                        hardwareMap.dcMotor.get("dcOmni180"),
                        hardwareMap.dcMotor.get("dcOmni270")
                ),
                DcMotorSimple.Direction.REVERSE,
                DcMotor.RunMode.RUN_USING_ENCODER,
                MotorConfigurations.QUAD_NONDIAGONAL_SHORT);
        fruity.setupRamper(0.002, 0.002);
    }

    public void loop() {
        fruity.handleGamepad(gamepad1);
    }

}
