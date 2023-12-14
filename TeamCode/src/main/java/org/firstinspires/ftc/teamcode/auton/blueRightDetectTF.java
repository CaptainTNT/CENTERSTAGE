package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.Calabration.auto.Reset;
import static org.firstinspires.ftc.teamcode.Calabration.auto.arm;
import static org.firstinspires.ftc.teamcode.Calabration.auto.drive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.servoLeftClose;
import static org.firstinspires.ftc.teamcode.Calabration.auto.servoLeftOpen;
import static org.firstinspires.ftc.teamcode.Calabration.auto.servoRightClose;
import static org.firstinspires.ftc.teamcode.Calabration.auto.servoRightOpen;
import static org.firstinspires.ftc.teamcode.Calabration.auto.spinLeft;
import static org.firstinspires.ftc.teamcode.Calabration.auto.spinRight;
import static org.firstinspires.ftc.teamcode.Calabration.auto.strafeLeft;
import static org.firstinspires.ftc.teamcode.Calabration.auto.strafeRight;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Calabration.hardwareDrive;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous(name = "blueRightDetectTF", group = "Blue")
public class blueRightDetectTF extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    private static final String TFOD_MODEL_ASSET = "blueModel.tflite";

    private static final String[] LABELS = {
       "blueProp",
    };

    private TfodProcessor tfod;

    private VisionPortal visionPortal;
    boolean Right = false;
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


        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();


    }   // end method initTfod()


    private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());
        telemetry.addLine("Left");

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
            if (x>=330){
                Right = true;
                Middle = false;
                telemetry.addLine("Right");
            } else if ( 330 >= x) {
                Middle = true;
                Right = false;
                telemetry.addLine("Middle");
            }else {
                telemetry.addLine("Left");
                Middle = false;
                Right = false;
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
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
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

            if(Right){
                drive(-124, -0.4, 500);

                spinLeft(900, 0.4, 2000);

                strafeLeft(1500, 0.4, 1800);

                drive(260, 0.4, 800);

                servoLeftOpen(550);

                drive(-300, -0.4,1000);

                strafeRight(1410, 0.4,2000);

                spinLeft(900, 0.4,2000);

                strafeRight(4000, -0.6, 4700);

                spinLeft(900, -0.4,1000);

                strafeRight(600, 0.4,2000);

                arm(-1200, 0.5, false, 2500);

                drive(-700, -0.4,1000);

                servoRightOpen(1000);

                drive(200, -0.4, 1000);
                Reset();

                strafeLeft(900, -0.4, 2000);

                drive(-500, -0.4, 1000);


                stop();
            } else if (Middle) {
                drive(-124, -0.4, 500);

                spinLeft(1800, 0.4,3000);


                drive(1250, 0.4, 2000);

                servoLeftOpen(500);

                drive(-1300, 0.4,2000);

                strafeLeft(4000, 0.6,4000);

                spinRight(900, 0.4,2000);

                strafeLeft(800, 0.4,2800);

                arm(-1200, 0.5,false,2500);

                drive(-700, -0.4,1000);

                servoRightOpen(1000);

                drive(200, -0.4,1000);
                Reset();

                strafeRight(1000, -0.4,2000);

                drive(-500, -0.4,1000);


                stop();
            } else {
                drive(-123, -0.4,1000);

                spinRight(910, 0.4,2000);

                strafeRight(1500, 0.4,1800);

                drive(300, 0.4,1000);

                servoLeftOpen(1000);

                drive(-200, -0.4,500);

                strafeLeft(1450, 0.4,1800);

                spinLeft(950, 0.4,2000);

                strafeRight(4000, 0.6,4000);

                drive(-1200, -0.4,2000);

                spinLeft(930, 0.4,2000);

                arm(-1200, 0.5,false, 2000);

                drive(-1000, 0.4,1000);

                servoRightOpen(2000);

                drive(200, -0.4,1000);
                Reset();

                strafeRight(1500, 0.4,3000);

                drive(-800, -0.4,1500);


                stop();
            }
            stop();
        }

        // Save more CPU resources when camera is no longer needed.
    }   // end runOpMode()
}   // end class
