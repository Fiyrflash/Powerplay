package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (group = "FSM")
public class driveConstantsFSM extends OpMode {

    public Servo lefts;
    public DcMotorEx crane;

    public void runOpMode() {

        lefts = hardwareMap.get(Servo.class, "LeftsFront");
        crane = hardwareMap.get(DcMotorEx.class, "CraneFront");

    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
