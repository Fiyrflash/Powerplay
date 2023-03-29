package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class FiniteStateMachine {
    @TeleOp (group = "FSM")
    public class FSMExample extends driveConstantsFSM {

        // An Enum is used to represent lift states.
        // (This is one thing enums are designed to do)
        public enum CraneState {
            CRANE_START,
            CRANE_UP,
            CRANE_DOWN,
            CRANE_DROP
        };

        CraneState CS = CraneState.CRANE_START;

        // the dump servo
        // used with the dump servo, this will get covered in a bit
        ElapsedTime cranetimer = new ElapsedTime();

        final double DROP_IDLE; // the idle position for the dump servo
        final double DROP_DEPOSIT; // the dumping position for the dump servo

        // the amount of time the dump servo takes to activate in seconds
        final double DUMP_TIME;

        final int LIFT_LOW; // the low encoder position for the lift
        final int LIFT_HIGH; // the high encoder position for the lift

        public void init() {
            cranetimer.reset();

            crane.setTargetPosition(LIFT_LOW);
            crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        public void loop() {
            crane.setPower(1.0);

            switch (CraneState) {
                case LiftState.LIFT_START:
                    // Waiting for some input
                    if (gamepad1.x) {
                        // x is pressed, start extending
                        liftMotor.setTargetPosition(LIFT_HIGH);
                        liftState = LiftState.LIFT_EXTEND;
                    }
                    break;
                case LiftState.LIFT_EXTEND:
                    // check if the lift has finished extending,
                    // otherwise do nothing.
                    if (Math.abs(liftMotor.getCurrentPosition() - LIFT_HIGH) < 10) {
                        // our threshold is within
                        // 10 encoder ticks of our target.
                        // this is pretty arbitrary, and would have to be
                        // tweaked for each robot.

                        // set the lift dump to dump
                        liftDump.setTargetPosition(DUMP_DEPOSIT);

                        liftTimer.reset();
                        liftState = LiftState.LIFT_DUMP;
                    }
                    break;
                case LiftState.LIFT_DUMP:
                    if (liftTimer.seconds() >= DUMP_TIME) {
                        // The robot waited long enough, time to start
                        // retracting the lift
                        liftDump.setTargetPosition(DUMP_IDLE);
                        liftMotor.setTargetPosition(LIFT_LOW);
                        liftState = LiftState.LIFT_RETRACT;
                    }
                    break;
                case LiftState.LIFT_RETRACT:
                    if (Math.abs(liftMotor.getCurrentPosition() - LIFT_LOW) < 10) {
                        liftState = LiftState.LIFT_START;
                    }
                    break;
                default:
                    // should never be reached, as liftState should never be null
                    liftState = LiftState.LIFT_START;
            }

            // small optimization, instead of repeating ourselves in each
            // lift state case besides LIFT_START for the cancel action,
            // it's just handled here
            if (gamepad1.y && liftState != LiftState.LIFT_START) {
                liftState = LiftState.LIFT_START;
            }

            // mecanum drive code goes here
            // But since none of the stuff in the switch case stops
            // the robot, this will always run!
            updateDrive(gamepad1, gamepad2);
        }
    }
}
