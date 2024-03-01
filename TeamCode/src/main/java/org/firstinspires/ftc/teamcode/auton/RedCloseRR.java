package org.firstinspires.ftc.teamcode.auton;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Servo;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Servo2;
import static org.firstinspires.ftc.teamcode.Calabration.auto.*;
import static org.firstinspires.ftc.teamcode.common.RedPropPipeline.Location.LEFT;
import static org.firstinspires.ftc.teamcode.common.RedPropPipeline.Location.MIDDLE;
import static org.firstinspires.ftc.teamcode.common.RedPropPipeline.Location.RIGHT;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.twod.Line;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.RedPropPipeline;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;

@Config
@Autonomous
public class RedCloseRR extends LinearOpMode {
    private RedPropPipeline.Location location = MIDDLE;
    private RedPropPipeline redPropProcessor;
    private VisionPortal visionPortal;


    SampleMecanumDrive drive;
    Pose2d stack1 = new Pose2d(-67.5,-30.1,Math.toRadians(180));
    Pose2d stack2 = new Pose2d(-67.5,-17.9,Math.toRadians(180));
    Pose2d stack3 = new Pose2d(-67.5,17.9,Math.toRadians(180));
    Pose2d stack4 = new Pose2d(-67.5,30.1,Math.toRadians(180));
    Vector2d REDLEFTAT = new Vector2d(53.5,-30);
    Vector2d REDMIDDLEAT = new Vector2d(53.5,-37);
    Vector2d REDRIGHTAT = new Vector2d(53.5,-44);
    Vector2d REDPARKLEFT = new Vector2d(66,-12);
    Vector2d REDPARKRIGHT = new Vector2d(66,-60);


    @Override
    public void runOpMode() throws InterruptedException {
        redPropProcessor = new RedPropPipeline(telemetry);
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"), redPropProcessor);
        //Defines and reverses motors
        Servo = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo1");
        Servo2 = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo2");
        Servo3 = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo 6");
        Servo4 = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo 7");
        Launchmotor = hardwareMap.get(DcMotorEx.class, "Launch Motor");
        Launchmotor2 = hardwareMap.get(DcMotorEx.class, "Launch Motor 2");
        Servo.setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE);
        Launchmotor2.setDirection(DcMotorEx.Direction.REVERSE);

        Pose2d StartPose = new Pose2d(12,-63,Math.toRadians(270));
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        /*TrajectorySequence Test2 = drive.trajectorySequenceBuilder(StartPose)
                .lineToSplineHeading(new Pose2d(11.34, -31.27, Math.toRadians(180.00)))
                .splineToConstantHeading(new Vector2d(7, -30),Math.toRadians(180.00))
                .addTemporalMarker( () -> {
                    // This marker runs two seconds into the trajectory
                    servoLeftOpen(1000);
                    // Run your action in here!
                    })
                .build();*/

        TrajectorySequence LeftPlacement = drive.trajectorySequenceBuilder(StartPose)
                .lineToSplineHeading(new Pose2d(11.34, -31.27, Math.toRadians(180.00)))
                .splineToConstantHeading(new Vector2d(7, -30), Math.toRadians(180.00))
                .addTemporalMarker( () -> {
                    servoLeftOpen(1000);
                })
                .splineTo(new Vector2d(53.5,-30), Math.toRadians(180))
                .addTemporalMarker( () -> {
                    arm(-1350,1000);
                    servoRightOpen(1000);
                 })
                .splineToConstantHeading(new Vector2d(10.76, -9.89), Math.toRadians(153.10))
                .addTemporalMarker( () -> {
                    arm(20,1000);
                })
                .build();
        TrajectorySequence RightPlacement = drive.trajectorySequenceBuilder(StartPose)
                .lineToSplineHeading(new Pose2d(28.53, -30.26, Math.toRadians(180.00)))
                .splineTo(new Vector2d(53.5,-44), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(10.76, -9.89), Math.toRadians(153.10))

                .build();
        TrajectorySequence MiddlePlacement = drive.trajectorySequenceBuilder(StartPose)
                .lineToSplineHeading(new Pose2d(11.34, -31.27, Math.toRadians(90.00)))
                .splineTo(new Vector2d(53.5,-37),Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(10.76, -9.89), Math.toRadians(153.10))
                .build();
        TrajectorySequence GoThruTruss = drive.trajectorySequenceBuilder(new Pose2d(10.76,-9.89, Math.toRadians(153.1)))
                .splineTo(new Vector2d(-26.50, -10.90), Math.toRadians(186.14))
                .lineToSplineHeading(new Pose2d(-63.62, -17.55, Math.toRadians(180.00)))
                .build();

        //This Trajectory pulls from stack 2, can change to 1 by copy pasting stack 1 into the drive trajectory builder
        TrajectorySequence PullFromStackAndPlace = drive.trajectorySequenceBuilder(stack2)
                .addTemporalMarker(  () -> {
                servoLeftCloseFl();
                servoRightCloseFl();
                servoLeftClose();
                servoRightClose();

                })
                .splineTo(new Vector2d(-26.50, -10.90), Math.toRadians(186.14))
                .splineToConstantHeading(new Vector2d(10.76, -9.89), Math.toRadians(153.10))
                .splineTo(new Vector2d(53.5,-44), Math.toRadians(180))
                .addTemporalMarker( () -> {
                    servoLeftOpenFl(1000);
                    servoRightOpenFl(1000);
                    arm(-1350,1000);
                    servoLeftOpen(1000);
                    servoRightOpen(1000);
                    arm(20,1000);
                    // Run your action in here!
                })
                .build();
        TrajectorySequence ParkLeft = drive.trajectorySequenceBuilder(new Pose2d(53.5,-44,Math.toRadians(180)))
                .splineToConstantHeading(new Vector2d(53.5, -11), Math.toRadians(180.00))
                .splineToConstantHeading(new Vector2d(10.76, -9.89), Math.toRadians(153.10))

                .build();
        while (!isStarted()) {
            location = redPropProcessor.getLocation();
            telemetry.update();
        }










        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Location", redPropProcessor.getLocation());
            telemetry.update();
        }
        drive.setPoseEstimate(StartPose);
        if (location == LEFT) {
            drive.followTrajectorySequence(LeftPlacement);
            drive.followTrajectorySequence(GoThruTruss);
            drive.followTrajectorySequence(PullFromStackAndPlace);


        } else if(location == MIDDLE){

        }
        else if (location == RIGHT) {

        }
    }







}