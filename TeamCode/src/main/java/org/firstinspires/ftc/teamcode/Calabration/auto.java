package org.firstinspires.ftc.teamcode.Calabration;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous(name = "auto")
public class auto extends OpMode {

    public static DcMotor leftDrive = null;
    public static DcMotor rightDrive = null;
    public static DcMotor backRightDrive = null;
    public static DcMotor backLeftDrive = null;
    public static DcMotor Launchmotor = null;
    public static Servo Servo = null;
    public static Servo Servo2 = null;

    public static ElapsedTime timer = new ElapsedTime();
    static motorStatus driveMotors;

    public static void drive(int target, double power) {
        driveReset();
        driveMotors = motorStatus.busy;

        leftDrive.setTargetPosition(target);
        rightDrive.setTargetPosition(target);
        backRightDrive.setTargetPosition(target);
        backLeftDrive.setTargetPosition(target);

        while (driveMotors == motorStatus.busy) {
            leftDrive.setPower(power);
            rightDrive.setPower(power);
            backRightDrive.setPower(power);
            backLeftDrive.setPower(power);

            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            checkMotorBusy();
        }
        timer.reset();

        if (timer.milliseconds() >= 700){
            stopDrive();
            driveReset();
        }

    }

    public static void strafeRight(int target, double power) {
        driveReset();
        driveMotors = motorStatus.busy;

        leftDrive.setTargetPosition(target);
        rightDrive.setTargetPosition(-target);
        backRightDrive.setTargetPosition(target);
        backLeftDrive.setTargetPosition(-target);

        while (driveMotors == motorStatus.busy) {
            leftDrive.setPower(power);
            rightDrive.setPower(-power);
            backRightDrive.setPower(power);
            backLeftDrive.setPower(-power);

            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            checkMotorBusy();

        }
        timer.reset();

        if (timer.milliseconds() >= 700){
            stopDrive();
            driveReset();
        }


    }

    public static void strafeLeft(int target, double power) {
        driveReset();
        driveMotors = motorStatus.busy;

        leftDrive.setTargetPosition(-target);
        rightDrive.setTargetPosition(target);
        backRightDrive.setTargetPosition(-target);
        backLeftDrive.setTargetPosition(target);

        while (driveMotors == motorStatus.busy) {
            leftDrive.setPower(-power);
            rightDrive.setPower(power);
            backRightDrive.setPower(-power);
            backLeftDrive.setPower(power);

            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            checkMotorBusy();

        }
        timer.reset();

        if (timer.milliseconds() >= 700){
            stopDrive();
            driveReset();
        }


    }

    public static void spinLeft(int target, double power) {
        driveReset();
        driveMotors = motorStatus.busy;

        leftDrive.setTargetPosition(-target);
        rightDrive.setTargetPosition(target);
        backRightDrive.setTargetPosition(target);
        backLeftDrive.setTargetPosition(-target);

        while (driveMotors == motorStatus.busy) {
            leftDrive.setPower(power);
            rightDrive.setPower(power);
            backRightDrive.setPower(power);
            backLeftDrive.setPower(power);

            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            checkMotorBusy();

        }
        timer.reset();

        if (timer.milliseconds() >= 700){
            stopDrive();
            driveReset();
        }


    }

    public static void spinRight(int target, double power) {
        driveReset();
        driveMotors = motorStatus.busy;

        leftDrive.setTargetPosition(target);
        rightDrive.setTargetPosition(-target);
        backRightDrive.setTargetPosition(-target);
        backLeftDrive.setTargetPosition(target);


        while (driveMotors == motorStatus.busy) {
            leftDrive.setPower(power);
            rightDrive.setPower(power);
            backRightDrive.setPower(power);
            backLeftDrive.setPower(power);

            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            checkMotorBusy();
        }
        timer.reset();

        if (timer.milliseconds() >= 700){
            stopDrive();
            driveReset();
        }

    }

    public static void stopDrive() {
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        backRightDrive.setPower(0);
        backLeftDrive.setPower(0);
    }

    public static void arm (int target, double power, boolean autoStop) {
        Reset();
        Launchmotor.setTargetPosition(target);
        driveMotors = motorStatus.busy;

        while (driveMotors == motorStatus.busy) {
            Launchmotor.setPower(power);

            Launchmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            checkMotorBusy();
        }
        timer.reset();

        if (timer.milliseconds() >= 700){
            stopArm();
            Reset();
        }

    }

    public static void stopArm() {
        Launchmotor.setPower(0);
    }

    public static void servoLeftOpen (double sleep) {
        timer.reset();
        driveMotors = motorStatus.busy;

        while (sleep > timer.milliseconds()) {
            Servo2.setPosition(1);

        }
        checkMotorBusy();
    }

    public static void servoRightOpen (double sleep) {
        timer.reset();
        driveMotors = motorStatus.busy;

        while (sleep > timer.milliseconds()) {
            Servo.setPosition(1);
        }
        checkMotorBusy();
    }

    public static void servoLeftClose () {
        Servo2.setPosition(0);
    }

    public static void servoRightClose () {
        Servo.setPosition(0);
    }

    public static void driveReset() {
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public static void Reset() {
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Launchmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Launchmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }


    public enum motorStatus {
        busy,
        waiting
    }

    public static void checkMotorBusy(){
        if (leftDrive.isBusy() || rightDrive.isBusy() || backLeftDrive.isBusy() || backRightDrive.isBusy() || Launchmotor.isBusy()){
            driveMotors = motorStatus.busy;
        } else {
            driveMotors = motorStatus.waiting;
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
