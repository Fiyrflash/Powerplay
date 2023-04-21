package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class TestingArea extends OpMode {

    public DcMotor crane;
    public Servo lefts;

    double cranepower = gamepad2.right_stick_y;

    public enum CraneState{
        CRANE_START,
        CRANE_MOVING,
        CRANE_DUMP
    }

    CraneState CS = CraneState.CRANE_START;

    final int CRANE_LOW = -2500;
    final int CRANE_MID = -4500;
    final int CRANE_HIGH = -6657;

    @Override
    public void init() {
        crane = hardwareMap.get(DcMotorEx.class, "CraneFront");
        lefts = hardwareMap.get(Servo.class, "LeftsFront");

        crane.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        crane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {
        switch (CS) {
            case CRANE_START:
                if (gamepad2.x) {
                    crane.setPower(1);
                    crane.setTargetPosition(CRANE_HIGH);
                    crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                    CS = CraneState.CRANE_MOVING;

                } else if (gamepad2.y) {
                    crane.setPower(1);
                    crane.setTargetPosition(CRANE_MID);
                    CS = CraneState.CRANE_MOVING;
                    crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

                } else if (gamepad2.a) {
                    crane.setPower(1);
                    crane.setTargetPosition(CRANE_LOW);
                    CS = CraneState.CRANE_MOVING;
                    crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                } else {
                    crane.setPower(cranepower);
                    crane.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
                break;
            case CRANE_MOVING:
                if (cranepower > 0.15) {
                    CS = CraneState.CRANE_START;
                }

                if (Math.abs(CRANE_LOW) > Math.abs(crane.getCurrentPosition()) - 10) {
                    CS = CraneState.CRANE_DUMP;
                } else if (Math.abs(CRANE_MID) > Math.abs(crane.getCurrentPosition()) - 10) {
                    CS = CraneState.CRANE_DUMP;
                } else if (Math.abs(CRANE_HIGH) > Math.abs(crane.getCurrentPosition()) - 10) {
                    CS = CraneState.CRANE_DUMP;
                }
            case CRANE_DUMP:
                if (gamepad2.b) {
                    crane.setTargetPosition(0);
                    crane.setPower(1);
                    crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                if (gamepad1.a) {
                    lefts.setPosition(200);
                    CS = CraneState.CRANE_START;
                    crane.setTargetPosition(0);
                    crane.setPower(1);
                    crane.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
        }
    }
}