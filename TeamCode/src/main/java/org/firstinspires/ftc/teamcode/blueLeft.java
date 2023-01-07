package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class blueLeft extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;


    //private CRServo Right;
    private CRServo Left;
    private DcMotor Crain;
    private DcMotor Spin;


    public void runOpMode() throws InterruptedException {


        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        //Right = hardwareMap.get(CRServo.class,"Rights");
        Left = hardwareMap.get(CRServo.class, "Lefts");
        Crain = hardwareMap.get(DcMotor.class, "Crane");
        //Spin = hardwareMap.get(DcMotor.class, "Spin");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        if (opModeIsActive()) {

        }


    }
}

