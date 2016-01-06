package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Charles on 12/3/2015.
 */
public class Autonomous extends OpMode{

    int Position = 9064;

    DcMotor Right;
    DcMotor Left;

    public Autonomous() {

    }

    @Override
    public void init() {


        Right = hardwareMap.dcMotor.get("Right");
        Right.setDirection(DcMotor.Direction.REVERSE);
        Left = hardwareMap.dcMotor.get("Left");

        Right.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        //Gate starts at 0 and the Wrist starts at 90 and goes to 0 and 180 to dump in baskets]

    }

    public void loop() {

        Right.setTargetPosition(Position);
        Right.setPower(1.0);
        Left.setPower(1.0);

        if (Right.getCurrentPosition() >= Position)
        {
            Right.setPower(0.0);
            Left.setPower(0.0);
        }



        telemetry.addData("Pos Val", Position);
        telemetry.addData("Pos L", Right.getCurrentPosition());
        telemetry.addData("Pos R", Left.getCurrentPosition());

    }
}
