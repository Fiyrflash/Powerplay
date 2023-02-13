package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

        crane.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        crane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        while (opModeIsActive()) {

            double turn;
            double throttle;
            float strafeLeft;
            float strafeRight;

            float pickup;
            float dropoff;
            boolean cranepowerup;
            boolean cranepowerdown;

            throttle = gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            strafeLeft = gamepad1.left_trigger;
            strafeRight = gamepad1.right_trigger;

            cranepowerup = gamepad1.a;
            cranepowerdown = gamepad1.b;
            pickup = gamepad2.left_trigger;
            dropoff = gamepad2.right_trigger;

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

            if (cranepowerup){
                crane.setPower(1);
            }

            if (cranepowerdown){
                if (crane.getCurrentPosition()==0)
                    crane.setPower(-1);
            }

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