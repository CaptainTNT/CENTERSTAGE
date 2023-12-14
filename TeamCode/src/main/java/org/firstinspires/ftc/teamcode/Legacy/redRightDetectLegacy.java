//Imports stuff
package org.firstinspires.ftc.teamcode.Legacy;


import android.util.Size;

import static org.firstinspires.ftc.teamcode.Calabration.auto.Reset;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Servo;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Servo2;
import static org.firstinspires.ftc.teamcode.Calabration.auto.backLeftDrive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.backRightDrive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.driveReset;
import static org.firstinspires.ftc.teamcode.Calabration.auto.leftDrive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.rightDrive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.servoLeftClose;
import static org.firstinspires.ftc.teamcode.Calabration.auto.servoRightClose;
import static org.firstinspires.ftc.teamcode.Calabration.auto.stopArm;
import static org.firstinspires.ftc.teamcode.Calabration.auto.stopDrive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

//Sets name for code and groups it for easy access in Driver Station
@Disabled
@Autonomous(name = "redRightDetectLegacy", group = "redDetect")
public class redRightDetectLegacy extends LinearOpMode {
    private VisionPortal portal;
    private String Type = "LEFT";
    String Location = null;

    @Override
    public void runOpMode() {

        telemetry.addLine("Detection: WAITING");
        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(320, 240))
                .setCamera(BuiltinCameraDirection.FRONT)
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();

        portal.close();

        leftDrive = hardwareMap.get(DcMotor.class, "front Left");
        rightDrive = hardwareMap.get(DcMotor.class, "front Right");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back Left");
        backRightDrive = hardwareMap.get(DcMotor.class, "back Right");
//        Launchmotor = hardwareMap.get(DcMotor.class, "Launch Motor");
        Servo = hardwareMap.get(Servo.class, "servo1");
        Servo2 = hardwareMap.get(Servo.class, "servo2");

        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        Servo.setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE);
        Reset();

        servoLeftClose();
        servoRightClose();


        waitForStart();
        //String Location = "MIDDLE";


        //41 (40.625) in at 0.4% Power


        if (Location == "LEFT") {

            Reset();
//            drive(-1150, -0.4);
            sleep(3000);
            stopDrive();
            Reset();

            Reset();
//            spinRight(920, -0.4);
            sleep(2000);
            stopDrive();
            Reset();

            Reset();
//            strafeRight(420, -0.4);
            sleep(2000);
            stopDrive();
            Reset();


            Reset();
//            drive(350, -0.4);
            sleep(3000);
            stopDrive();
            Reset();

//            servoLeftOpen();
            sleep(3000);

            Reset();
//            drive(-271, -0.4);
            sleep(2000);
            stopDrive();
            Reset();


            Reset();
//            arm(-1200, 0.6);
            sleep(1000);
            stopDrive();
            driveReset();

            driveReset();
//            drive(-1460, 0.4);
            sleep(2000);
            stopDrive();
            driveReset();


//            servoRightOpen();
            sleep(2000);

            driveReset();
//            arm(1000, 0.6);
            sleep(2000);
            stopDrive();
            stopArm();
            Reset();


            stop();


        } else if (Location == "MIDDLE") {

            Reset();
//            drive(-1950, -0.4);
            sleep(3000);
            stopDrive();
            Reset();


//            servoLeftOpen();
            sleep(3000);

            Reset();
//            drive(-300, -0.4);
            sleep(3000);
            stopDrive();
            Reset();

            Reset();
//            spinRight(920, 0.4);
            sleep(2000);
            stopDrive();
            Reset();

            Reset();
//            arm(-1200, 0.6);
            sleep(1000);
            stopDrive();
            driveReset();

            driveReset();
//            drive(-1350, 0.4);
            sleep(2000);
            stopDrive();
            driveReset();

            driveReset();
//            strafeLeft(6000, 0.4);
            sleep(1800);
            stopDrive();
            driveReset();

            driveReset();
//            drive(-500, 0.4);
            sleep(1000);
            stopDrive();
            driveReset();

//            servoRightOpen();
            sleep(2000);

            driveReset();
//            arm(-300, 0.6);
            sleep(2000);
            stopDrive();
            stopArm();
            Reset();


            stop();


        } else if (Location == "RIGHT") {
            Reset();
//            drive(-123, -0.4);
            sleep(3000);
            stopDrive();
            Reset();

            Reset();
//            spinRight(920, 0.4);
            sleep(2000);
            stopDrive();
            Reset();

            Reset();
//            drive(-574, -0.4);
            sleep(3000);
            stopDrive();
            Reset();

            driveReset();
//            strafeRight(1000, 0.4);
            sleep(1800);
            stopDrive();
            driveReset();

//            servoLeftOpen();
            sleep(3000);

            Reset();
//            arm(-1200, 0.6);
            sleep(1000);
            stopDrive();
            driveReset();

            Reset();
//            drive(-615, -0.4);
            sleep(3000);
            stopDrive();
            Reset();

//            servoRightOpen();
            sleep(3000);

            stop();

        }

        stop();
    }}




