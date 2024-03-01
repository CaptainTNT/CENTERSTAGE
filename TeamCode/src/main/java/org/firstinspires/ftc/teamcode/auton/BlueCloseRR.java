package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.common.BluePropPipeline.Location.MIDDLE;
import static org.firstinspires.ftc.teamcode.common.BluePropPipeline.Location.LEFT;
import static org.firstinspires.ftc.teamcode.common.BluePropPipeline.Location.RIGHT;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.BluePropPipeline;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import org.firstinspires.ftc.vision.VisionPortal;
import org.opencv.core.Mat;

public class BlueCloseRR extends LinearOpMode {
    private BluePropPipeline.Location location = MIDDLE;
    private BluePropPipeline BluePropProcessor;
    private VisionPortal visionPortal;
    Pose2d stack1 = new Pose2d(-67.5,-30.1,Math.toRadians(180));
    Pose2d stack2 = new Pose2d(-67.5,-17.9,Math.toRadians(180));
    Pose2d stack3 = new Pose2d(-67.5,17.9,Math.toRadians(180));
    Pose2d stack4 = new Pose2d(-67.5,30.1,Math.toRadians(180));
    Vector2d REDLEFTAT = new Vector2d(53.5,-30);
    Vector2d REDMIDDLEAT = new Vector2d(53.5,-37);
    Vector2d REDRIGHTAT = new Vector2d(53.5,-44);
    Vector2d REDPARKLEFT = new Vector2d(66,-12);
    Vector2d REDPARKRIGHT = new Vector2d(66,-60);
    Vector2d TrussAlignment = new Vector2d(10.76, 9.89);
    SampleMecanumDrive drive;

    Pose2d StartPose = new Pose2d(12,63,Math.toRadians(90));
    Pose2d Truss_AL = new Pose2d(10.76, 9.89,Math.toRadians(180));

    public void runOpMode() throws InterruptedException {
        BluePropProcessor = new BluePropPipeline(telemetry);
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"), BluePropProcessor);


        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence PlaceRight = drive.trajectorySequenceBuilder(StartPose)
                .lineToSplineHeading(new Pose2d(11.34, 31.27, Math.toRadians(180.00)))
                .splineToConstantHeading(new Vector2d(7.00, 31.27), Math.toRadians(180.00))
                .lineToConstantHeading(TrussAlignment)
                .build();


        TrajectorySequence PlaceMiddle = drive.trajectorySequenceBuilder(StartPose)
                .lineToSplineHeading(new Pose2d(12.00, 31.27, Math.toRadians(270.00)))
                .splineTo(new Vector2d(49.50, 37.00), Math.toRadians(180.00))
                .lineToConstantHeading(TrussAlignment)
                .build();
        TrajectorySequence PlaceLeft = drive.trajectorySequenceBuilder(StartPose)
                .lineToSplineHeading(new Pose2d(28.53, 30.26, Math.toRadians(180.00)))
                .splineTo(new Vector2d(53.50, 44.00), Math.toRadians(180.00))

                .lineToConstantHeading(TrussAlignment)
                .build();
        TrajectorySequence gothrutruss = drive.trajectorySequenceBuilder(Truss_AL)
                .splineTo(new Vector2d(-26.00, 10.90), Math.toRadians(180.00))
                .lineToSplineHeading(new Pose2d(-60.62, 17.55, Math.toRadians(180.00)))
                .build();

        TrajectorySequence cyclewhites = drive.trajectorySequenceBuilder(stack3)
                .lineTo(new Vector2d(-26.50, 10.90))
                .splineToConstantHeading(new Vector2d(10.76, 9.89), Math.toRadians(180.00))
                .lineToConstantHeading(new Vector2d(45.84, 30.00))
                .build();

        TrajectorySequence ParkLeft = drive.trajectorySequenceBuilder(new Pose2d(53.8,29.1))
                .strafeRight(35)
                .build();
        TrajectorySequence ParkRight = drive.trajectorySequenceBuilder(new Pose2d(53.8,29.1))
                .strafeLeft(20)
                .build();
        while (!isStarted()) {
            location = BluePropProcessor.getLocation();
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Location", BluePropProcessor.getLocation());
            telemetry.update();
        }

        drive.setPoseEstimate(StartPose);
        if (location == LEFT) {

            drive.followTrajectorySequence(PlaceLeft);
            drive.followTrajectorySequence(gothrutruss);
            drive.followTrajectorySequence(cyclewhites);
            drive.followTrajectorySequence(ParkLeft);

            //drive.followTrajectorySequence(ParkRight);




        } else if(location == MIDDLE){

            drive.followTrajectorySequence(PlaceMiddle);
            drive.followTrajectorySequence(gothrutruss);
            drive.followTrajectorySequence(cyclewhites);

            drive.followTrajectorySequence(ParkLeft);
            //drive.followTrajectorySequence(ParkRight);
        }
        else if (location == RIGHT) {

            drive.followTrajectorySequence(PlaceRight);
            drive.followTrajectorySequence(gothrutruss);
            drive.followTrajectorySequence(cyclewhites);

            drive.followTrajectorySequence(ParkLeft);
            //drive.followTrajectorySequence(ParkRight);

        }


    }
}