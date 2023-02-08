package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class TestingArea extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private CRServo Left;
    private DcMotor crane;

    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        Left = hardwareMap.get(CRServo.class, "Lefts");
        crane = hardwareMap.get(DcMotor.class, "Crane");

        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();
        while (opModeIsActive()) {

            double turn;
            double throttle;
            float strafe;

            float pickup;
            float dropoff;
            double cranepower;

            throttle = gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            strafe = gamepad1.left_stick_x;

            cranepower = gamepad2.right_stick_y;
            pickup = gamepad2.left_trigger;
            dropoff = gamepad2.right_trigger;

            crane.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            if (strafe>throttle) {
                frontLeft.setPower(strafe);
                frontRight.setPower(-strafe);
                backLeft.setPower(-strafe);
                backRight.setPower(strafe);
            }
            if (strafe<throttle) {
                frontLeft.setPower(throttle);
                frontRight.setPower(throttle);
                backLeft.setPower(throttle);
                backRight.setPower(throttle);
            }

            frontLeft.setPower(-turn);
            frontRight.setPower(turn);
            backLeft.setPower(-turn);
            backRight.setPower(turn);

            crane.setPower(cranepower);

            if (pickup > 0){
                Left.setPower(-1);
            }
            if (dropoff > 0){
                Left.setPower(1);
            }
            if (dropoff == 0 && pickup == 0){
                Left.setPower(0);
            }
        }
    }
}