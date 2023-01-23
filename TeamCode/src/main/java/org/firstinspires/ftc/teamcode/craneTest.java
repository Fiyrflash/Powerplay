package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp
public class craneTest extends LinearOpMode {

    private DcMotor Crane;

    public void runOpMode() throws InterruptedException {

        Crane = hardwareMap.get(DcMotor.class, "Crane");

        Crane.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        public void crane() {
            telemetry.addData("Crane", Crane.getCurrentPosition());
            telemetry.update();
            Crane.setTargetPosition(position);
            Crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Crane.setPower(power);
            while (Crane.isBusy() && opModeIsActive()) {

            }
        }

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a = true) {
                crane
            }


        }
    }
}


