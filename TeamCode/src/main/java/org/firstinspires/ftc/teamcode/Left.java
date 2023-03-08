package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
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

@Autonomous

public class Left extends LinearOpMode {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    BNO055IMU imu;
    Orientation angles;

    public CRServo leftFront;
    public CRServo leftBack;
    public DcMotor craneFront;
    public DcMotor craneBack;

    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    // Tag ID 1,2,3 from the 36h11 family
    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;

    AprilTagDetection tagOfInterest = null;

    @Override
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

        craneFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        craneBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        opModeInInit();
//camera starts
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "ConeCam"), cameraMonitorViewId);
            aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

            camera.setPipeline(aprilTagDetectionPipeline);
            camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                @Override
                public void onOpened() {
                    camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
                }

                @Override
                public void onError(int errorCode) {

                }
            });

            telemetry.setMsTransmissionInterval(50);

            /*
             * The INIT-loop:
             * This REPLACES waitForStart!
             */
            while (!isStarted() && !isStopRequested()) {
                ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

                if (currentDetections.size() != 0) {
                    boolean tagFound = false;

                    for (AprilTagDetection tag : currentDetections) {
                        if (tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT) {
                            tagOfInterest = tag;
                            tagFound = true;
                            break;
                        }
                    }

                    if (tagFound) {
                        telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                        tagToTelemetry(tagOfInterest);
                    } else {
                        telemetry.addLine("Don't see tag of interest :(");

                        if (tagOfInterest == null) {
                            telemetry.addLine("(The tag has never been seen)");
                            break; //could break something
                        } else {
                            telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                            tagToTelemetry(tagOfInterest);
                        }
                    }

                } else {
                    telemetry.addLine("Don't see tag of interest :(");

                    if (tagOfInterest == null) {
                        telemetry.addLine("(The tag has never been seen)");
                        break; //This might break something
                    } else {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }

                }

                telemetry.update();
                sleep(20);
            }

            /*
             * The START command just came in: now work off the latest snapshot acquired
             * during the init loop.
             */

            /* Update the telemetry */
            if (tagOfInterest != null) {
                telemetry.addLine("Tag snapshot:\n");
                tagToTelemetry(tagOfInterest);
                telemetry.update();
            } else {
                telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
                telemetry.update();
            }
        }

        void tagToTelemetry(AprilTagDetection detection){
            telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
            telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x * FEET_PER_METER));
            telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y * FEET_PER_METER));
            telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z * FEET_PER_METER));
            telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
            telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
            telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));

        waitForStart();
        initGyro();
        if (opModeIsActive()) {
            telemetry.clearAll();
            telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
            telemetry.update();





            /*if (tagOfInterest.id == LEFT){
                slowgyroTurning(0);
                moveandcrane(.5,-1300,0,1,-2700);
                slowgyroTurning(0);
                stopMotors();

            }
            else if (tagOfInterest.id == MIDDLE){
                stopMotors();

            }
            else if (tagOfInterest.id == RIGHT){
                slowgyroTurning(0);
                moveandcrane(-5,1200,0,1,-2700);
                slowgyroTurning(0);
                stopMotors();

            }
            else {
                stopMotors();

            }

             */
            terminateOpModeNow();

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
    public void stopMotors(){
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
        craneFront.setPower(0);
        craneBack.setPower(0);
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

    public void strafeLeft(double power, int position) {
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

    public void strafeRight(double power, int position) {
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

    public void strafeLeftandCrane(double power, int position, int time, double power2, int position2) {
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
        while (backRight.isBusy() || craneFront.isBusy() || craneFront.isBusy() && opModeIsActive()) {

        }
    }

    public void strafeRightandCrane(double power, int position, int time, double power2, int position2) {
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
        leftBack.setPower(power);
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