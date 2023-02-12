package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public class CreatingABetterGyro {

    @Autonomous
    public class redLeft extends LinearOpMode {
        private DcMotor frontLeft;
        private DcMotor frontRight;
        private DcMotor backLeft;
        private DcMotor backRight;

        BNO055IMU imu;
        Orientation angles;

        private CRServo intake;
        private DcMotor Crane;

        @Override
        public void runOpMode() {

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

            }
            terminateOpModeNow();
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
            while (backRight.isBusy() && opModeIsActive()) {

            }
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
                if (angles.firstAngle >= targetAngle - 0.01 && angles.firstAngle <= targetAngle + 0.01) {
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
    }
}