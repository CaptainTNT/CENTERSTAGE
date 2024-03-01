package org.firstinspires.ftc.teamcode.Calabration;

import static org.firstinspires.ftc.teamcode.Calabration.auto.Launchmotor;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Launchmotor2;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Servo;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Servo2;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Servo3;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Servo4;
import static org.firstinspires.ftc.teamcode.Calabration.auto.backLeftDrive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.backRightDrive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.leftDrive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.rightDrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class hardwareImports {
    public hardwareImports(HardwareMap hardwareMap) {


    Launchmotor = hardwareMap.get(DcMotorEx.class, "Launch Motor");
    Launchmotor2 = hardwareMap.get(DcMotorEx.class, "Launch Motor 2");
    Servo = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo1");
    Servo2 = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo2");
    Servo3 = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo 6");
    Servo4 = hardwareMap.get(Servo.class, "servo 7");


    Servo.setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE);
    Launchmotor2.setDirection(DcMotorEx.Direction.REVERSE);
}
}
