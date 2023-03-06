package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class teleop extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private CRServo leftFront;
    private CRServo leftBack;
    private DcMotor craneFront;
    private DcMotor craneBack;

    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        leftFront = hardwareMap.get(CRServo.class, "LeftsFront");
        leftBack = hardwareMap.get(CRServo.class, "LeftsBack");
        craneFront = hardwareMap.get(DcMotor.class, "CraneFront");
        craneBack = hardwareMap.get(DcMotor.class, "CraneBack");

        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        craneFront.setDirection(DcMotorSimple.Direction.FORWARD);
        craneFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        craneFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        craneBack.setDirection(DcMotorSimple.Direction.FORWARD);
        craneBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        craneBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        while (opModeIsActive()) {

            double turn;
            double throttle;
            float strafeLeft;
            float strafeRight;

            float pickup;
            float dropoff;
            double cranepower;

            throttle = gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            strafeLeft = gamepad1.left_trigger;
            strafeRight = gamepad1.right_trigger;

            cranepower = -gamepad2.right_stick_y;
            pickup = gamepad2.left_trigger;
            dropoff = gamepad2.right_trigger;

            frontLeft.setPower(-strafeLeft);
            frontRight.setPower(strafeLeft);
            backLeft.setPower(strafeLeft);
            backRight.setPower(-strafeLeft);

            frontLeft.setPower(strafeRight);
            frontRight.setPower(-strafeRight);
            backLeft.setPower(-strafeRight);
            backRight.setPower(strafeRight);

            frontLeft.setPower(throttle);
            frontRight.setPower(throttle);
            backLeft.setPower(throttle);
            backRight.setPower(throttle);

            frontLeft.setPower(turn);
            frontRight.setPower(turn);
            backLeft.setPower(-turn);
            backRight.setPower(-turn);

            //craneFront.setPower(-cranepower);
            //craneBack.setPower(-cranepower);

            if (cranepower>0) {
                craneFront.setPower(cranepower);
                craneBack.setTargetPosition(craneFront.getCurrentPosition());
                craneBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (cranepower<0) {
                if (craneFront.getCurrentPosition() >= 0) {
                    craneFront.setPower(cranepower);
                    craneBack.setTargetPosition(craneFront.getCurrentPosition());
                    craneBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    craneBack.setPower(1);
                }
                if (craneFront.getCurrentPosition() < 0) {
                    craneFront.setPower(0);
                }
            }
            if (cranepower==0){
                craneFront.setPower(0);
            }

            if (pickup > 0){
                leftFront.setPower(-1);
                leftBack.setPower(-1);
            }

            if (dropoff > 0){
                leftFront.setPower(1);
                leftBack.setPower(1);
            }

            if (dropoff == 0 && pickup == 0){
                leftFront.setPower(0);
                leftBack.setPower(0);
            }
        }
    }
}