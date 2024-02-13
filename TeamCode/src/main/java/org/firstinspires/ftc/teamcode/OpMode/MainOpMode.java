package org.firstinspires.ftc.teamcode.OpMode;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class MainOpMode extends OpMode {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotorEx LaunchMotor = null;
    private DcMotorEx LaunchMotor2 = null;
    private DcMotor LaunchMotor3 = null;
    private Servo servo1 = null;
    private Servo servo2 = null;
    private Servo servo3 = null;
    private Servo servo4 = null;
    private Servo servo5 = null;

    double Speed;
    double MaxSpeed;
    double MinSpeed;
    double Turn_Speed;
    double Vertical;
    double Horizontal;
    double Pivot;
    double leftServo = 0; //odd open, even closed
    double rightServo = 1; //odd closed, even open
    double p = 0.0025, i = 0.074, d = 0.00001, f = 0.083;
    double ticks_in_degree = 700 / 180.0;
    double pid;
    double ff;
    double power;
    int newTarget = 10;
    int armPos;
    int target = 0;
    boolean Safety = false;
    boolean Armed = false;
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
        LaunchMotor = hardwareMap.get(DcMotorEx.class, "Launch Motor");
        LaunchMotor2 = hardwareMap.get(DcMotorEx.class, "Launch Motor 2");
        LaunchMotor3 = hardwareMap.get(DcMotor.class, "Launch Motor 3");
        servo1 = hardwareMap.get(Servo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");
        servo3 = hardwareMap.get(Servo.class, "servo 3");
        servo4 = hardwareMap.get(Servo.class, "servo 4");
        servo5 = hardwareMap.get(Servo.class, "servo 5");


        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        LaunchMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
        servo1.setPosition(Servo.Direction.REVERSE.ordinal());

        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LaunchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LaunchMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LaunchMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LaunchMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Speed = 0.85;
        MaxSpeed = 1;
        MinSpeed = 0.65;
        Turn_Speed = 0.7;

        servo3.setPosition(1);
        servo4.setPosition(0);

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
        power = Range.clip(power, -0.5, 0.5);

        LaunchMotor.setPower(power);
        LaunchMotor2.setPower(power);

    }
    public void loop() {

        PIDLoop(newTarget);

        if (gamepad2.touchpad && !Safety) {
            gamepad2.setLedColor(1, 0, 0, Gamepad.LED_DURATION_CONTINUOUS);
            gamepad2.rumble(1000);
            //newTarget = -800;
            Safety = true;
        } else if (!gamepad2.touchpad && Safety){
            Armed = true;
        } else if (gamepad2.touchpad && Armed){
            servo5.setPosition(1);
        }

        if (gamepad1.left_trigger > 0.3){
            Speed = MinSpeed;
        } else {
            Speed = MaxSpeed;
        }

        if (gamepad2.left_stick_y > 0){
            newTarget += 2;
        } else if (gamepad2.left_stick_y < 0){
            newTarget -= 2;
        }

        if (gamepad2.left_stick_y > 0){
            newTarget += 2;
        } else if (gamepad2.left_stick_y < 0){
            newTarget -= 2;
        }

        if (gamepad2.left_bumper){
            newTarget = 50;
        } else if (gamepad2.right_bumper){
            newTarget = -1350;
        }

        if (gamepad2.left_trigger > 0.3 && !changed1) {
            leftServo = (leftServo == 0) ? 1 : 0;
            changed1 = true;
        } else if (gamepad2.left_trigger < 0.3 && changed1) {
            changed1 = false;
        }

        if (gamepad2.right_trigger > 0.3 && !changed2) {
            rightServo = (rightServo == 0) ? 1 : 0;
            changed2 = true;
        } else if (gamepad2.right_trigger < 0.3 && changed2) {
            changed2 = false;
        }
        if (gamepad2.triangle){
            servo3.setPosition(0);
            servo4.setPosition(1);
        }
        servo1.setPosition(rightServo);
        servo2.setPosition(leftServo);

        LaunchMotor3.setPower(gamepad2.right_stick_y);
        LaunchMotor3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Vertical = Math.min(Math.max(gamepad1.left_stick_x, -Speed), Speed);
        Horizontal = Math.min(Math.max(-gamepad1.left_stick_y, -Speed), Speed);
        Pivot = Math.min(Math.max(gamepad1.right_stick_x, -Turn_Speed), Turn_Speed);
        backLeftDrive.setPower(-Pivot + (Vertical - Horizontal));
        backRightDrive.setPower(-Pivot + Vertical + Horizontal);
        leftDrive.setPower(Pivot + Vertical + Horizontal);
        rightDrive.setPower(Pivot + (Vertical - Horizontal));

        telemetry.addData("RightServo", rightServo);
        telemetry.addData("LeftServer", leftServo);
        telemetry.addData("armPos", armPos);
    }

    @Override
    public void stop(){
        telemetry.addData("Info", "OpMode has been successfully stopped");
        telemetry.update();
    }

}
