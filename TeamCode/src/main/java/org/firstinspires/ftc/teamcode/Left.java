package org.firstinspires.ftc.teamcode;                                     //imports

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class Left extends LinearOpMode {


    @Override
    public void runOpMode() {
        driveConstants dc = new driveConstants();
        AprilTagAutonomousInitDetectionExample aT = new AprilTagAutonomousInitDetectionExample();

        dc.initGyro();
        aT.scan();

        if (opModeIsActive()) {
            dc.strafeLeft(.7, 1400);
            if (aT.location == 1 || aT.location == 0){
                dc.move(.7,-950);
                dc.stopMotors();
            }else if (aT.location==2){
                dc.stopMotors();
            } else if (aT.location==3) {
                dc.move(.7,950);
                dc.stopMotors();
            }
        }
    }
}