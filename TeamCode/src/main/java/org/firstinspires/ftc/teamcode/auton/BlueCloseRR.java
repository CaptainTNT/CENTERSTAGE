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
    Vector2d TrussAlignment = new Vector2d(35,9);
    SampleMecanumDrive drive;

    Pose2d StartPose = new Pose2d(12,63,Math.toRadians(90));
    Pose2d Truss_AL = new Pose2d(35,9,Math.toRadians(180));

    public void runOpMode() throws InterruptedException {
        BluePropProcessor = new BluePropPipeline(telemetry);
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"), BluePropProcessor);


        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence PlaceRight = drive.trajectorySequenceBuilder(StartPose)
                .lineToConstantHeading(new Vector2d(8.74, 30.69))
                .splineToLinearHeading(new Pose2d(53.80, 29.10, Math.toRadians(180.00)), Math.toRadians(-4.84))
                .lineToConstantHeading(TrussAlignment)
                .build();
        TrajectorySequence PlaceMiddle = drive.trajectorySequenceBuilder(StartPose)
                .lineToConstantHeading(new Vector2d(18.56, 24.77))
                .splineToLinearHeading(new Pose2d(52.93, 35.60, Math.toRadians(180.15)), Math.toRadians(23.15))
                .lineToConstantHeading(TrussAlignment)
                .build();
        TrajectorySequence PlaceLeft = drive.trajectorySequenceBuilder(StartPose)
                .lineToConstantHeading(new Vector2d(25.06, 30.55))
                .splineToLinearHeading(new Pose2d(53.37, 42.54, Math.toRadians(180.15)), Math.toRadians(23.15))
                .lineToConstantHeading(TrussAlignment)
                .build();
        TrajectorySequence gothrutruss = drive.trajectorySequenceBuilder(Truss_AL)
                .splineTo(new Vector2d(-40.37, 9.46), Math.toRadians(179.67))
                .lineToConstantHeading(new Vector2d(-67.5, 17.9))
                .build();
        TrajectorySequence cyclewhites = drive.trajectorySequenceBuilder(stack3)
                .splineTo(new Vector2d(-40.37, 9.46), Math.toRadians(179.67))
                .lineToConstantHeading(TrussAlignment)
                .splineToLinearHeading(new Pose2d(53.80, 29.10, Math.toRadians(180.00)), Math.toRadians(-4.84))
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