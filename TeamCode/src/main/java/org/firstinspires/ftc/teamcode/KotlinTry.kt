package org.firstinspires.ftc.teamcode
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp
private class KotlinTry : LinearOpMode() {
    val frontLeft: DcMotor = hardwareMap.get(DcMotor:: class.java, "frontLeft")
    val frontRight: DcMotor = hardwareMap.get(DcMotor:: class.java, "frontRight")
    val backLeft: DcMotor = hardwareMap.get(DcMotor:: class.java, "backLeft")
    val backRight: DcMotor = hardwareMap.get(DcMotor:: class.java, "backRight")

    val crane: DcMotor = hardwareMap.get(DcMotor:: class.java,"CraneFront")
    val lefts: CRServo = hardwareMap.get(CRServo:: class.java, "LeftsFront")

    override fun runOpMode() {
        waitForStart()
        while (opModeIsActive()) {

            val dropoff = gamepad2.right_trigger
            val pickup = gamepad2.left_trigger
            val cranepower = gamepad2.right_stick_y.toDouble()

            val throttle = gamepad1.left_stick_y.toDouble()
            val strafeLeft = gamepad1.left_trigger.toDouble()
            val strafeRight = gamepad1.right_trigger.toDouble()
            val turn = gamepad1.right_stick_x.toDouble()

            frontLeft.power = throttle
            frontRight.power = throttle
            backLeft.power = throttle
            backRight.power = throttle

            frontLeft.power = turn
            frontRight.power = turn
            backLeft.power = turn
            backRight.power = turn

            frontLeft.power = strafeLeft
            frontRight.power = strafeLeft
            backLeft.power = strafeLeft
            backRight.power = strafeLeft

            frontLeft.power = strafeRight
            frontRight.power = strafeRight
            backLeft.power = strafeRight
            backRight.power = strafeRight

            crane.power = cranepower

            if (dropoff > 0) {
                lefts.power = 1.0

            }
            if (pickup > 0) {
                lefts.power = -1.0

            }
            if (pickup > 0 && dropoff > 0) {
                lefts.power = 0.0

            }
        }
    }
}