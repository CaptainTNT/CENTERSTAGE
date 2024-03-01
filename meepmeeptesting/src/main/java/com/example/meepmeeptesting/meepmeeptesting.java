package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class meepmeeptesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(47, 20, 238, Math.toRadians(60), 15.535)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, -63, Math.toRadians(270)))
                                .lineToSplineHeading(new Pose2d(11.34, -31.27, Math.toRadians(180.00)))
                                .splineToConstantHeading(new Vector2d(7, -30), Math.toRadians(180.00))
                                .waitSeconds(.25)
                                .lineToConstantHeading(new Vector2d(49.5,-30))
                                .waitSeconds(1)
                                .splineToConstantHeading(new Vector2d(10.76, -9.89), Math.toRadians(180.0))
                                .splineTo(new Vector2d(-26.50, -10.90), Math.toRadians(186.14))
                                .lineToSplineHeading(new Pose2d(-60.62, -17.55, Math.toRadians(180.00)))
                                .waitSeconds(1)
                                .lineTo(new Vector2d(-26.50, -10.90))
                                .splineToConstantHeading(new Vector2d(10.76, -9.89), Math.toRadians(180.0))
                               .lineToConstantHeading(new Vector2d(49.5,-30))
                                .waitSeconds(1)
                                .strafeRight(20)
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}