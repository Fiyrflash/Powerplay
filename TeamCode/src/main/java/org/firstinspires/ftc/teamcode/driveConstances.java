package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class driveConstances extends LinearOpMode{

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public CRServo leftFront;
    public CRServo leftBack;
    public DcMotor craneFront;
    public DcMotor craneBack;

    public void runOpMode() throws InterruptedException {

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

        craneFront.setDirection(DcMotorSimple.Direction.REVERSE);
        craneFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        craneFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        craneBack.setDirection(DcMotorSimple.Direction.REVERSE);
        craneBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        craneBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        craneBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
