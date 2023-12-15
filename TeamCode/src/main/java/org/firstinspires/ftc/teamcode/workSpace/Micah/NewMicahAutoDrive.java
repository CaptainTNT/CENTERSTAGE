package org.firstinspires.ftc.teamcode.workSpace.Micah;

import static org.firstinspires.ftc.teamcode.workSpace.Micah.newAutonTest.driveMB;
import static org.firstinspires.ftc.teamcode.workSpace.Micah.newAutonTest.strafeRightMB;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "NewMicahAutoDrive")
public class NewMicahAutoDrive extends LinearOpMode{
    @Override
    public void runOpMode() {
        driveMB(1000, 0.4);

        strafeRightMB(1000, 0.4);
    }
}
