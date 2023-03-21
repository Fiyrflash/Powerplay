package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class teleop extends LinearOpMode {
    public void runOpMode(){
        driveConstants dC = new driveConstants();

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


            dC.frontLeft.setPower(strafeLeft);
            dC.frontRight.setPower(-strafeLeft);
            dC.backLeft.setPower(-strafeLeft);
            dC.backRight.setPower(strafeLeft);

            dC.frontLeft.setPower(-strafeRight);
            dC.frontRight.setPower(strafeRight);
            dC.backLeft.setPower(strafeRight);
            dC.backRight.setPower(-strafeRight);

            dC.frontLeft.setPower(throttle);
            dC.frontRight.setPower(throttle);
            dC.backLeft.setPower(throttle);
            dC.backRight.setPower(throttle);

            dC.frontLeft.setPower(-turn);
            dC.frontRight.setPower(turn);
            dC.backLeft.setPower(-turn);
            dC.backRight.setPower(turn);

            dC.craneFront.setPower(cranepower);
            dC.craneBack.setPower(cranepower2);

            if (pickup > 0){
                dC.leftFront.setPower(1);
            }

            if (dropoff > 0){
                dC.leftFront.setPower(-1);
            }

            if (dropoff == 0 && pickup == 0){
                dC.leftFront.setPower(0);
            }

            if (pickup2){
                dC.leftBack.setPower(-1);
            }

            if (dropoff2){
                dC.leftBack.setPower(1);
            }

            if (dropoff2 && pickup2){
                dC.leftBack.setPower(0);
            }

        }
    }
}