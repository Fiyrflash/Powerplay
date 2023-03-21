package org.firstinspires.ftc.teamcode;                                     //imports

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.apriltag.AprilTagDetection;
import java.util.ArrayList;

@Autonomous
public class Left extends LinearOpMode {


    @Override
    public void runOpMode() {
        driveConstants dc = new driveConstants();
        dc.initGyro();
        int cameraMonitorViewId = hardwareMap.appContext                            //setting up the camera
                .getResources().getIdentifier("cameraMonitorViewId",
                        "id", hardwareMap.appContext.getPackageName());
        dc.webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "ConeCam"), cameraMonitorViewId);
        dc.aprilTagDetectionPipeline = new AprilTagDetectionPipeline(dc.tagsize, dc.fx, dc.fy, dc.cx, dc.cy);

        dc.webcam.setPipeline(dc.aprilTagDetectionPipeline);
        dc.webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                dc.webcam.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
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
            ArrayList<AprilTagDetection> currentDetections = dc.aprilTagDetectionPipeline.getLatestDetections();

            if (currentDetections.size() != 0) {
                boolean tagFound = false;

                for (AprilTagDetection tag : currentDetections) {
                    if (tag.id == dc.LEFT || tag.id == dc.MIDDLE || tag.id == dc.RIGHT) {
                        dc.tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }
                //telematry for the signal sleave
                if (tagFound) {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    dc.tagToTelemetry(dc.tagOfInterest);
                } else {
                    telemetry.addLine("Don't see tag of interest :(");

                    if (dc.tagOfInterest == null) {
                        telemetry.addLine("(The tag has never been seen)");
                    } else {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        dc.tagToTelemetry(dc.tagOfInterest);
                    }
                }

            } else {
                telemetry.addLine("Don't see tag of interest :(");

                if (dc.tagOfInterest == null) {
                    telemetry.addLine("(The tag has never been seen)");
                } else {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    dc.tagToTelemetry(dc.tagOfInterest);
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
        if (dc.tagOfInterest != null) {
            telemetry.addLine("Tag snapshot:\n");
            dc.tagToTelemetry(dc.tagOfInterest);
            telemetry.update();
        } else {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }


        if (dc.tagOfInterest == null) {                                            //takes the camera value and turns it into my own varible
            dc.location = 0;
        } else if (dc.tagOfInterest.id == dc.LEFT) {
            dc.location = 1;
        } else if (dc.tagOfInterest.id == dc.MIDDLE) {
            dc.location = 2;
        } else {
            dc.location = 3;
        }

        if (opModeIsActive()) {
            dc.strafeLeft(.7, 1400);
            if (dc.location == 1 || dc.location == 0){
                dc.move(.7,-950);
                dc.stopMotors();
            }else if (dc.location==2){
                dc.stopMotors();
            } else if (dc.location==3) {
                dc.move(.7,950);
                dc.stopMotors();
            }
        }
    }
}