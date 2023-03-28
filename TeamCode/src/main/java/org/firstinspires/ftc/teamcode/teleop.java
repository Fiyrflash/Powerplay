package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class teleop extends LinearOpMode {
    public void runOpMode(){

        driveConstants dc = new driveConstants();

        waitForStart();
        while (opModeIsActive()) {
            double turn;
            double throttle;
            float strafeLeft;
            float strafeRight;

            float pickup;
            float dropoff;
            boolean dropoff2;
            boolean pickup2;
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
            pickup2 = gamepad2.left_bumper;
            dropoff2 = gamepad2.right_bumper;


            dc.frontLeft.setPower(strafeLeft);
            dc.frontRight.setPower(-strafeLeft);
            dc.backLeft.setPower(-strafeLeft);
            dc.backRight.setPower(strafeLeft);

            dc.frontLeft.setPower(-strafeRight);
            dc.frontRight.setPower(strafeRight);
            dc.backLeft.setPower(strafeRight);
            dc.backRight.setPower(-strafeRight);

            dc.frontLeft.setPower(throttle);
            dc.frontRight.setPower(throttle);
            dc.backLeft.setPower(throttle);
            dc.backRight.setPower(throttle);

            dc.frontLeft.setPower(-turn);
            dc.frontRight.setPower(turn);
            dc.backLeft.setPower(-turn);
            dc.backRight.setPower(turn);

            dc.craneFront.setPower(cranepower);
            dc.craneBack.setPower(cranepower2);

            if (pickup > 0){
                dc.leftFront.setPower(1);
            }

            if (dropoff > 0){
                dc.leftFront.setPower(-1);
            }

            if (dropoff == 0 && pickup == 0){
                dc.leftFront.setPower(0);
            }

            if (pickup2){
                dc.leftBack.setPower(-1);
            }

            if (dropoff2){
                dc.leftBack.setPower(1);
            }

            if (dropoff2 && pickup2){
                dc.leftBack.setPower(0);
            }

        }
    }
}