/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.auton.auto.Reset;
import static org.firstinspires.ftc.teamcode.auton.auto.arm;
import static org.firstinspires.ftc.teamcode.auton.auto.drive;
import static org.firstinspires.ftc.teamcode.auton.auto.servoLeftClose;
import static org.firstinspires.ftc.teamcode.auton.auto.servoLeftOpen;
import static org.firstinspires.ftc.teamcode.auton.auto.servoRightClose;
import static org.firstinspires.ftc.teamcode.auton.auto.servoRightOpen;
import static org.firstinspires.ftc.teamcode.auton.auto.spinLeft;
import static org.firstinspires.ftc.teamcode.auton.auto.spinRight;
import static org.firstinspires.ftc.teamcode.auton.auto.strafeLeft;
import static org.firstinspires.ftc.teamcode.auton.auto.strafeRight;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@Autonomous(name = "blueRightDetectTF", group = "Blue")
public class blueRightDetectTF extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "blueModel.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
    //private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/myCustomModel.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
       "blueProp",
    };

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
    boolean Right = false;
    boolean Middle = false;
    boolean Left = false;

    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load,
                // choose one of the following:
                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_ASSET)
                //.setModelFileName(TFOD_MODEL_FILE)

                // The following default settings are available to un-comment and edit as needed to
                // set parameters for custom models.
                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        //tfod.setMinResultConfidence(0.75f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
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
