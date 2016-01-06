package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Charles on 11/28/2015.
 */

public class LiftTest  extends OpMode{

    float LiftPower;


    //DcMotor Right;
    //DcMotor Left;

    DcMotor LiftR1;
    DcMotor LiftR2;
    DcMotor LiftL1;
    DcMotor LiftL2;




    public LiftTest() {

    }

    @Override
    public void init() {




       // Right = hardwareMap.dcMotor.get("Right");
       // Right.setDirection(DcMotor.Direction.REVERSE);
       // Left = hardwareMap.dcMotor.get("Left");

        //Controller 2 port 1
        LiftR1 = hardwareMap.dcMotor.get("LiftR1");

        //Controller 2 port 2
        LiftR2 = hardwareMap.dcMotor.get("LiftR2");
        //Controller 3 port 1
        LiftL1 = hardwareMap.dcMotor.get("LiftL1");
        //Controller 3 port 2
        LiftL2 = hardwareMap.dcMotor.get("LiftL2");



    }

    @Override
    public void loop() {

        LiftPower = gamepad2.right_stick_y;

        LiftR1.setPower(LiftPower);
        LiftR2.setPower(LiftPower);
        LiftL1.setPower(LiftPower);
        LiftL2.setPower(LiftPower);

    }



}
