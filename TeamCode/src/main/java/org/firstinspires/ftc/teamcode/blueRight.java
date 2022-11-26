package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
@Autonomous
public class blueRight extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    OpenCvWebcam webcam;

    private CRServo Right;
    private CRServo Left;
    private DcMotor Crane;
    private DcMotor Spin;

    BNO055IMU imu;
    Orientation angles;
    String position;


    public void runOpMode() throws InterruptedException {
        initGyro();

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        Right = hardwareMap.get(CRServo.class, "Rights");
        Left = hardwareMap.get(CRServo.class, "Lefts");
        Crane = hardwareMap.get(DcMotor.class, "Crane");
        Spin = hardwareMap.get(DcMotor.class, "Spin");

        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        waitForStart();//posiblyy the problem

        if (opModeIsActive()) {


                move(1, 2200);
                gyroTurning(90);
                move(.2,700);

                crane(300);
                move(.5, 470);
                crane(-250);
                intake(-1, 2100);
                moveandspin(1, -720, 800);
                crane(-300);
                /*
                intake(1, 2000);
                moveandspin(1, 470, -800);

                //second cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                //third cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                //forth cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                //fith cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                move(.5, 470);
                strafeRight(1, 1000);

            /*if (position == "side2") {
                crane(-200);
                move(1, 2160);
                gyroTurning(90);

                //first cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                //second cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                //third cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                //forth cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                //fith cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                move(.5, 200);
                strafeRight(1, 1000);
            }
            if (position == "side3") {
                crane(-200);
                move(1, 2160);
                gyroTurning(90);

                //first cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                //second cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                //third cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                //forth cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);

                //fith cone
                move(.5, 470);
                crane(300);
                intake(-1, 2000);
                moveandspin(1, -720, 800);
                crane(-300);
                intake(1, 2000);
                moveandspin(1, 470, -800);


                strafeRight(1, 1000);
                move(.5, 200);
           }*/


        }
    }

    public void initGyro() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        sleep(250);
    }

    public boolean gyroTurning(double targetAngle) {
        boolean foundAngle;
        foundAngle = false;
        while (!foundAngle) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double currentAngle = angles.firstAngle;
            telemetry.addData("Angle",currentAngle);
            telemetry.addData("targetangle",targetAngle);
            telemetry.update();
            if (angles.firstAngle >= targetAngle - 0.1 && angles.firstAngle <= targetAngle + 0.1) {
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
                foundAngle = true;
                sleep(1000);
                break;

            } else if (angles.firstAngle >= targetAngle + 0.5) {
                if (angles.firstAngle <= targetAngle + 10) {
                    frontLeft.setPower(0.5);
                    frontRight.setPower(-0.5);
                    backLeft.setPower(0.5);
                    backRight.setPower(-0.5);
                    foundAngle = false;
                } else {
                    frontLeft.setPower(0.5);
                    frontRight.setPower(-0.5);
                    backLeft.setPower(0.5);
                    backRight.setPower(-0.5);
                    foundAngle = false;
                }
            } else if (angles.firstAngle <= targetAngle - 0.5) {
                if (angles.firstAngle >= targetAngle - 10) {
                    frontLeft.setPower(-0.5);
                    frontRight.setPower(0.5);
                    backLeft.setPower(-0.5);
                    backRight.setPower(0.5);
                    foundAngle = false;
                }
                else {
                    telemetry.addLine("hellow");
                    telemetry.update();
                    frontLeft.setPower(-.5);
                    frontRight.setPower(.5);
                    backLeft.setPower(-.5);
                    backRight.setPower(.5);
                    foundAngle = false;
                }
            }
        }
        return foundAngle;
    }


    public void crane(int position) {
        Crane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Crane.setTargetPosition(position);
        Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Crane.setPower(1);
        while (Crane.isBusy()) {

        }

    }

    public void intake(int direction, long Time) {
        Right.setPower(direction * 1);
        Left.setPower(direction * 1);
        sleep(Time);
        Right.setPower(0);
        Left.setPower(0);

    }

    public void moveandspin(double power, int moveposition, int spinposition) {
        move(power, moveposition);
        spin(spinposition);
        while (Spin.isBusy()) {

        }
    }

    public void Turn(double power,long time) {
        frontLeft.setPower(-power);
        backLeft.setPower(-power);
        frontRight.setPower(power);
        backRight.setPower(power);
        sleep(time);
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);

    }

    public void strafeRight(double power, int time) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(-power);
        backRight.setPower(power);
        sleep(time);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void spin(int position) {
        Spin.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Crane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Spin.setTargetPosition(position);
        Crane.setTargetPosition(-500);
        Spin.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Spin.setPower(1);
        Crane.setPower(1);
        while (Spin.isBusy()) {

        }
    }

    public void move(double power, int position) {
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setTargetPosition(-position);
        frontLeft.setTargetPosition(-position);
        backRight.setTargetPosition(-position);
        backLeft.setTargetPosition(-position);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);

        while (frontLeft.isBusy()) {

        }



    }
}
