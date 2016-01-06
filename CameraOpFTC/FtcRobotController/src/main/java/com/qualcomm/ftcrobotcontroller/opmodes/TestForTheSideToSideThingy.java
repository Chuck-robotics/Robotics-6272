package com.qualcomm.ftcrobotcontroller.opmodes;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Charles on 11/23/2015.
 */






public class TestForTheSideToSideThingy extends OpMode {

    Servo SideToSide;

    double Position;
    double delta;


    public TestForTheSideToSideThingy() {

    }

    @Override
    public void init() {


        SideToSide = hardwareMap.servo.get("SideToSide");

        //Gate starts at 0 and the Wrist starts at 90 and goes to 0 and 180 to dump in baskets]

    }


    @Override
    public void loop() {

        delta = 0.01;

        if (gamepad1.right_bumper) {
            Position -= delta;
        } else if (gamepad1.left_bumper) {
            Position += delta;
        }

        if (Position > 1.0){
            Position = 1.0;
        }
        if (Position < 0.0){
            Position = 0.0;
        }

        SideToSide.setPosition(Position);


        telemetry.addData("Pos Val", String.format("%.2f", Position));
        telemetry.addData("Pos L", String.format("%.2f", SideToSide.getPosition()));

    }
}