package org.firstinspires.ftc.teamcode.auton;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.common.ParkingSpots;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
public class RedCloseRR extends LinearOpMode {
    Pose2d stack1 = new Pose2d(-67.5,-30.1,Math.toRadians(180));
    Pose2d stack2 = new Pose2d(-67.5,-17.9,Math.toRadians(180));
    Pose2d stack3 = new Pose2d(-67.5,17.9,Math.toRadians(180));
    Pose2d stack4 = new Pose2d(-67.5,30.1,Math.toRadians(180));
    Vector2d REDLEFTAT = new Vector2d(53.5,-30);
    Vector2d REDMIDDLEAT = new Vector2d(53.5,-37);
    Vector2d REDRIGHTAT = new Vector2d(53.5,-44);
    Vector2d REDPARKLEFT = new Vector2d(66,-12);
    Vector2d REDPARKRIGHT = new Vector2d(66,-60);
    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

    Pose2d StartPose = new Pose2d(12,-63,Math.toRadians(270));
    Trajectory RightPlacement = drive.trajectoryBuilder(StartPose)
            .lineToSplineHeading(new Pose2d(28.53, -30.26, Math.toRadians(180.00)))
            .splineTo(new Vector2d(53.5,-44), Math.toRadians(180))
            .splineToConstantHeading(new Vector2d(10.76, -9.89), Math.toRadians(153.10))

            .build();
    //This Trajectory pulls from stack 2, can change to 1 by copy pasting stack 1 into the drive trajectory builder
    Trajectory Pullfromstackandplace = drive.trajectoryBuilder(stack2,true)
            .splineTo(new Vector2d(-26.50, -10.90), Math.toRadians(186.14))
            .splineToConstantHeading(new Vector2d(10.76, -9.89), Math.toRadians(153.10))
            .splineTo(new Vector2d(53.5,-44), Math.toRadians(180))
            .build();
    Trajectory GoThruTruss = drive.trajectoryBuilder(new Pose2d(10.76,-9.89), Math.toRadians(153.1))
            .splineTo(new Vector2d(-26.50, -10.90), Math.toRadians(186.14))
            .lineToSplineHeading(new Pose2d(-63.62, -17.55, Math.toRadians(180.00)))
            .build();

    Trajectory MiddlePlacement = drive.trajectoryBuilder(StartPose)
            .lineToSplineHeading(new Pose2d(11.34, -31.27, Math.toRadians(90.00)))
            .splineTo(new Vector2d(53.5,-37),Math.toRadians(180))
            .splineToConstantHeading(new Vector2d(10.76, -9.89), Math.toRadians(153.10))
            .build();
    Trajectory LeftPlacement = drive.trajectoryBuilder(StartPose)
            .lineToSplineHeading(new Pose2d(11.34, -31.27, Math.toRadians(180.00)))
            .splineTo(new Vector2d(53.5,-30), Math.toRadians(180))
            .splineToConstantHeading(new Vector2d(10.76, -9.89), Math.toRadians(153.10))
            .build();
    @Override
    public void runOpMode() throws InterruptedException {
        drive.setPoseEstimate(StartPose);


    }
}