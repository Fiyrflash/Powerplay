package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.apriltag.AprilTagDetection;

public class driveConstants extends LinearOpMode {

    AprilTagDetection tagOfInterest = null;
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public CRServo leftFront;
    public CRServo leftBack;
    public DcMotor craneFront;
    public DcMotor craneBack;
    BNO055IMU imu;
    Orientation angles;

    public void runOpMode() {

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
        craneBack.setDirection(DcMotorSimple.Direction.FORWARD);
        craneBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
            if (angles.firstAngle >= targetAngle - 0.15 && angles.firstAngle <= targetAngle + 0.15) {
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
                foundAngle = true;
                sleep(500);
                break;

            } else if (angles.firstAngle >= targetAngle + 0.5) {
                if (angles.firstAngle <= targetAngle - 5) {
                    frontLeft.setPower(0.1);
                    frontRight.setPower(-0.1);
                    backLeft.setPower(0.1);
                    backRight.setPower(-0.1);
                    foundAngle = false;
                } else {
                    frontLeft.setPower(-0.1);
                    frontRight.setPower(0.1);
                    backLeft.setPower(-0.1);
                    backRight.setPower(0.1);
                    foundAngle = false;
                }
            } else if (angles.firstAngle <= targetAngle - 0.5) {
                if (angles.firstAngle >= targetAngle + 5) {
                    frontLeft.setPower(-0.1);
                    frontRight.setPower(0.1);
                    backLeft.setPower(-0.1);
                    backRight.setPower(0.1);
                    foundAngle = false;
                } else {
                    frontLeft.setPower(.1);
                    frontRight.setPower(-.1);
                    backLeft.setPower(.1);
                    backRight.setPower(-.1);
                    foundAngle = false;
                }
            }
        }
        return foundAngle;
    }

    public void stopMotors() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void move(double power, int position) {
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


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

        while (frontLeft.isBusy() && opModeIsActive()) {

        }

    }

    public void moveandcrane(double power, int position, int time, double power2, int position2) {

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

        craneFront.setTargetPosition(position2);
        craneBack.setTargetPosition(position2);
        craneFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        craneBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        craneFront.setPower(power2);
        craneBack.setPower(power2);
        while (backRight.isBusy() || craneFront.isBusy() || craneBack.isBusy() && opModeIsActive()) {

        }
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
        craneFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

        craneFront.setTargetPosition(position2);
        craneBack.setTargetPosition(position2);
        craneFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        craneBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        craneFront.setPower(power2);
        craneBack.setPower(power2);
        while (backRight.isBusy() || craneFront.isBusy() || craneBack.isBusy() && opModeIsActive()) {

        }
    }

    public void strafeRightandCrane(double power, int position, int time, double power2, int position2) {
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

        craneFront.setTargetPosition(position2);
        craneBack.setTargetPosition(position2);
        craneFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        craneBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        craneFront.setPower(power2);
        craneBack.setPower(power2);
        while (backRight.isBusy() || craneFront.isBusy() || craneBack.isBusy() && opModeIsActive()) {

        }
    }

    public void crane(double power, int position) {
        craneFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneFront.setTargetPosition(position);
        craneBack.setTargetPosition(position);
        craneFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        craneBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        craneFront.setPower(power);
        craneBack.setPower(power);
        while (craneFront.isBusy() || craneBack.isBusy() && opModeIsActive()) {

        }
    }

    public void intake(double power) {
        leftFront.setPower(power);
        leftBack.setPower(-power);
        sleep(2000);
        leftFront.setPower(0);
        leftBack.setPower(0);
        while (backLeft.isBusy() || leftFront.getPower() == 1 && opModeIsActive()) {

        }
    }

    public void cranethenIntake(double power, int position, int time, double power2) {
        craneFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneFront.setTargetPosition(position);
        craneBack.setTargetPosition(position);
        craneFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        craneBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        craneFront.setPower(power);
        craneBack.setPower(power);

        sleep(time);

        leftFront.setPower(power2);
        leftBack.setPower(power2);
        sleep(2000);
        leftFront.setPower(0);
        leftBack.setPower(0);
        while (craneFront.isBusy() && opModeIsActive()) {

        }
    }

    public void movethenCranethenIntake(double power, int position, int time, double power2, int position2, int time2, double power3) {
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

        craneFront.setTargetPosition(position2);
        craneBack.setTargetPosition(position2);
        craneFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        craneBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        craneFront.setPower(power2);
        craneBack.setPower(power2);

        sleep(time2);

        leftFront.setPower(power3);
        leftBack.setPower(power3);
        sleep(2000);
        leftFront.setPower(0);
        leftBack.setPower(0);
        while (backRight.isBusy() || craneFront.isBusy() || craneBack.isBusy() && opModeIsActive()) {
        }
    }
}
