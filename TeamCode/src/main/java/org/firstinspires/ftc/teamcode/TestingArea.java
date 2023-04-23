package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp

public class TestingArea extends OpMode {

    public DcMotor crane;
    public CRServo lefts;

    public DcMotorSimple frontLeft;
    public DcMotorSimple frontRight;
    public DcMotorSimple backLeft;
    public DcMotorSimple backRight;

    double cranepower;

    float pickup;
    float dropoff;

    double turn;
    double throttle;
    float strafeLeft;
    float strafeRight;

    final int CRANE_LOW = -2500;
    final int CRANE_MID = -4500;
    final int CRANE_HIGH = -6657;

    @Override
    public void init() {

        crane = hardwareMap.get(DcMotorEx.class, "CraneFront");
        lefts = hardwareMap.get(CRServo.class, "LeftsFront");

        frontLeft = hardwareMap.get(DcMotorSimple.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorSimple.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorSimple.class, "backLeft");
        backRight = hardwareMap.get(DcMotorSimple.class, "backRight");

        crane.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        crane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    @Override
    public void loop() {

        cranepower = gamepad2.right_stick_y;
        pickup = gamepad2.left_trigger;
        dropoff = gamepad2.right_trigger;

        throttle = gamepad1.left_stick_y;
        turn = gamepad1.right_stick_x;
        strafeLeft = gamepad1.left_trigger;
        strafeRight = gamepad1.right_trigger;

        frontLeft.setPower(strafeLeft);
        frontRight.setPower(-strafeLeft);
        backLeft.setPower(-strafeLeft);
        backRight.setPower(strafeLeft);

        frontLeft.setPower(-strafeRight);
        frontRight.setPower(strafeRight);
        backLeft.setPower(strafeRight);
        backRight.setPower(-strafeRight);

        frontLeft.setPower(throttle);
        frontRight.setPower(throttle);
        backLeft.setPower(throttle);
        backRight.setPower(throttle);

        frontLeft.setPower(-turn);
        frontRight.setPower(turn);
        backLeft.setPower(-turn);
        backRight.setPower(turn);


        if (gamepad2.triangle) {
            crane.setPower(1);
            crane.setTargetPosition(CRANE_HIGH);
            crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        } else if (gamepad2.circle) {
            crane.setPower(1);
            crane.setTargetPosition(CRANE_MID);
            crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        } else if (gamepad2.cross) {
            crane.setPower(1);
            crane.setTargetPosition(CRANE_LOW);
            crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        }else if (gamepad2.square) {
            crane.setTargetPosition(0);
            crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            crane.setPower(1);
        }

        if (pickup > 0){
            lefts.setPower(-1);
        }

        if (dropoff > 0){
            lefts.setPower(1);
        }

        if (dropoff == 0 && pickup == 0){
            lefts.setPower(0);
        }

    }
}