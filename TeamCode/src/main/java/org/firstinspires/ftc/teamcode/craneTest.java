package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Disabled
public class craneTest extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    BNO055IMU imu;
    Orientation angles;

    private CRServo intake;
    private DcMotor Crane;

    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        intake = hardwareMap.get(CRServo.class, "Lefts");
        Crane = hardwareMap.get(DcMotor.class, "Crane");

        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        Crane.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        initGyro();
        if (opModeIsActive()) {

            strafeLeftandCrane(1,980, 0,.7,3700);
            slowgyroTurning(0);
            move(1,290);
            slowgyroTurning(0);
            cranethenIntake(.7,-550,1000,1);
            move(1,-250);
            slowgyroTurning(0);
            strafeLeft(1,2040);
            gyroTurning(90);
            initGyro();
            gyroTurning(89);
            moveandcrane(1,1275, 0,1,-200);
            crane(.7,-1100);





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

        while (backRight.isBusy() && opModeIsActive()) {

        }
    }

    public void moveandcrane(double power, int position, int time, double power2, int position2) {

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Crane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

        sleep(time);

        telemetry.addData("Crane", Crane.getCurrentPosition());
        telemetry.update();
        Crane.setTargetPosition(position2);
        Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Crane.setPower(power2);
        while (backRight.isBusy() && opModeIsActive()) {

        }
    }

    public void initGyro () {
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
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        boolean foundAngle;
        foundAngle = false;
        while (!foundAngle) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double currentAngle = angles.firstAngle;
            telemetry.addData("Angle", currentAngle);
            telemetry.addData("targetangle", targetAngle);
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
                if (angles.firstAngle <= targetAngle - 5) {
                    frontLeft.setPower(0.25);
                    frontRight.setPower(-0.25);
                    backLeft.setPower(0.25);
                    backRight.setPower(-0.25);
                    foundAngle = false;
                } else {
                    frontLeft.setPower(-0.25);
                    frontRight.setPower(0.25);
                    backLeft.setPower(-0.25);
                    backRight.setPower(0.25);
                    foundAngle = false;
                }
            } else if (angles.firstAngle <= targetAngle - 0.5) {
                if (angles.firstAngle >= targetAngle + 5) {
                    frontLeft.setPower(-0.25);
                    frontRight.setPower(0.25);
                    backLeft.setPower(-0.25);
                    backRight.setPower(0.25);
                    foundAngle = false;
                } else {
                    frontLeft.setPower(.25);
                    frontRight.setPower(-.25);
                    backLeft.setPower(.25);
                    backRight.setPower(-.25);
                    foundAngle = false;
                }
            }
        }
        return foundAngle;
    }

    public boolean slowgyroTurning(double targetAngle) {
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        boolean foundAngle;
        foundAngle = false;
        while (!foundAngle) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double currentAngle = angles.firstAngle;
            telemetry.addData("Angle", currentAngle);
            telemetry.addData("targetangle", targetAngle);
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
                if (angles.firstAngle <= targetAngle - 5) {
                    frontLeft.setPower(0.10);
                    frontRight.setPower(-0.10);
                    backLeft.setPower(0.10);
                    backRight.setPower(-0.10);
                    foundAngle = false;
                } else {
                    frontLeft.setPower(-0.10);
                    frontRight.setPower(0.10);
                    backLeft.setPower(-0.10);
                    backRight.setPower(0.10);
                    foundAngle = false;
                }
            } else if (angles.firstAngle <= targetAngle - 0.5) {
                if (angles.firstAngle >= targetAngle + 5) {
                    frontLeft.setPower(-0.10);
                    frontRight.setPower(0.10);
                    backLeft.setPower(-0.10);
                    backRight.setPower(0.10);
                    foundAngle = false;
                } else {
                    frontLeft.setPower(.10);
                    frontRight.setPower(-.10);
                    backLeft.setPower(.10);
                    backRight.setPower(-.10);
                    foundAngle = false;
                }
            }
        }
        return foundAngle;
    }

    public boolean fastgyroTurning(double targetAngle) {
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        boolean foundAngle;
        foundAngle = false;
        while (!foundAngle) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double currentAngle = angles.firstAngle;
            telemetry.addData("Angle", currentAngle);
            telemetry.addData("targetangle", targetAngle);
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
                if (angles.firstAngle <= targetAngle - 5) {
                    frontLeft.setPower(0.50);
                    frontRight.setPower(-0.50);
                    backLeft.setPower(0.50);
                    backRight.setPower(-0.50);
                    foundAngle = false;
                } else {
                    frontLeft.setPower(-0.50);
                    frontRight.setPower(0.50);
                    backLeft.setPower(-0.50);
                    backRight.setPower(0.50);
                    foundAngle = false;
                }
            } else if (angles.firstAngle <= targetAngle - 0.5) {
                if (angles.firstAngle >= targetAngle + 5) {
                    frontLeft.setPower(-0.50);
                    frontRight.setPower(0.50);
                    backLeft.setPower(-0.50);
                    backRight.setPower(0.50);
                    foundAngle = false;
                } else {
                    frontLeft.setPower(.50);
                    frontRight.setPower(-.50);
                    backLeft.setPower(.50);
                    backRight.setPower(-.50);
                    foundAngle = false;
                }
            }
        }
        return foundAngle;
    }

    public void strafeLeft(double power, int position) {
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setTargetPosition(-position);
        frontLeft.setTargetPosition(position);
        backRight.setTargetPosition(position);
        backLeft.setTargetPosition(-position);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);
        while (backRight.isBusy() && opModeIsActive()) {

        }
    }

    public void strafeRight(double power, int position) {
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setTargetPosition(position);
        frontLeft.setTargetPosition(-position);
        backRight.setTargetPosition(-position);
        backLeft.setTargetPosition(position);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);

        while (backRight.isBusy() && opModeIsActive()) {

        }
    }

    public void strafeLeftandCrane(double power, int position, int time, double power2, int position2) {
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Crane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setTargetPosition(-position);
        frontLeft.setTargetPosition(position);
        backRight.setTargetPosition(position);
        backLeft.setTargetPosition(-position);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);

        sleep(time);

        telemetry.addData("Crane", Crane.getCurrentPosition());
        telemetry.update();
        Crane.setTargetPosition(position2);
        Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Crane.setPower(power2);
        while (backRight.isBusy() && opModeIsActive()) {

        }
    }

    public void strafeRightandCrane(double power, int position, int time, double power2, int position2) {
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Crane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setTargetPosition(position);
        frontLeft.setTargetPosition(-position);
        backRight.setTargetPosition(-position);
        backLeft.setTargetPosition(position);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);

        sleep(time);

        telemetry.addData("Crane", Crane.getCurrentPosition());
        telemetry.update();
        Crane.setTargetPosition(position2);
        Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Crane.setPower(power2);
        while (backRight.isBusy() && opModeIsActive()) {

        }
    }

    public void crane(double power, int position) {
        telemetry.addData("Crane", Crane.getCurrentPosition());
        telemetry.update();
        Crane.setTargetPosition(position);
        Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Crane.setPower(power);
        while (backRight.isBusy() && opModeIsActive()) {

        }
    }

    public void intake(double power){
        intake.setPower(power);
        sleep(2000);
        intake.setPower(0);
        while (backRight.isBusy() && opModeIsActive()) {

        }
    }
    public void cranethenIntake(double power, int position, int time, double power2){
        Crane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("Crane", Crane.getCurrentPosition());
        telemetry.update();
        Crane.setTargetPosition(position);
        Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Crane.setPower(power);

        sleep(time);

        intake.setPower(power2);
        sleep(2000);
        intake.setPower(0);
        while (backRight.isBusy() && opModeIsActive()) {

        }
    }

    public void movethenCranethenIntake(double power, int position, int time, double power2, int position2, int time2, double power3){
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Crane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

        sleep(time);

        telemetry.addData("Crane", Crane.getCurrentPosition());
        telemetry.update();
        Crane.setTargetPosition(position2);
        Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Crane.setPower(power2);

        sleep(time2);

        intake.setPower(power3);
        sleep(2000);
        intake.setPower(0);
        while (backRight.isBusy() && opModeIsActive()) {
        }
    }
}
