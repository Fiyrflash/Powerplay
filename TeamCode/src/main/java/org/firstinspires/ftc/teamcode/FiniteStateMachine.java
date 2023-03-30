package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class FiniteStateMachine {
    @TeleOp(name = "FSM Example")
    public class FSMExample extends OpMode {
        // An Enum is used to represent lift states.
        // (This is one thing enums are designed to do)
        public enum CraneState {
            CRANE_START,
            CRANE_UP,
            CRANE_DOWN
        }

        ;

        // The liftState variable is declared out here
        // so its value persists between loop() calls
        CraneState CS = CraneState.CRANE_START;

        // Some hardware access boilerplate; these would be initialized in init()
        // the lift motor, it's in RUN_TO_POSITION mode
        public DcMotorEx crane;

        // the dump servo
        public Servo lefts;
        // used with the dump servo, this will get covered in a bit

        final int CRANE_LOW; // the low encoder position for the lift
        final int CRANE_MID;
        final int CRANE_HIGH; // the high encoder position for the lift

        public void init() {
            liftTimer.reset();

            // hardware initialization code goes here
            // this needs to correspond with the configuration used
            crane = hardwareMap.get(DcMotorEx.class, "CraneFront");
            lefts = hardwareMap.get(Servo.class, "LeftsFront");

            crane.setTargetPosition(CRANE_LOW);
            crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        public void loop() {
            crane.setPower(1.0);

            switch (CS) {
                case CRANE_START:
                    if (gamepad2.x) {
                        // x is pressed, start extending
                        crane.setTargetPosition(CRANE_HIGH);
                        CS = CraneState.CRANE_UP;
                    }
                    if (gamepad2.y) {
                        // x is pressed, start extending
                        crane.setTargetPosition(CRANE_MID);
                        CS = CraneState.CRANE_UP;
                    }
                    if (gamepad2.b) {
                        // x is pressed, start extending
                        crane.setTargetPosition(CRANE_LOW);
                        CS = CraneState.CRANE_UP;
                    }
                    break;
                case CRANE_UP:
                    if (Math.abs(crane.getCurrentPosition() - CRANE_HIGH) < 10) {

                        /*liftDump.setTargetPosition(DUMP_DEPOSIT);

                        liftTimer.reset();
                        liftState = LiftState.LIFT_DUMP;*/

                    }
                    break;
                case CRANE_DUMP:
                    if (liftTimer.seconds() >= DUMP_TIME) {
                        // The robot waited long enough, time to start
                        // retracting the lift
                        /*liftDump.setTargetPosition(DUMP_IDLE);
                        liftMotor.setTargetPosition(LIFT_LOW);
                        liftState = LiftState.LIFT_RETRACT;*/
                    }
                case CRANE_DOWN:
                    if (Math.abs(crane.getCurrentPosition() - CRANE_LOW) < 10) {
                        CS = CraneState.CRANE_START;
                    }
                    break;
                default:
                    // should never be reached, as liftState should never be null
                    CS = CraneState.CRANE_START;
            }
            if (gamepad2.y && CS != CraneState.CRANE_START) {
                CS = CraneState.CRANE_START;
            }
        }
    }
}