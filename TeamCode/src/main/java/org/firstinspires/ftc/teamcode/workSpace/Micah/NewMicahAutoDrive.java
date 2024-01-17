package org.firstinspires.ftc.teamcode.workSpace.Micah;

import static org.firstinspires.ftc.teamcode.workSpace.Micah.newAutonTest.Servo2;
import static org.firstinspires.ftc.teamcode.workSpace.Micah.newAutonTest.rightDrive;
import static org.firstinspires.ftc.teamcode.workSpace.Micah.newAutonTest.backRightDrive;
import static org.firstinspires.ftc.teamcode.workSpace.Micah.newAutonTest.backLeftDrive;
import static org.firstinspires.ftc.teamcode.workSpace.Micah.newAutonTest.Launchmotor;
import static org.firstinspires.ftc.teamcode.workSpace.Micah.newAutonTest.Servo;
import static org.firstinspires.ftc.teamcode.workSpace.Micah.newAutonTest.driveMB;
import static org.firstinspires.ftc.teamcode.workSpace.Micah.newAutonTest.leftDrive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "NewMicahAutoDrive")
public class NewMicahAutoDrive extends LinearOpMode {

    @Override
    public void runOpMode() {
        hardwareImports();

        waitForStart();

        driveMB(1000, 0.4);

        //strafeRightMB(1000, 0.4);
    }

    public void hardwareImports() {

        leftDrive = hardwareMap.get(DcMotor.class, "front Left");
        rightDrive = hardwareMap.get(DcMotor.class, "front Right");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back Left");
        backRightDrive = hardwareMap.get(DcMotor.class, "back Right");
        Launchmotor = hardwareMap.get(DcMotor.class, "Launch Motor");
        Servo = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo1");
        Servo2 = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo2");

        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        Servo.setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE);
    }
}
