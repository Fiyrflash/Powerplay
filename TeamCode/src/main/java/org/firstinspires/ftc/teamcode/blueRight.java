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


    OpenCvWebcam webcam;
    String position;


    public void runOpMode() throws InterruptedException {
        Functions robot = new Functions();


        waitForStart();//posiblyy the problem

        if (opModeIsActive()) {
            robot.move(1, 2200);
            robot.gyroTurning(90);
            robot.move(.2, 700);

            //first cone
            robot.move(.5, 470);
            robot.crane(300);
            robot.intake(-1, 2000);
            robot.moveandspin(1, -720, 800);
            robot.crane(-300);
            robot.intake(1, 2000);
            robot.moveandspin(1, 470, -800);

            //second cone
            robot.move(.5, 470);
            robot.crane(300);
            robot.intake(-1, 2000);
            robot.moveandspin(1, -720, 800);
            robot.crane(-300);
            robot.intake(1, 2000);
            robot.moveandspin(1, 470, -800);

            //third cone
            robot.move(.5, 470);
            robot.crane(300);
            robot.intake(-1, 2000);
            robot.moveandspin(1, -720, 800);
            robot.crane(-300);
            robot.intake(1, 2000);
            robot.moveandspin(1, 470, -800);

            //forth cone
            robot.move(.5, 470);
            robot.crane(300);
            robot.intake(-1, 2000);
            robot.moveandspin(1, -720, 800);
            robot.crane(-300);
            robot.intake(1, 2000);
            robot.moveandspin(1, 470, -800);

            //fith cone
            robot.move(.5, 470);
            robot.crane(300);
            robot.intake(-1, 2000);
            robot.moveandspin(1, -720, 800);
            robot.crane(-300);
            robot.intake(1, 2000);
            robot.moveandspin(1, 470, -800);

            robot.move(.5, 470);
            robot.strafeRight(1, 1000);


        }
    }
}

    /*public boolean gyroTurning(double targetAngle) {
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


*/
