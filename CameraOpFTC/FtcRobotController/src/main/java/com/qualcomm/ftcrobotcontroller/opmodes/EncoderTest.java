package com.qualcomm.ftcrobotcontroller.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
/**
 * Created by Charles on 9/17/2015.
 */
public class EncoderTest extends OpMode {

    DcMotor TestMotor;



    public EncoderTest() {

    }

    @Override
    public void init() {


        TestMotor = hardwareMap.dcMotor.get("motor1");

        TestMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }

    double power = 0;


    @Override
    public void loop() {



        if ( TestMotor.getCurrentPosition() < 5000) {
            // when A on gamepad1 is pushed, put servo to the left
            power = gamepad1.right_stick_y;
        }
        else {
           // when X on gamepad1 is pushed, put servo to the right
             power = 0;
        }

        TestMotor.setPower(power);



        // Feed me back the info to tell me if its encoder running or by power
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData(" power ","" + power);
        telemetry.addData("Encoder","" + TestMotor.getCurrentPosition());
    }


}
