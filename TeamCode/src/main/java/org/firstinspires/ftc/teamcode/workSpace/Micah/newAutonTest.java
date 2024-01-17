package org.firstinspires.ftc.teamcode.workSpace.Micah;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous(name = "newAutonTest")
public class newAutonTest extends OpMode {

    public static DcMotor leftDrive = null;
    public static DcMotor rightDrive = null;
    public static DcMotor backRightDrive = null;
    public static DcMotor backLeftDrive = null;
    public static DcMotor Launchmotor = null;
    public static Servo Servo = null;
    public static Servo Servo2 = null;

    public static ElapsedTime timer = new ElapsedTime();
    static motorStatus driveMotors;

    public static void driveMB(int target, double power) {
        driveReset();
        driveMotors = motorStatus.driving;

        leftDrive.setTargetPosition(target);
        rightDrive.setTargetPosition(target);
        backRightDrive.setTargetPosition(target);
        backLeftDrive.setTargetPosition(target);

        while (driveMotors == motorStatus.driving) {
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
            Reset();
        }

    }

    public static void strafeRightMB(int target, double power) {
        driveReset();
        driveMotors = motorStatus.driving;

        leftDrive.setTargetPosition(target);
        rightDrive.setTargetPosition(-target);
        backRightDrive.setTargetPosition(target);
        backLeftDrive.setTargetPosition(-target);

        while (driveMotors == motorStatus.driving) {
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
            Reset();
        }


    }

    public static void strafeLeftMB(int target, double power) {
        driveReset();
        driveMotors = motorStatus.driving;

        leftDrive.setTargetPosition(-target);
        rightDrive.setTargetPosition(target);
        backRightDrive.setTargetPosition(-target);
        backLeftDrive.setTargetPosition(target);

        while (driveMotors == motorStatus.driving) {
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
            Reset();
        }


    }

    public static void spinLeftMB(int target, double power) {
        driveReset();
        driveMotors = motorStatus.driving;

        leftDrive.setTargetPosition(-target);
        rightDrive.setTargetPosition(target);
        backRightDrive.setTargetPosition(target);
        backLeftDrive.setTargetPosition(-target);

        while (driveMotors == motorStatus.driving) {
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
            Reset();
        }


    }

    public static void spinRightMD(int target, double power) {
        driveReset();
        driveMotors = motorStatus.driving;

        leftDrive.setTargetPosition(target);
        rightDrive.setTargetPosition(-target);
        backRightDrive.setTargetPosition(-target);
        backLeftDrive.setTargetPosition(target);


        while (driveMotors == motorStatus.driving) {
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
            Reset();
        }


    }

    public static void stopDrive() {
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        backRightDrive.setPower(0);
        backLeftDrive.setPower(0);
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


    public enum motorStatus {
        driving,
        waiting
    }

    public static void checkMotorBusy(){
        if (leftDrive.isBusy() || rightDrive.isBusy() || backLeftDrive.isBusy() || backRightDrive.isBusy()){
            driveMotors = motorStatus.driving;
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
