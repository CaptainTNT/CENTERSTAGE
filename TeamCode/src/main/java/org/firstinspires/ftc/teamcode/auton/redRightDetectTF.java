package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.Calabration.auto.Launchmotor;
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
import static org.firstinspires.ftc.teamcode.Calabration.auto.spinRight;
import static org.firstinspires.ftc.teamcode.Calabration.auto.strafeLeft;
import static org.firstinspires.ftc.teamcode.Calabration.auto.strafeRight;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous(name = "redRightDetectTF", group = "Red")
public class redRightDetectTF extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    private static final String TFOD_MODEL_ASSET = "redModel.tflite";
    private static final String[] LABELS = {
       "redProp",
    };
    private TfodProcessor tfod;
    private VisionPortal visionPortal;
    boolean Right = false;
    boolean Middle = false;

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
                Middle = false;
                Right = false;
                telemetry.addLine("Left");
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

            // Push telemetry to the Driver Station.
            telemetry.update();


        }

        visionPortal.close();

        waitForStart();

        if (opModeIsActive()) {

            if(Right){
                drive(-123, -0.4,3000);

                spinRight(920, 0.4,2000);

                drive(-620, -0.4,3000);

                strafeRight(1500, 0.4,1800);

                servoLeftOpen(3000);

                drive(-890, -0.4,3100);

                arm(-1450, 0.5,false,2000);

                servoRightOpen(2000);

                Reset();
                drive(200,-0.4,3100);

                strafeRight(1000, 0.4,3000);


                drive(-500, -0.4,3100);

                stop();
            } else if (Middle) {
                drive(-1950, -0.4,3000);

                servoLeftOpen(3000);

                drive(-300, -0.4,3000);

                spinRight(920, 0.4,2000);

                arm(-1200, 0.6,false,1000);

                drive(-1350, 0.4,2000);

                strafeLeft(6000, 0.4,1800);

                drive(-500, 0.4,1000);

                servoRightOpen(2000);

                Reset();

                strafeRight(1000, 0.4,3000);

                drive(-500, -0.4,3100);

                stop();

            } else {
                drive(-1150, -0.4,3000);

                spinRight(920, -0.4,2000);

                strafeRight(420, -0.4,2000);

                drive(280, -0.4,3000);

                servoLeftOpen(3000);

                drive(-271, -0.4,2000);

                arm(-1200, 0.6,false,1000);

                drive(-1460, 0.4,2000);

                servoRightOpen(2000);

                Reset();

                strafeRight(1000, 0.4,3000);

                drive(-500, -0.4,3100);


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
        Launchmotor = hardwareMap.get(DcMotor.class, "Launch Motor");
        Servo = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo1");
        Servo2 = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo2");

        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        Servo.setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE);
    }// end runOpMode()
}   // end class
