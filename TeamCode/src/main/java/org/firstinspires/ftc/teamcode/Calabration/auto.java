//Imports stuff
package org.firstinspires.ftc.teamcode.Calabration;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

//Sets name for code and disables it
@Disabled
@Autonomous(name = "auto")
public class auto extends OpMode {

    //Set values to variables for motors
    public static DcMotor leftDrive = null;
    public static DcMotor rightDrive = null;
    public static DcMotor backRightDrive = null;
    public static DcMotor backLeftDrive = null;
    public static DcMotorEx Launchmotor = null;
    public static DcMotorEx Launchmotor2 = null;
    public static Servo Servo = null;
    public static Servo Servo2 = null;
    public static Servo Servo3 = null;
    public static Servo Servo4 = null;

    public static ElapsedTime timer = new ElapsedTime();
    public static void strafeLeft(int target, double power, double sleep) {
        driveReset();
        leftDrive.setTargetPosition(-target);
        rightDrive.setTargetPosition(target);
        backRightDrive.setTargetPosition(-target);
        backLeftDrive.setTargetPosition(target);

        timer.reset();

        while (sleep > timer.milliseconds()) {

            leftDrive.setPower(-power);
            rightDrive.setPower(power);
            backRightDrive.setPower(-power);
            backLeftDrive.setPower(power);

            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }


        if (timer.milliseconds() >= sleep){
            stopDrive();
        }
    }

    public static void strafeRight(int target, double power, double sleep) {
        driveReset();

        leftDrive.setTargetPosition(target);
        rightDrive.setTargetPosition(-target);
        backRightDrive.setTargetPosition(target);
        backLeftDrive.setTargetPosition(-target);
        timer.reset();

        while (sleep > timer.milliseconds()) {
            leftDrive.setPower(power);
            rightDrive.setPower(power);
            backRightDrive.setPower(power);
            backLeftDrive.setPower(power);

            backLeftDrive.getCurrentPosition();


            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        if (timer.milliseconds() >= sleep){
            stopDrive();
        }


    }

    public static void spinLeft(int target, double power, double sleep) {
        driveReset();

        leftDrive.setTargetPosition(-target);
        rightDrive.setTargetPosition(target);
        backRightDrive.setTargetPosition(target);
        backLeftDrive.setTargetPosition(-target);

        timer.reset();

        while (sleep > timer.milliseconds()) {
            leftDrive.setPower(power);
            rightDrive.setPower(power);
            backRightDrive.setPower(power);
            backLeftDrive.setPower(power);

            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        if (timer.milliseconds() >= sleep){
            stopDrive();
        }


    }

    public static void spinRight(int target, double power, double sleep) {
        driveReset();

        leftDrive.setTargetPosition(target);
        rightDrive.setTargetPosition(-target);
        backRightDrive.setTargetPosition(-target);
        backLeftDrive.setTargetPosition(target);


        timer.reset();

        while (sleep > timer.milliseconds()) {
            leftDrive.setPower(power);
            rightDrive.setPower(power);
            backRightDrive.setPower(power);
            backLeftDrive.setPower(power);

            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }
        if (timer.milliseconds() >= sleep){
            stopDrive();
        }


    }



    public static void drive(int target, double power, double sleep) {
        driveReset();

        leftDrive.setTargetPosition(target);
        rightDrive.setTargetPosition(target);
        backRightDrive.setTargetPosition(target);
        backLeftDrive.setTargetPosition(target);


        timer.reset();

        while (sleep > timer.milliseconds()) {
            leftDrive.setPower(power);
            rightDrive.setPower(power);
            backRightDrive.setPower(power);
            backLeftDrive.setPower(power);

            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        if (timer.milliseconds() >= sleep){
            stopDrive();
        }

    }


    public static void arm (int Target, double sleep) {
        Reset();
        timer.reset();
        double p = 0.0025, i = 0, d = 0.00001, f = 0.083;
        double ticks_in_degree = 700 / 180.0;
        double pid;
        double ff;
        double power;
        PIDController controller;
        controller = new PIDController(p, i, d);
        Launchmotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        Launchmotor2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        while (sleep > timer.milliseconds()) {
            int target = Target;
            controller.setPID(p, i, d);
            int armPos = Launchmotor.getCurrentPosition();
            pid = controller.calculate(armPos, target);
            ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

            double proportionalTerm = 0.0015 * (target - armPos);

            power = pid + ff - proportionalTerm;
            power = Range.clip(power, -0.5, 0.5);

            Launchmotor.setPower(power);
            Launchmotor2.setPower(power);
        }


    }

    public static void stopDrive() {
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        backRightDrive.setPower(0);
        backLeftDrive.setPower(0);
    }

    public static void stopArm() {Launchmotor.setPower(0);Launchmotor2.setPower(0);}

    public static void servoLeftOpen () {

            Servo2.setPosition(1);


    }

    public static void servoRightOpen () {
        timer.reset();


            Servo.setPosition(1);

    }
    public static void servoLeftOpenFl () {



            Servo3.setPosition(1);


    }

    public static void servoRightOpenFl () {

            Servo4.setPosition(1);

    }

    public static void servoLeftClose () {
        Servo2.setPosition(0);
    }

    public static void servoRightClose () {
        Servo.setPosition(0);
    }
    public static void servoLeftCloseFl () {
        Servo3.setPosition(0);
    }

    public static void servoRightCloseFl () {
        Servo4.setPosition(0);
    }

    public static void Reset() {
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Launchmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Launchmotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Launchmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Launchmotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public static void sleep(double time) {



        timer.reset();

        while (time > timer.milliseconds()) {
            driveReset();

            stopDrive();

        }
        if (timer.milliseconds() >= time){
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }


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

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}






