package com.qualcomm.ftcrobotcontroller.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Charles on 9/8/2015.
 */
public class TestDrive extends OpMode {


    float LiftPower;


    DcMotor Right;
    DcMotor Left;

    DcMotor LiftR;
    DcMotor LiftL;




    public TestDrive() {

    }

    @Override
    public void init() {




        Right = hardwareMap.dcMotor.get("Right");
        Right.setDirection(DcMotor.Direction.REVERSE);
        Left = hardwareMap.dcMotor.get("Left");

        Right.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        Left.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        LiftR = hardwareMap.dcMotor.get("LiftR");
        LiftL = hardwareMap.dcMotor.get("LiftL");



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
        float direction = gamepad1.right_stick_x;
        float right = throttle - direction;
        float left = throttle + direction;

        /*
        if (gamepad1.left_stick_x >= 0.08) {
            right = right*turnLimiter;
        }
        else if (gamepad1.left_stick_x <= -0.08) {
            left = left*turnLimiter;
        }
        */

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);
        // clip the right/left values so that the values never exceed +/- 1



        // write the values to the motors

        Right.setPower(right);
        Left.setPower(left);


        //Beater motor's code
        /*
        if(gamepad1.x) {

            BeaterBarPower = -0.8;
        }
        else if (gamepad1.b) {

            BeaterBarPower = 0.8;
        }
        else BeaterBarPower = 0;

        BeaterBar.setPower(BeaterBarPower);
        */


        // Code to drive the ski lift
        LiftPower = -1*gamepad2.right_stick_y;

        LiftR.setPower(LiftPower);
        LiftL.setPower(LiftPower);

        // Code fot the Gandala wrist that swings back and forth the score balls and blocks
        /*
        if (gamepad2.b) {
            // when B on gamepad1 is pushed, put servo to the left
            WristTargetPosition = 1;

        }
        else if (gamepad2.x) {
            // when X on gamepad1 is pushed, put servo to the right
            WristTargetPosition = 0;
        }
        else if (gamepad2.a) {
            // when A is pushed, reset servo to the middle
            WristTargetPosition = 0.5;
        }

        // Code for the Gate on the Gandala Cart

        if (gamepad2.left_bumper) {
            // Left Bumper puts the gate down to stop the balls and blocks from falling
            GateTargetPosition = 1;
        }
        else if (gamepad2.right_bumper) {
            // Right Bumper lifts Gate to score the blocks and balls
            GateTargetPosition = 0;
        }

        // After getting all the values for the servos set their position to what we have
        // decided in the previous else ifs
        Gate.setPosition(GateTargetPosition);
        Wrist.setPosition(WristTargetPosition);



		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData(" pwr R ",  " pwr: " + right);
        telemetry.addData(" pwr L ",  " pwr: " + left);
        telemetry.addData(" Encoder R ",  " pwr: " + Right.getCurrentPosition());
        telemetry.addData(" Encoder L ",  " pwr: " + Left.getCurrentPosition());
        telemetry.addData("Lift Power","" + LiftPower);


        //if(WristTargetPosition == 0.5 && (0.48 >= Wrist.getPosition() || Wrist.getPosition() <= 0.52)) {
           // Wrist.getController().pwmDisable();
        //}



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
