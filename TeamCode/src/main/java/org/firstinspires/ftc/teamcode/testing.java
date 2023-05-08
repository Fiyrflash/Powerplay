package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class testing extends LinearOpMode {
    public DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
    public DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
    public DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
    public DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");
    public CRServo lefts = hardwareMap.get(CRServo.class, "LeftsFront");
    public DcMotor crane = hardwareMap.get(DcMotor.class, "CraneFront");
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        while (opModeIsActive()){
            double throttle = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double strafeLeft = gamepad1.left_trigger;
            double strafeRight = gamepad1.right_trigger;
            double cranepower = -gamepad2.right_stick_y;
            float pickup = gamepad2.left_trigger;
            float dropoff = gamepad2.right_trigger;
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
            crane.setPower(cranepower);
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
}