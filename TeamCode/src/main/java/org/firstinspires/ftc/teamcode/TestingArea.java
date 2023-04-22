package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class TestingArea extends OpMode {

    public DcMotor crane;
    public CRServo lefts;

    double cranepower;

    public enum CraneState{
        CRANE_START,
        CRANE_DUMP
    }

    CraneState CS = CraneState.CRANE_START;

    final int CRANE_LOW = -2500;
    final int CRANE_MID = -4500;
    final int CRANE_HIGH = -6657;

    @Override
    public void init() {
        cranepower = gamepad2.right_stick_y;

        crane = hardwareMap.get(DcMotorEx.class, "CraneFront");
        lefts = hardwareMap.get(CRServo.class, "LeftsFront");

        crane.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        crane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {
        switch (CS) {
            case CRANE_START:
                if (gamepad2.cross) {
                    crane.setPower(1);
                    crane.setTargetPosition(CRANE_HIGH);
                    crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                    if (Math.abs(CRANE_LOW) >= Math.abs(crane.getCurrentPosition()) - 10) {
                        CS = CraneState.CRANE_DUMP;
                    }

                } else if (gamepad2.circle) {
                    crane.setPower(1);
                    crane.setTargetPosition(CRANE_MID);
                    crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                    if (Math.abs(CRANE_MID) >= Math.abs(crane.getCurrentPosition()) - 10) {
                        CS = CraneState.CRANE_DUMP;
                    }

                } else if (gamepad2.triangle) {
                    crane.setPower(1);
                    crane.setTargetPosition(CRANE_LOW);
                    crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                    if (Math.abs(CRANE_LOW) >= Math.abs(crane.getCurrentPosition()) - 10) {
                        CS = CraneState.CRANE_DUMP;
                    }
                }


            case CRANE_DUMP:
                if (gamepad2.square) {
                    crane.setTargetPosition(0);
                    crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    crane.setPower(1);
                    if (Math.abs(crane.getCurrentPosition()) - 10 >= 0) {
                        CS = CraneState.CRANE_START;
                    }
                }

        }
    }
}