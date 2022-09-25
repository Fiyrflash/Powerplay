package org.firstinspires.ftc.teamcode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.sun.source.tree.WhileLoopTree;


@Autonomous
public class encoder_crane extends LinearOpMode {


    private DcMotor crane;

    public void runOpMode() {
        crane = hardwareMap.get(DcMotor.class, "crane");

        waitForStart();

        if (opModeIsActive()) {

            //crane(1,1000);
            crane.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            crane.setTargetPosition(1000);
            crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            crane.setPower(1);
            while (crane.isBusy()){

            }
            crane.setTargetPosition(500);
            crane.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            crane.setPower(1);
            while (crane.isBusy()){
                
            }


            telemetry.addData("encoder value", crane.getCurrentPosition());
            telemetry.update();
        }
    }


    public void crane(double power,int time){
        crane.setPower(power);
        sleep(time);
        crane.setPower(0);
    }
}


