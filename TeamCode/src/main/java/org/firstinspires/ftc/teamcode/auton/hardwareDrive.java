package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.auton.auto.Launchmotor;
import static org.firstinspires.ftc.teamcode.auton.auto.Reset;
import static org.firstinspires.ftc.teamcode.auton.auto.Servo;
import static org.firstinspires.ftc.teamcode.auton.auto.Servo2;
import static org.firstinspires.ftc.teamcode.auton.auto.arm;
import static org.firstinspires.ftc.teamcode.auton.auto.backLeftDrive;
import static org.firstinspires.ftc.teamcode.auton.auto.backRightDrive;
import static org.firstinspires.ftc.teamcode.auton.auto.drive;
import static org.firstinspires.ftc.teamcode.auton.auto.driveReset;
import static org.firstinspires.ftc.teamcode.auton.auto.leftDrive;
import static org.firstinspires.ftc.teamcode.auton.auto.rightDrive;
import static org.firstinspires.ftc.teamcode.auton.auto.servoLeftClose;
import static org.firstinspires.ftc.teamcode.auton.auto.servoLeftOpen;
import static org.firstinspires.ftc.teamcode.auton.auto.servoRightClose;
import static org.firstinspires.ftc.teamcode.auton.auto.servoRightOpen;
import static org.firstinspires.ftc.teamcode.auton.auto.spinLeft;
import static org.firstinspires.ftc.teamcode.auton.auto.spinRight;
import static org.firstinspires.ftc.teamcode.auton.auto.stopArm;
import static org.firstinspires.ftc.teamcode.auton.auto.stopDrive;
import static org.firstinspires.ftc.teamcode.auton.auto.strafeLeft;
import static org.firstinspires.ftc.teamcode.auton.auto.strafeRight;
import org.firstinspires.ftc.teamcode.auton.hardwareDrive.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@Disabled
@Autonomous(name = "hardwareDrive", group = "Blue")
public class hardwareDrive extends LinearOpMode {

    public void hardwareImport() {
        leftDrive = hardwareMap.get(DcMotor.class, "front Left");
        rightDrive = hardwareMap.get(DcMotor.class, "front Right");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back Left");
        backRightDrive = hardwareMap.get(DcMotor.class, "back Right");
        Launchmotor = hardwareMap.get(DcMotor.class, "Launch Motor");
        Servo = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo1");
        Servo2 = hardwareMap.get(Servo.class, "servo2");

        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        Servo.setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE);
    }


    @Override
    public void runOpMode() {


        }

        // Save more CPU resources when camera is no longer needed.
    }   // end runOpMode() // end class
