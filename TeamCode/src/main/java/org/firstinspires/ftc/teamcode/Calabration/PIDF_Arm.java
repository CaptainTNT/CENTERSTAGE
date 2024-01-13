package org.firstinspires.ftc.teamcode.Calabration;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp
public class PIDF_Arm extends OpMode {
    private PIDController controller;
    public static double p = 0.0025, i = 0, d = 0.00001;
    public static double f = 0.083;

    public static int target = 0;

    private final double ticks_in_degree = 700 / 180.0;


    private DcMotorEx LaunchMotor;

    @Override
    public void init(){
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        LaunchMotor =  hardwareMap.get(DcMotorEx.class, "Launch Motor");
    }

    @Override
    public void loop(){
        controller.setPID(p, i, d);
        int armPos = LaunchMotor.getCurrentPosition();
        double pid = controller.calculate(armPos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

        double power = pid + ff;

        LaunchMotor.setPower(power);

        if (gamepad2.left_stick_y > 0){
            target += 2;
        } else if (gamepad2.left_stick_y < 0){
            target -= 2;
        }


        telemetry.addData("pos ", armPos);
        telemetry.addData("target", target);
    }
}

