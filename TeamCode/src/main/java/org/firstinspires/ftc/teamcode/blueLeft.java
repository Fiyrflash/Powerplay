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
public class blueLeft extends LinearOpMode {


    OpenCvWebcam webcam;
    String position;


    public void runOpMode() throws InterruptedException {
       Functions robot =new Functions();

        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId",
                        "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        side1 a = new side1(telemetry);
        side2 b = new side2(telemetry);
        side3 c = new side3(telemetry);

        webcam.setPipeline(a);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        waitForStart();

        if (opModeIsActive()) {
            if (a.getLocation()== side1.Location.SIDE1) {
                    position = "side1";
                    telemetry.addLine("side1");
                    telemetry.update();
                    sleep(3000);
            }
            webcam.setPipeline(c);

            if (c.getLocation()== side3.Location.SIDE3) {
                    position = "side3";
                    telemetry.addLine("side3");
                    telemetry.update();
                    sleep(3000);

            }
            webcam.setPipeline(b);
            if (b.getLocation()== side2.Location.SIDE2) {

                    position = "side2";
                    telemetry.addLine("side2");
                    telemetry.update();
                    sleep(3000);
            }

            if (position == "side1") {
                robot.crane(-200);
                robot.move(1, 2160);
                robot.gyroTurning(90);

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
            if (position == "side2") {
                robot.crane(-200);
                robot.move(1, 2160);
                robot.gyroTurning(90);

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

                robot.move(.5, 200);
                robot.strafeRight(1, 1000);
            }
            if (position == "side3") {
                robot.crane(-200);
                robot.move(1, 2160);
                robot.gyroTurning(90);

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


                robot.strafeRight(1, 1000);
                robot.move(.5, 200);
           }

        }


        webcam.stopStreaming();
    }
}




