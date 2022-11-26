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

public class oldtest extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    OpenCvWebcam webcam;

    BNO055IMU imu;
    Orientation angles;

    public void runOpMode() throws InterruptedException {
        initGyro();

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();//possubky the problem
        if (opModeIsActive()) {



            gyroTurning(90);

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
                } else {
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
}

