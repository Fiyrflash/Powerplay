package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor

class KotlinAuto : LinearOpMode() {
    val frontLeft: DcMotor = hardwareMap.get(DcMotor:: class.java, "frontLeft")
    val frontRight: DcMotor = hardwareMap.get(DcMotor:: class.java, "frontRight")
    val backLeft: DcMotor = hardwareMap.get(DcMotor:: class.java, "backLeft")
    val backRight: DcMotor = hardwareMap.get(DcMotor:: class.java, "backRight")

    val crane: DcMotor = hardwareMap.get(DcMotor:: class.java,"CraneFront")
    val lefts: CRServo = hardwareMap.get(CRServo:: class.java, "LeftsFront")
    override fun runOpMode() {
        waitForStart()
        if (opModeIsActive()){

        }
        fun strafeLeft(){

        }
    }
}