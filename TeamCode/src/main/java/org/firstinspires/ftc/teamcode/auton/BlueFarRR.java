package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.Calabration.auto.servoLeftClose;
import static org.firstinspires.ftc.teamcode.Calabration.auto.servoLeftCloseFl;
import static org.firstinspires.ftc.teamcode.Calabration.auto.servoLeftOpen;
import static org.firstinspires.ftc.teamcode.common.RedPropPipeline.Location.LEFT;
import static org.firstinspires.ftc.teamcode.common.RedPropPipeline.Location.MIDDLE;
import static org.firstinspires.ftc.teamcode.common.RedPropPipeline.Location.RIGHT;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Calabration.hardwareImports;
import org.firstinspires.ftc.teamcode.common.RedPropPipeline;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;

public class BlueFarRR extends LinearOpMode {
    SampleMecanumDrive drive;
    private RedPropPipeline.Location location = MIDDLE;
    private RedPropPipeline bluePropProcessor;
    private VisionPortal visionPortal;
    Pose2d stack1 = new Pose2d(-67.5,-30.1,Math.toRadians(180));
    Pose2d stack2 = new Pose2d(-67.5,-17.9,Math.toRadians(180));
    Pose2d stack3 = new Pose2d(-67.5,17.9,Math.toRadians(180));
    Pose2d stack4 = new Pose2d(-67.5,30.1,Math.toRadians(180));
    Vector2d BLUELEFTAT = new Vector2d(53.5,42);
    Vector2d BLUEMIDDLEAT = new Vector2d(53.5,35);
    Vector2d BLUERIGHTAT = new Vector2d(53.5,29);
    Vector2d BLUEPARKLEFT = new Vector2d(66,63);
    Vector2d BLUEPARKRIGHT = new Vector2d(66,12);
    Vector2d TrussAlignment = new Vector2d(35,9);

    Pose2d StartPose = new Pose2d(36,63,Math.toRadians(90));
    Pose2d Truss_AL = new Pose2d(35,9,Math.toRadians(180));
    TrajectorySequence PlaceLeft = drive.trajectorySequenceBuilder(StartPose)
            .lineToSplineHeading(new Pose2d(25, 30.69, Math.toRadians(0)))
            .addTemporalMarker( () -> {
                servoLeftOpen();
            })
            .splineToLinearHeading(stack3, Math.toRadians(-4.84))
            .addTemporalMarker( () -> {
                servoLeftCloseFl();
                servoLeftClose();
            })
            .build();
    TrajectorySequence PlaceMiddle = drive.trajectorySequenceBuilder(StartPose)
            .lineToConstantHeading(new Vector2d(36, 24.77))
            .addTemporalMarker( () -> {
                servoLeftOpen();
            })
            .splineToLinearHeading(stack3, Math.toRadians(-4.84))
            .build();
    TrajectorySequence PlaceRight = drive.trajectorySequenceBuilder(StartPose)
            .lineToConstantHeading(new Vector2d(-46, 30.55))
            .addTemporalMarker( () -> {
                servoLeftOpen();
            })
            .splineToLinearHeading(stack3, Math.toRadians(-4.84))
            .build();
    TrajectorySequence LeftYELLPlacement = drive.trajectorySequenceBuilder(new Pose2d(35,9, Math.toRadians(180.00)))
            .lineToConstantHeading(BLUELEFTAT)
            .build();
    TrajectorySequence MiddleYELLPlacement = drive.trajectorySequenceBuilder(new Pose2d(35,9, Math.toRadians(180.00)))
            .lineToConstantHeading(BLUEMIDDLEAT)
            .build();
    TrajectorySequence RightYELLPlacement = drive.trajectorySequenceBuilder(new Pose2d(35,9, Math.toRadians(180.00)))
            .lineToConstantHeading(BLUERIGHTAT)
            .build();
    TrajectorySequence gothrutruss = drive.trajectorySequenceBuilder(stack3)
            .splineTo(new Vector2d(-40.37, 9.46), Math.toRadians(180))
            .lineToConstantHeading(TrussAlignment)
            .build();
    TrajectorySequence cyclewhites = drive.trajectorySequenceBuilder(stack3)
            .addTemporalMarker( () -> {
                servoLeftOpen();
            })
            .splineTo(new Vector2d(-40.37, 9.46), Math.toRadians(179.67))
            .lineToConstantHeading(TrussAlignment)
            .splineToLinearHeading(new Pose2d(53.80, 29.10, Math.toRadians(180.00)), Math.toRadians(-4.84))
            .build();
    TrajectorySequence ParkLeft = drive.trajectorySequenceBuilder(new Pose2d(53.8,29.1, Math.toRadians(180)))
            .strafeRight(35)
            .splineToConstantHeading(BLUEPARKLEFT,Math.toRadians(180.00))
            .build();
    TrajectorySequence ParkRight = drive.trajectorySequenceBuilder(new Pose2d(53.8,29.1, Math.toRadians(180)))
            .strafeLeft(20)
            .splineToConstantHeading(BLUEPARKRIGHT,Math.toRadians(180.00))
            .build();
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        hardwareImports motor = new hardwareImports(hardwareMap);
        drive.setPoseEstimate(StartPose);
        bluePropProcessor = new RedPropPipeline(telemetry);
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"), bluePropProcessor);



        while (!isStarted()) {
            location = bluePropProcessor.getLocation();
            telemetry.update();
        }


        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Location", bluePropProcessor.getLocation());
            telemetry.update();
        }
        if (location == LEFT) {

            drive.followTrajectorySequence(PlaceLeft);
            drive.followTrajectorySequence(cyclewhites);

            drive.followTrajectorySequence(ParkLeft);
            //drive.followTrajectorySequence(ParkRight);




        } else if(location == MIDDLE){

            drive.followTrajectorySequence(PlaceMiddle);
            drive.followTrajectorySequence(cyclewhites);

            drive.followTrajectorySequence(ParkLeft);
            //drive.followTrajectorySequence(ParkRight);
        }
        else if (location == RIGHT) {

            drive.followTrajectorySequence(PlaceRight);
            drive.followTrajectorySequence(cyclewhites);

            drive.followTrajectorySequence(ParkLeft);
            //drive.followTrajectorySequence(ParkRight);

        }


    }
}