package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class testing extends OpMode {
    public DcMotor crane;
    double cranepower;
    @Override
    public void init() {
        cranepower = gamepad1.right_stick_y;
        crane = hardwareMap.get(DcMotorEx.class, "CraneFront");
    }

    @Override
    public void loop() {
        telemetry.addData("RunMode: ", crane.getMode());
        crane.setPower(cranepower);

    }
}