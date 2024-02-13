package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.Calabration.auto.Launchmotor;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Launchmotor2;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Reset;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Servo;
import static org.firstinspires.ftc.teamcode.Calabration.auto.Servo2;
import static org.firstinspires.ftc.teamcode.Calabration.auto.arm;
import static org.firstinspires.ftc.teamcode.Calabration.auto.backLeftDrive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.backRightDrive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.drive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.leftDrive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.rightDrive;
import static org.firstinspires.ftc.teamcode.Calabration.auto.servoLeftClose;
import static org.firstinspires.ftc.teamcode.Calabration.auto.servoLeftOpen;
import static org.firstinspires.ftc.teamcode.Calabration.auto.servoRightClose;
import static org.firstinspires.ftc.teamcode.Calabration.auto.servoRightOpen;
import static org.firstinspires.ftc.teamcode.Calabration.auto.spinLeft;
import static org.firstinspires.ftc.teamcode.Calabration.auto.spinRight;
import static org.firstinspires.ftc.teamcode.Calabration.auto.strafeLeft;
import static org.firstinspires.ftc.teamcode.Calabration.auto.strafeRight;
import static org.firstinspires.ftc.teamcode.Calabration.auto.timer;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous(name = "blueFarParkLeft", group = "BlueNewFar")
public class blueFarParkLeft extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    private static final String TFOD_MODEL_ASSET = "blueModel.tflite";

    private static final String[] LABELS = {
       "blueProp",
    };

    private TfodProcessor tfod;

    private VisionPortal visionPortal;
    boolean Right = false;
    boolean Middle = false;
    public ElapsedTime tfodTime = new ElapsedTime();


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

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            tfodTime.reset();
            tfodTime.startTime();

            if (x>=330 && tfodTime.milliseconds() < 1000){
                Right = true;
                Middle = false;
                telemetry.addLine("Right");
                tfodTime.reset();
            } else if ( 330 >= x && tfodTime.milliseconds() < 1000) {
                Middle = true;
                Right = false;
                telemetry.addLine("Middle");
                tfodTime.reset();
            }

        }   // end for() loop

    }   // end method telemetryTfod()


    @Override
    public void runOpMode() {
        hardwareImports();
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
            if (tfodTime.milliseconds() > 1000){
                Right = false;
                Middle = false;
                telemetry.addLine("Left");
            }

            // Push telemetry to the Driver Station.
            telemetry.update();


        }


        waitForStart();
        timer.reset();


        if (opModeIsActive()) {

            visionPortal.close();
            if(Right){
                drive(-124, -0.4, 500); // ADD MORE COMMENTS

                spinLeft(900, 0.4, 2000);

                strafeLeft(1500, 0.4, 1800);

                servoLeftOpen(550); //Drops the purple pixel on the spike mark

                drive(-200, -0.4,1000);

                strafeLeft(1000, 0.4,2000);

                spinLeft(60, 0.4, 500); //Recorrects rotational error

                drive(-3500, 0.4,5000); // Drives towards the backboard

                strafeRight(700, -0.6, 1500); //Aligns with the backboard to perpare to place the pixel

                //spinRight(900, -0.4,1000);

                //strafeLeft(600, 0.4,2000);

                arm(-1300, 0.5, false, 2500);

                drive(-330, -0.4,1000);

                servoRightOpen(1000);

                drive(200, -0.4, 1000);
                Reset();

                strafeRight(2000, -0.4, 2500);

                drive(-500, -0.4, 1000);


                stop();
            } else if (Middle) {

                drive(-2000, 0.4, 3000);

                servoLeftOpen(500);

                drive(-300, 0.4,2000);

                spinLeft(900, 0.4,2000);

                drive(-3500, 0.4, 4000);

                strafeRight(1000, 0.4, 2000);

                arm(-1300, 0.5,false,2500);

                drive(-400, -0.4,1000);

                servoRightOpen(1000);

                drive(200, -0.4,1000);

                Reset();

                strafeRight(1500, -0.4,2000);

                drive(-500, -0.4,1000);

                stop();
            } else {

                drive(-124, -0.4, 500); // ADD MORE COMMENTS

                spinRight(900, 0.4, 2000);

                strafeRight(1500, 0.4, 1800);

                drive(240, -0.4,1000);

                servoLeftOpen(550); //Drops the purple pixel on the spike mark

                drive(-200, -0.4,1000);

                strafeRight(1100, 0.4,2000);

                spinLeft(1810, 0.4, 5000); //Recorrects rotational error

                drive(-3500, 0.4,5000); // Drives towards the backboard

                strafeRight(1100, -0.4, 2000); //Aligns with the backboard to perpare to place the pixel

                //spinRight(900, -0.4,1000);

                //strafeLeft(600, 0.4,2000);

                arm(-1300, 0.5, false, 2500);

                drive(-330, -0.4,1000);

                servoRightOpen(1000);

                drive(200, -0.4, 1000);
                Reset();

                strafeRight(1000, -0.4, 2000);

                drive(-500, -0.4, 1000);

                stop();
            }


            stop();
        }

        // Save more CPU resources when camera is no longer needed.
    }
    public void hardwareImports() {

        leftDrive = hardwareMap.get(DcMotor.class, "front Left");
        rightDrive = hardwareMap.get(DcMotor.class, "front Right");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back Left");
        backRightDrive = hardwareMap.get(DcMotor.class, "back Right");
        Launchmotor = hardwareMap.get(DcMotorEx.class, "Launch Motor");
        Launchmotor2 = hardwareMap.get(DcMotorEx.class, "Launch Motor 2");
        Servo = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo1");
        Servo2 = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo2");

        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        Servo.setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE);
        Launchmotor2.setDirection(DcMotorEx.Direction.REVERSE);
    }// end runOpMode()
}   // end class
