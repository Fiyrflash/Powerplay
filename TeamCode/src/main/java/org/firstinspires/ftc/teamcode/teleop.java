package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.VoltageSensor;

@TeleOp
public class teleop extends LinearOpMode {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public CRServo leftFront;
    public CRServo leftBack;
    public DcMotor craneFront;
    public DcMotor craneBack;

    public void runOpMode(){

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        leftFront = hardwareMap.get(CRServo.class, "LeftsFront");
        leftBack = hardwareMap.get(CRServo.class, "LeftsBack");
        craneFront = hardwareMap.get(DcMotor.class, "CraneFront");
        craneBack = hardwareMap.get(DcMotor.class, "CraneBack");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        craneFront.setDirection(DcMotorSimple.Direction.FORWARD);
        craneFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        craneBack.setDirection(DcMotorSimple.Direction.FORWARD);
        craneBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
            telemetry.addData("frontRight", frontRight.getCurrentPosition());
            telemetry.addData("backLeft", backLeft.getCurrentPosition());
            telemetry.addData("backRight", backRight.getCurrentPosition());
            telemetry.update();
            double turn;
            double throttle;
            float strafeLeft;
            float strafeRight;

            float pickup;
            float dropoff;
            //double cranepower;
            double cranepower2;

            throttle = gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            strafeLeft = gamepad1.left_trigger;
            strafeRight = gamepad1.right_trigger;

            //cranepower = -gamepad2.right_stick_y;
            cranepower2 = -gamepad2.left_stick_y;
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

            //craneFront.setPower(cranepower);
            craneBack.setPower(cranepower2);

            if (pickup > 0){
                leftFront.setPower(1);
                leftBack.setPower(-1);
            }

            if (dropoff > 0){
                leftFront.setPower(-1);
                leftBack.setPower(1);
            }

            if (dropoff == 0 && pickup == 0){
                leftFront.setPower(0);
                leftBack.setPower(0);
            }
        }
    }
}