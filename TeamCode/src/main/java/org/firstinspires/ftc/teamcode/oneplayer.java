package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class oneplayer extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private CRServo Left;
    private DcMotor Crane;

    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        Left = hardwareMap.get(CRServo.class, "Lefts");
        Crane = hardwareMap.get(DcMotor.class, "Crane");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {

            double turn;
            double throttle;
            float strafeLeft;
            float strafeRight;

            boolean pickup;
            boolean dropoff;
            double cranepower;

            throttle = gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            strafeLeft = gamepad1.left_trigger;
            strafeRight = gamepad1.right_trigger;

            cranepower = gamepad1.right_stick_y;
            pickup = gamepad1.left_bumper;
            dropoff = gamepad1.right_bumper;

            Crane.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            frontLeft.setPower(-strafeLeft);
            frontRight.setPower(-strafeLeft);
            backLeft.setPower(strafeLeft);
            backRight.setPower(strafeLeft);

            frontLeft.setPower(strafeRight);
            frontRight.setPower(strafeRight);
            backLeft.setPower(-strafeRight);
            backRight.setPower(-strafeRight);

            frontLeft.setPower(throttle);
            frontRight.setPower(throttle);
            backLeft.setPower(throttle);
            backRight.setPower(throttle);

            if (cranepower<turn){

                Crane.setPower(0);
                frontLeft.setPower(-turn);
                frontRight.setPower(turn);
                backLeft.setPower(-turn);
                backRight.setPower(turn);

            }else {

                Crane.setPower(cranepower);
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);

                if (cranepower==turn){

                    Crane.setPower(cranepower);
                    frontLeft.setPower(0);
                    frontRight.setPower(0);
                    backLeft.setPower(0);
                    backRight.setPower(0);

                }
            }

            if (pickup) {
                Left.setPower(-1);

            }
            if (dropoff) {
                Left.setPower(1);

            }
        }
    }
}
