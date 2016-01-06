package com.qualcomm.ftcrobotcontroller.controls;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by root on 10/24/15.
 */
public class Controls {

    public float motorRightSpeed = 0;
    public float motorLeftSpeed = 0;
    public float armExtendSpeed = 0;
    public float gondolaAngle = 0;

    public static float ARM_SPEED = .3f;

    private Gamepad gamepad1;
    private Gamepad gamepad2;

    public Controls(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }

    public void readJoySticksAndSetMotors() {
        drivePower();

        armExtend();
    }

    private void armExtend() {
        if (gamepad2.a) {
            armExtendSpeed = ARM_SPEED;
        } else if (gamepad2.b) {
            armExtendSpeed = ARM_SPEED*-1;
        } else {
            armExtendSpeed = 0;
        }
    }

    private void drivePower() {
        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.right_stick_x;
        float right = throttle - direction;
        float left = throttle + direction;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        motorRightSpeed = (float)scaleInput(right);
        motorLeftSpeed =  (float)scaleInput(left);
    }

    /*
	 * This method scales the joystick input so for low joystick values, the
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 */
    private double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}
