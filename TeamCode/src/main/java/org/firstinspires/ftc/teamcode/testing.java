package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class testing extends OpMode {
    public DcMotorEx crane;

    public Servo lefts;

    private enum CraneState {
        CRANE_MOVING,
        CRANE_START

    }

    CraneState CS = CraneState.CRANE_START;

    final int CRANE_LOW = 0;
    final int CRANE_MID = 0;
    final int CRANE_HIGH = 0;
    double cranepower = gamepad2.right_stick_y;


    @Override
    public void init() {
        crane = hardwareMap.get(DcMotorEx.class, "CraneFront");
        lefts = hardwareMap.get(Servo.class, "LeftsFront");

        CS = CraneState.CRANE_START;
        crane.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        switch (CS) {
            case CRANE_START:
                if (gamepad2.x) {
                    crane.setPower(1);
                    crane.setTargetPosition(CRANE_HIGH);
                    CS = CraneState.CRANE_MOVING;
                    crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

                } else if (gamepad2.y) {
                    crane.setPower(1);
                    crane.setTargetPosition(CRANE_MID);
                    CS = CraneState.CRANE_MOVING;
                    crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

                } else if (gamepad2.b) {
                    crane.setPower(1);
                    crane.setTargetPosition(CRANE_LOW);
                    CS = CraneState.CRANE_MOVING;
                    crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                } else {
                    crane.setPower(cranepower);
                    crane.setTargetPosition(crane.getCurrentPosition());
                    crane.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                }
                break;
            case CRANE_MOVING:
                if (cranepower > 0) {
                    crane.setTargetPosition(crane.getCurrentPosition());
                    CS = CraneState.CRANE_START;
                }
                break;
            default:
                CS = CraneState.CRANE_START;
        }
        if (gamepad2.left_trigger>0) {
            lefts.setPosition(100);
        } else if (gamepad2.right_trigger>0) {
            lefts.setPosition(-100);
        }
        gamepad2.rumble(10*1000);
    }
}