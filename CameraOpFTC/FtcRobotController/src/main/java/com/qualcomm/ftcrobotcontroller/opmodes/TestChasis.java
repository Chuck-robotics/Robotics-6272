package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Charles on 9/28/2015.
 */
public class TestChasis extends OpMode {

    DcMotor FrontRight;
    DcMotor FrontLeft;
    TouchSensor touchSensor;
    OpticalDistanceSensor light;


    public TestChasis() {

    }

    @Override
    public void init() {


        FrontRight = hardwareMap.dcMotor.get("FrontRight");
        FrontLeft = hardwareMap.dcMotor.get("FrontLeft");

        FrontLeft.setDirection(DcMotor.Direction.REVERSE);

        touchSensor = hardwareMap.touchSensor.get("touch");
        light = hardwareMap.opticalDistanceSensor.get("light");





    }

    @Override
    public void loop() {

		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

        // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
        // 1 is full down
        // direction: left_stick_x ranges from -1 to 1, where -1 is full left
        // and 1 is full right
        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.left_stick_x;
        float rightPower = throttle - direction;
        float leftPower = throttle + direction;

        // clip the right/left values so that the values never exceed +/- 1
        rightPower = Range.clip(rightPower, -1, 1);
        leftPower = Range.clip(leftPower, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        rightPower =  (float)scaleInput(rightPower);
        leftPower =  (float)scaleInput(leftPower);

        // write the values to the motors

            if(touchSensor.isPressed()) {
                //Stop the motors if the touch sensor is pressed
                FrontLeft.setPower(0);
                FrontRight.setPower(0);
            } else {
                //Keep driving if the touch sensor is not pressed
                FrontLeft.setPower(leftPower);
                FrontRight.setPower(rightPower);
            }

        telemetry.addData("isPressed", String.valueOf(touchSensor.isPressed()));

		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */

        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", leftPower));
        telemetry.addData("Light Value", String.valueOf(light.getLightDetected()));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", rightPower));

    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }

}
