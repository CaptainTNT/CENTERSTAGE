package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.opencv.core.Mat;

public class BlueCloseRR extends LinearOpMode {
    Pose2d stack1 = new Pose2d(-67.5,-30.1,Math.toRadians(180));
    Pose2d stack2 = new Pose2d(-67.5,-17.9,Math.toRadians(180));
    Pose2d stack3 = new Pose2d(-67.5,17.9,Math.toRadians(180));
    Pose2d stack4 = new Pose2d(-67.5,30.1,Math.toRadians(180));
    Vector2d REDLEFTAT = new Vector2d(53.5,-30);
    Vector2d REDMIDDLEAT = new Vector2d(53.5,-37);
    Vector2d REDRIGHTAT = new Vector2d(53.5,-44);
    Vector2d REDPARKLEFT = new Vector2d(66,-12);
    Vector2d REDPARKRIGHT = new Vector2d(66,-60);
    Vector2d TrussAlignment = new Vector2d(35,9);
    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

    Pose2d StartPose = new Pose2d(12,63,Math.toRadians(90));
    Pose2d Truss_AL = new Pose2d(35,9,Math.toRadians(180));
    Trajectory PlaceRight = drive.trajectoryBuilder(StartPose,true)
            .lineToConstantHeading(new Vector2d(8.74, 30.69))
            .splineToLinearHeading(new Pose2d(53.80, 29.10, Math.toRadians(180.00)), Math.toRadians(-4.84))
            .lineToConstantHeading(TrussAlignment)
            .build();
    Trajectory PlaceMiddle = drive.trajectoryBuilder(StartPose,true)
            .lineToConstantHeading(new Vector2d(18.56, 24.77))
            .splineToLinearHeading(new Pose2d(52.93, 35.60, Math.toRadians(180.15)), Math.toRadians(23.15))
            .lineToConstantHeading(TrussAlignment)
            .build();
    Trajectory PlaceLeft = drive.trajectoryBuilder(StartPose,true)
            .lineToConstantHeading(new Vector2d(25.06, 30.55))
            .splineToLinearHeading(new Pose2d(53.37, 42.54, Math.toRadians(180.15)), Math.toRadians(23.15))
            .lineToConstantHeading(TrussAlignment)
            .build();
    Trajectory gothrutruss = drive.trajectoryBuilder(Truss_AL)
            .splineTo(new Vector2d(-40.37, 9.46), Math.toRadians(179.67))
            .lineToConstantHeading(new Vector2d(-67.5, 17.9))
            .build();
    Trajectory cyclewhites = drive.trajectoryBuilder(stack3,true)
            .splineTo(new Vector2d(-40.37, 9.46), Math.toRadians(179.67))
            .lineToConstantHeading(TrussAlignment)
            .splineToLinearHeading(new Pose2d(53.80, 29.10, Math.toRadians(180.00)), Math.toRadians(-4.84))
            .build();
    Trajectory ParkLeft = drive.trajectoryBuilder(new Pose2d(53.8,29.1), Math.toRadians(180))
            .strafeRight(35)
            .build();
    Trajectory ParkRight = drive.trajectoryBuilder(new Pose2d(53.8,29.1), Math.toRadians(180))
            .strafeLeft(20)
            .build();
    @Override
    public void runOpMode() throws InterruptedException {
        drive.setPoseEstimate(StartPose);


    }
}