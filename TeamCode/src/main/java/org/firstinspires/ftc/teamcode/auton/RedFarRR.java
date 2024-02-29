package org.firstinspires.ftc.teamcode.auton;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.common.ParkingSpots;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class RedFarRR extends LinearOpMode{
    Pose2d stack1 = new Pose2d(-67.5,-30.1,Math.toRadians(180));
    Pose2d stack2 = new Pose2d(-67.5,-17,Math.toRadians(180));
    Pose2d stack3 = new Pose2d(-67.5,17,Math.toRadians(180));
    Pose2d stack4 = new Pose2d(-67.5,30.1,Math.toRadians(180));
    Vector2d REDLEFTAT = new Vector2d(53.5,-30);
    Vector2d REDMIDDLEAT = new Vector2d(53.5,-37);
    Vector2d REDRIGHTAT = new Vector2d(53.5,-44);
    Vector2d REDPARKLEFT = new Vector2d(66,-12);
    Vector2d REDPARKRIGHT = new Vector2d(66,-60);
    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

    Pose2d StartPose = new Pose2d(-36,-64,Math.toRadians(270));
    Trajectory onewhitepulled = drive.trajectoryBuilder(stack2,true)
            .lineToConstantHeading(new Vector2d(-7.58, -9.17))
            .lineToSplineHeading(new Pose2d(37.62, -10.33, Math.toRadians(180.00)))
            .build();

    Trajectory MiddlePURPPlacement = drive.trajectoryBuilder(StartPose)
           .splineToLinearHeading(new Pose2d(-36.32, -17.55, Math.toRadians(90.00)), Math.toRadians(90.00))
            .addDisplacementMarker(() -> {
                // This marker runs after the first splineTo()

                // Let go of purple
            })
           .lineToConstantHeading(new Vector2d(-62.32, -17.12))
           .build();
   Trajectory LeftPURPPlacement = drive.trajectoryBuilder(StartPose)
           .lineToSplineHeading(new Pose2d(-39.65, -30.98, Math.toRadians(180.73)))
           .splineToLinearHeading(new Pose2d(-41.09, -14.52, Math.toRadians(180.43)), Math.toRadians(143.43))
           .lineToLinearHeading(new Pose2d(-67.23, -16.54, Math.toRadians(180.00)))
           .build();
   Trajectory RightPURPPlacement = drive.trajectoryBuilder(StartPose)
           .splineToSplineHeading(new Pose2d(-33.87, -30.40, Math.toRadians(0.00)), Math.toRadians(91.73))
           .lineToConstantHeading(new Vector2d(-36.47, -15.09))
           .lineToLinearHeading(new Pose2d(-67.23, -16.54, Math.toRadians(180.00)))
           .build();
   Trajectory LeftYELLPlacement = drive.trajectoryBuilder(new Pose2d(37.62, -10.33, Math.toRadians(180.00)), true)
           .lineToConstantHeading(REDLEFTAT)
           .build();
    Trajectory MiddleYELLPlacement = drive.trajectoryBuilder(new Pose2d(37.62, -10.33, Math.toRadians(180.00)), true)
            .lineToConstantHeading(REDMIDDLEAT)
            .build();
    Trajectory RightYELLPlacement = drive.trajectoryBuilder(new Pose2d(37.62, -10.33, Math.toRadians(180.00)), true)
            .lineToConstantHeading(REDRIGHTAT)
            .build();
    @Override
    public void runOpMode() throws InterruptedException {
        drive.setPoseEstimate(StartPose);
    }
}