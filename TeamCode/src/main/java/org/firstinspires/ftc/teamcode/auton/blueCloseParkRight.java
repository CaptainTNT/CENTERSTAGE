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
import static org.firstinspires.ftc.teamcode.Calabration.auto.spinLeft;
import static org.firstinspires.ftc.teamcode.Calabration.auto.strafeLeft;
import static org.firstinspires.ftc.teamcode.Calabration.auto.strafeRight;
import static org.firstinspires.ftc.teamcode.Calabration.auto.timer;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous(name = "blueCloseParkRight", group = "BlueNewClose")
public class blueCloseParkRight extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;
    private static final String TFOD_MODEL_ASSET = "blueModel.tflite";
    private static final String[] LABELS = {
       "blueProp",
    };

    private TfodProcessor tfod;
    private VisionPortal visionPortal;
    boolean Middle = false;
    boolean Left = false;
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

            if (x<=330 && tfodTime.milliseconds() < 1000){
                Left = true;
                Middle = false;
                telemetry.addLine("Left");
                tfodTime.reset();
            } else if ( 330 <= x && tfodTime.milliseconds() < 1000) {
                Left = false;
                Middle = true;
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
        telemetry.addLine("WAIT NATHAN DONT START, WAIT" );

        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        while (!opModeIsActive()) {

            telemetryTfod();
            if (tfodTime.milliseconds() > 1000){
                Left = false;
                Middle = false;
                telemetry.addLine("Right");
            }

            // Push telemetry to the Driver Station.
            telemetry.update();


        }


        waitForStart();

        timer.reset();

        if (opModeIsActive()) {


            visionPortal.close();
            if(Left){
                drive(-123, 0.4, 1000);

                //Sets the robot to spin Left for 920 ticks at 0.4 power for 1.5 seconds
                spinLeft(900, 0.4, 1500);

                //Sets the robot to drive for -620 ticks at 0.4 power for 1.6 seconds
                drive(-620, 0.4, 1600);

                //Sets the robot to strafe Left for 1500 ticks at 0.4 power for 1.8 seconds
                strafeLeft(1500, 0.4, 1800);

                //Sets the robot to open left servo for 1.5 seconds
                servoLeftOpen(1500);

                //Sets the robot lift to arm for -1450 ticks at 0.5 power for 2.0 seconds
                arm(-1400, 0.5, false, 2000);

                //Sets the robot to drive for -840 ticks at 0.4 power for 3.1 seconds
                drive(- 840, 0.4, 3100);

                //Sets the robot to strafe Right for 600 ticks at 0.4 power for 1.8 seconds
                strafeRight(400, 0.4, 1800);

                //Sets the robot to open right flipper for 2 seconds
                servoRightOpen(2000);

                //Sets the robot to drive for 200 ticks at 0.4 power for 3.1 seconds
                drive(200, 0.4, 3100);

                //Resets the encoders + lowers arm
                Reset();

                //Sets the robot to strafe Left for 1600 ticks at 0.4 power for 3.0 seconds
                strafeLeft(1000, 0.4, 3000);

                //Sets the robot to drive for -500 ticks at 0.4 power for 3.1 seconds
                drive(-500, 0.4, 3100);

                //Stops the robot
                stop();

            } else if (Middle) {
                //Sets the robot to drive for -1950 ticks at 0.4 power for 3.0 seconds
                drive(-1950, 0.4, 3000);

                //Sets the robot to open left flipper for 1.5 seconds
                servoLeftOpen(1500);

                //Sets the robot to drive for -300 ticks at 0.4 power for 1.8 seconds
                drive(-300, 0.4, 1800);

                //Sets the robot to spin Left for 920 ticks at 0.4 power for 2.0 seconds
                spinLeft(920, 0.4, 2000);

                //Sets the robot to lift arm for -1200 ticks at 0.6 power for 1.0 seconds
                arm(-1400, 0.6, false, 1000);

                //Sets the robot to drive for -1200 ticks at 0.4 power for 2.0 seconds
                drive(-1200, 0.4, 2000);

                //Sets the robot to strafe Right for 5000 ticks at 0.4 power for 1.5 seconds
                strafeRight(890, 0.4, 1500);

                //Sets the robot to drive for -350 ticks at 0.4 power for 1.0 seconds
                drive(-350, 0.4, 1000);

                //Sets the robot to open right servo for 2000 ticks
                servoRightOpen(2000);

                //Resets the encoders + lowers arm
                Reset();

                //Sets the robot to strafe Left for 1100 ticks at 0.4 power for 2.0 seconds
                strafeLeft(1500, 0.4, 3000);

                //Sets the robot to drive for -500 ticks at 0.4 power for 3.1 seconds
                drive(-500, 0.4, 3100);

                //Stops the robot
                stop();

            } else {
                //Sets the robot to drive for -1150 ticks at 0.4 power for 3.0 seconds
                drive(-1150, 0.4, 3000);

                //Sets the robot to spin right for 920 ticks at 0.4 power for 2.0 seconds
                spinLeft(920, 0.4, 2000);

                //Sets the robot to strafe right for 420 ticks at 0.4 power for 2.0 seconds
                strafeLeft(420, 0.4, 2000);

                //Sets the robot to drive for 280 ticks at 0.4 power for 3.0 seconds
                drive(280, 0.4, 3000);

                //Sets the robot to open left flipper for 3 seconds
                servoLeftOpen(3000);

                //Sets the robot to drive for -271 ticks at 0.4 power for 2.0 seconds
                drive(-271, 0.4, 2000);

                //sets the robot to lift arm for -1200 ticks at 0.6 power for 1.0 seconds
                arm(-1400, 0.6, false, 1000);

                //Sets the robot to drive for -1460 ticks at 0.4 power for 2.0 seconds
                drive(-1440, 0.4, 2000);

                strafeLeft(250, 0.4, 1000);

                // Sets the robot to open right flipper for 2 seconds
                servoRightOpen(2000);

                drive(200,0.4,1000);

                //Resets the encoders + lowers arm
                Reset();

                //Sets the robot to strafe right for 1000 ticks at 0.4 power for 3.0 seconds
                strafeLeft(1600, 0.4, 3000);

                //Sets the robot to drive for -500 ticks at 0.4 power for 3.1 seconds
                drive(-500, 0.4, 3100);

                //Stops the robot
                stop();


            }
            stop();
        }
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
    }
}   // end class
