package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class teleop extends driveConstants {

    public void runOpMode(){
        waitForStart();
        while (opModeIsActive()) {
            double turn;
            double throttle;
            float strafeLeft;
            float strafeRight;

            float pickup;
            float dropoff;
            double cranepower;
            double cranepower2;

            throttle = gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            strafeLeft = gamepad1.left_trigger;
            strafeRight = gamepad1.right_trigger;

            cranepower = -gamepad2.right_stick_y;
            cranepower2 = -gamepad2.left_stick_y;
            pickup = gamepad2.left_trigger;
            dropoff = gamepad2.right_trigger;

            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            backLeft = hardwareMap.get(DcMotor.class, "backLeft");
            backRight = hardwareMap.get(DcMotor.class, "backRight");

            leftFront = hardwareMap.get(CRServo.class, "LeftsFront");
            leftBack = hardwareMap.get(CRServo.class, "LeftsBack");
            craneFront = hardwareMap.get(DcMotor.class, "CraneFront");
            craneBack = hardwareMap.get(DcMotor.class, "CraneBack");

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

            craneFront.setPower(cranepower);
            craneBack.setPower(cranepower2);

            if (pickup > 0){
                leftFront.setPower(-1);
                leftBack.setPower(1);
            }

            if (dropoff > 0){
                leftFront.setPower(1);
                leftBack.setPower(-1);
            }

            if (dropoff == 0 && pickup == 0){
                leftFront.setPower(0);
                leftBack.setPower(0);
            }
        }
    }
}