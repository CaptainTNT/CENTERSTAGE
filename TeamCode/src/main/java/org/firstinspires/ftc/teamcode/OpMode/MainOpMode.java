package org.firstinspires.ftc.teamcode.OpMode;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class MainOpMode extends OpMode {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotorEx LaunchMotor = null;
    private Servo servo1 = null;
    private Servo servo2 = null;

    double Speed;
    double Turn_Speed;
    double Vertical;
    double Horizontal;
    double Pivot;
    double leftServo = 0; //odd open, even closed
    double rightServo = 0; //odd open, even closed
    double p = 0.0025, i = 0, d = 0.00001, f = 0.083;
    double ticks_in_degree = 700 / 180.0;
    double pid;
    double ff;
    double power;

    int newTarget = 10;
    int armPos;
    int target = 0;

    boolean changed1 = false; //leftservo
    boolean changed2 = false; //rightservo

    PIDController controller;

    @Override
    public void init() {

        controller = new PIDController(p, i, d);
        leftDrive = hardwareMap.get(DcMotor.class, "front Left");
        rightDrive = hardwareMap.get(DcMotor.class, "front Right");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back Left");
        backRightDrive = hardwareMap.get(DcMotor.class, "back Right");
        LaunchMotor =  hardwareMap.get(DcMotorEx.class, "Launch Motor");
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");


        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        servo1.setPosition(Servo.Direction.REVERSE.ordinal());

        LaunchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LaunchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Speed = 0.5;
        Turn_Speed = 0.55;

        telemetry.addData("Info", "OpMode is ready to run");
        telemetry.update();
    }
    public void PIDLoop(int Target) {
        target = Target;
        controller.setPID(p, i, d);
        armPos = LaunchMotor.getCurrentPosition();
        pid = controller.calculate(armPos, target);
        ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

        double proportionalTerm = 0.0015 * (target - armPos);

        power = pid + ff - proportionalTerm;
        power = Range.clip(power, -1.0, 1.0);

        LaunchMotor.setPower(power);
    }
    public void loop() {

        PIDLoop(newTarget);

        if (gamepad2.left_stick_y > 0){
            newTarget++;
        } else if (gamepad2.left_stick_y < 0){
            newTarget--;
        }

        if (gamepad2.left_stick_y > 0){
            newTarget++;
        } else if (gamepad2.left_stick_y < 0){
            newTarget--;
        }

        if (gamepad2.left_trigger > 0.3){
            newTarget = -100;
        } else if (gamepad2.right_trigger > 0.3){
            newTarget = -1100;
        }

        if (gamepad2.left_bumper && !changed1) {
            leftServo = (leftServo == 0) ? 1 : 0;
            changed1 = true;
        } else if (!gamepad2.left_bumper && changed1) {
            changed1 = false;
        }

        if (gamepad2.right_bumper && !changed2) {
            rightServo = (rightServo == 0) ? 1 : 0;
            changed2 = true;
        } else if (!gamepad2.right_bumper && changed2) {
            changed2 = false;
        }

        servo1.setPosition(rightServo);
        servo2.setPosition(leftServo);

        Vertical = Math.min(Math.max(-gamepad1.left_stick_x, -Speed), Speed);
        Horizontal = Math.min(Math.max(-gamepad1.left_stick_y, -Speed), Speed);
        Pivot = Math.min(Math.max(gamepad1.right_stick_x, -Turn_Speed), Turn_Speed);
        backLeftDrive.setPower(-Pivot + (Vertical - Horizontal));
        backRightDrive.setPower(-Pivot + Vertical + Horizontal);
        leftDrive.setPower(Pivot + Vertical + Horizontal);
        rightDrive.setPower(Pivot + (Vertical - Horizontal));

        telemetry.addData("power", power);
        telemetry.update();
    }

    @Override
    public void stop(){
        telemetry.addData("Info", "OpMode has been successfully stopped");
        telemetry.update();
    }

}
