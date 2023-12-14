package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.auton.auto.Reset;
import static org.firstinspires.ftc.teamcode.auton.auto.Servo;
import static org.firstinspires.ftc.teamcode.auton.auto.Servo2;
import static org.firstinspires.ftc.teamcode.auton.auto.arm;
import static org.firstinspires.ftc.teamcode.auton.auto.backLeftDrive;
import static org.firstinspires.ftc.teamcode.auton.auto.backRightDrive;
import static org.firstinspires.ftc.teamcode.auton.auto.drive;
import static org.firstinspires.ftc.teamcode.auton.auto.driveReset;
import static org.firstinspires.ftc.teamcode.auton.auto.leftDrive;
import static org.firstinspires.ftc.teamcode.auton.auto.rightDrive;
import static org.firstinspires.ftc.teamcode.auton.auto.servoLeftClose;
import static org.firstinspires.ftc.teamcode.auton.auto.servoLeftOpen;
import static org.firstinspires.ftc.teamcode.auton.auto.servoRightClose;
import static org.firstinspires.ftc.teamcode.auton.auto.servoRightOpen;
import static org.firstinspires.ftc.teamcode.auton.auto.spinLeft;
import static org.firstinspires.ftc.teamcode.auton.auto.spinRight;
import static org.firstinspires.ftc.teamcode.auton.auto.stopArm;
import static org.firstinspires.ftc.teamcode.auton.auto.stopDrive;
import static org.firstinspires.ftc.teamcode.auton.auto.strafeLeft;
import static org.firstinspires.ftc.teamcode.auton.auto.strafeRight;
import org.firstinspires.ftc.teamcode.auton.hardwareDrive.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import java.util.List;

@Autonomous(name = "blueLeftDetectTF", group = "Blue")
public class blueLeftDetectTF extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;
    private static final String TFOD_MODEL_ASSET = "blueModel.tflite";
    private static final String[] LABELS = {
       "blueProp",
    };

    private TfodProcessor tfod;
    private VisionPortal visionPortal;
    boolean Middle = false;
    boolean Left = false;

    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                .setModelAssetName(TFOD_MODEL_ASSET)

                .setModelLabels(LABELS)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

    }   // end method initTfod()

    private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());
        telemetry.addLine("Right");

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
            if (x<=330){
                Left = true;
                Middle = false;
                telemetry.addLine("Left");
            } else if ( 330 <= x) {
                Left = false;
                Middle = true;
                telemetry.addLine("Middle");
            }else {
                telemetry.addLine("Right");
                Left = false;
                Middle = false;
            }

        }   // end for() loop

    }   // end method telemetryTfod()


    @Override
    public void runOpMode() {
        new hardwareDrive();
        Reset();
        servoLeftClose();
        servoRightClose();

        initTfod();

        // Wait for the DS start button to be touched.
        telemetry.addLine("WAIT NATHAN DONT START, WAIT" );

        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        while (!opModeIsActive()) {

            telemetryTfod();

            // Push telemetry to the Driver Station.
            telemetry.update();


        }

        visionPortal.close();

        waitForStart();

        if (opModeIsActive()) {

            if(Left){
                drive(-123, -0.4, 1000);

                spinLeft(920, 0.4,1000);

                drive(-620, -0.4, 1000);

                strafeLeft(1500, 0.4,1800);

                servoLeftOpen(1000);

                drive(-890, -0.4, 1200);

                arm(-1200, 0.5,false,2000);

                strafeRight(450, -0.4, 1000);

                servoRightOpen(2000);

                drive(200, -0.4, 1000);
                Reset();

                strafeRight(1000, 0.4, 3000);

                drive(-500, -0.4, 1000);


                stop();

            } else if (Middle) {
                drive(-1950, -0.4, 1000);

                servoLeftOpen(2000);

                drive(-300, -0.4, 1000);

                spinLeft(920, 0.4, 2000);

                arm(-1200, 0.6, false, 1000);

                drive(-1350, 0.4, 2000);

                strafeRight(5450, 0.4, 4000);

                drive(-400, 0.4, 4000);

                servoRightOpen(2000);

                strafeRight(1000, 0.4,2000);
                Reset();

                drive(-500, -0.4,100);

                stop();

            } else {
                drive(-1150, -0.4,3000);

                spinLeft(920, -0.4, 2000);

                strafeLeft(420, -0.4, 2000);

                drive(280, -0.4, 3000);

                servoLeftOpen(3000);

                drive(-271, -0.4, 2000);

                arm(-1200, 0.6, false, 2000);

                drive(-1460, 0.4, 2000);

                servoRightOpen(2000);

                strafeRight(1000, 0.4,3000);
                Reset();

                drive(-500, -0.4,3100);


                stop();


            }
            stop();
        }

        // Save more CPU resources when camera is no longer needed.
    }   // end runOpMode()
}   // end class