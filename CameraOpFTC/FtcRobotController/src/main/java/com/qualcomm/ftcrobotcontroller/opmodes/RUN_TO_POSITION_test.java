package com.qualcomm.ftcrobotcontroller.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
/**
 * Created by Charles on 9/17/2015.
 */
public class RUN_TO_POSITION_test extends OpMode {

    DcMotor TestMotor;



    public RUN_TO_POSITION_test() {

    }

    @Override
    public void init() {


        TestMotor = hardwareMap.dcMotor.get("motor1");

        TestMotor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
    }


    @Override
    public void loop() {



        if ( gamepad1.b) {
            // when A on gamepad1 is pushed, put servo to the left
            TestMotor.setTargetPosition(5000);
            TestMotor.setPower(1);
        }
        else if (gamepad1.x) {
            // when X on gamepad1 is pushed, put servo to the right
            TestMotor.setTargetPosition(0);
            TestMotor.setPower(-1);
        }





        // Feed me back the info to tell me if its encoder running or by power
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("Encoder Target: ","" + TestMotor.getTargetPosition());
        telemetry.addData("Encoder Target: ","" + TestMotor.getPower());
        telemetry.addData("Encoder: ","" + TestMotor.getCurrentPosition());
    }


}
