package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

public class Button extends LinearOpMode {
    @Override
    public void runOpMode() {
    }

    public void mode_a(byte mode1, byte mode2, char button){
        if (gamepad2.a){
            mode1 = 1;
            if (!gamepad2.a){
                if (gamepad2.a){

                }
            }
        }
    }
}
