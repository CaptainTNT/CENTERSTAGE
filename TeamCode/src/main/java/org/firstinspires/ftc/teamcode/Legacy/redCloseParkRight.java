//States the package the code is in
package org.firstinspires.ftc.teamcode.Legacy;

//Imports methods/classes

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

//States the name of the code in the Driver Hub and groups codes of same color together
@Autonomous(name = "redCloseParkRight", group = "RedNewClose")

//States the class and adds the methods from LinearOpMode
public class redCloseParkRight extends LinearOpMode {

    //Tells code code to use the webcam
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    //Calls the .tflite model from the assets folder and sets it to variable
    private static final String TFOD_MODEL_ASSET = "redModel.tflite";

    //Sets label names to variable
    private static final String[] LABELS = {
       "redProp",
    };

    //Creates tfod and vision portal variables
    private TfodProcessor tfod;
    private VisionPortal visionPortal;

    //Sets variables for detection
    boolean Right = false;
    boolean Middle = false;
    public ElapsedTime tfodTime = new ElapsedTime();



    //Creates method for the tfod to start in init
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                //Sets the model name for the builder
                .setModelAssetName(TFOD_MODEL_ASSET)

                //Sets label names for the builder
                .setModelLabels(LABELS)

                //Builds tfod
                .build();

        //Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        //Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        //Adds Tfod to builder
        builder.addProcessor(tfod);

        //Build the Vision Portal, using the above settings.
        visionPortal = builder.build();
    }


    //Creates telemetry + logic for detection
    private void telemetryTfod() {

        //Adds telemerty for if a object is detected
        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        //Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {

            //Gets parameters of bounding box for x and y position
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            //Adds telemetry for confidence of detection and label
            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);

            tfodTime.reset();
            tfodTime.startTime();
            //Code for position and size of the object (not currently using)
            //telemetry.addData("- Position", "%.0f / %.0f", x, y);
            //telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());

            //Logic for deciding what tape the object is on
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
        }
    }


    //Actual run code
    @Override
    public void runOpMode() {

        //Imports hardware map
        hardwareImports();

        //Resets all encoders
        Reset();

        //Closes both flippers
        servoLeftClose();
        servoRightClose();

        //Calls code for builder/tfod
        initTfod();

        //Loop for detection logic/telemetry will run until opmode is started
        while (!opModeIsActive()) {

            telemetryTfod();
            if (tfodTime.milliseconds() > 1000){
                Right = false;
                Middle = false;
                telemetry.addLine("Left");
            }

            //Push telemetry to the Driver Station.
            telemetry.update();
        }

        //Closes vision portal for cpu savings

        //Stops code until opmode is started
        waitForStart();

        //Resets timer for sleep
        timer.reset();


        //Runs when opmode is started
        if (opModeIsActive()) {


            visionPortal.close();
            //Will run if Left is the last object detected when opmode is started
            /*if(Right){

                //Sets the robot to drive for -123 ticks at 0.45 power for 1.0 seconds
                drive(-123, 0.45, 1000);

                //Sets the robot to spin right for 920 ticks at 0.45 power for 1.5 seconds
                spinRight(920, 0.45, 1500);

                //Sets the robot to drive for -620 ticks at 0.45 power for 1.6 seconds
                drive(-620, 0.45, 1600);

                //Sets the robot to strafe right for 1500 ticks at 0.45 power for 1.8 seconds
                strafeRight(1500, 0.45, 1800);

                //Sets the robot to open left servo for 1.5 seconds
                servoLeftOpen(1000);

                //Sets the robot lift to arm for -1450 ticks at 0.5 power for 2.0 seconds
                arm(-1300,  1000);

                //Sets the robot to drive for -840 ticks at 0.45 power for 3.1 seconds
                drive(-840, 0.45, 1000);

                //Sets the robot to strafe left for 600 ticks at 0.45 power for 1.8 seconds
                strafeLeft(600, 0.45, 1500);

                //Sets the robot to open right flipper for 2 seconds
                servoRightOpen(1000);

                //Sets the robot to drive for 200 ticks at 0.45 power for 3.1 seconds
                drive(200, 0.45, 1000);

                //Resets the encoders + lowers arm
                Reset();

                //Sets the robot to strafe right for 1600 ticks at 0.45 power for 3.0 seconds
                strafeLeft(1000, 0.45, 1300);

                //Sets the robot to drive for -500 ticks at 0.45 power for 3.1 seconds
                drive(-500, 0.45, 1000);

                //Stops the robot
                stop();

                //Will run if Middle is the last object detected when opmode is started
            } else if (Middle) {

                //Sets the robot to drive for -1950 ticks at 0.45 power for 3.0 seconds
                drive(-1950, 0.45, 3000);

                //Sets the robot to open left flipper for 1.5 seconds
                servoLeftOpen(1000);

                //Sets the robot to drive for -300 ticks at 0.45 power for 1.8 seconds
                drive(-300, 0.45, 1000);

                //Sets the robot to spin right for 920 ticks at 0.45 power for 2.0 seconds
                spinRight(920, 0.45, 2000);

                //Sets the robot to drive for -1200 ticks at 0.45 power for 2.0 seconds
                drive(-1200, 0.45, 2000);

                //Sets the robot to strafe left for 5000 ticks at 0.45 power for 1.5 seconds
                strafeLeft(2000, 0.45, 1500);

                //Sets the robot to lift arm for -1200 ticks at 0.6 power for 1.0 seconds
                arm(-1300,  2000);

                //Sets the robot to drive for -350 ticks at 0.45 power for 1.0 seconds
                drive(-370, 0.45, 1000);

                //Sets the robot to open right servo for 2000 ticks
                servoRightOpen(1000);

                drive(200, 0.45, 1000);

                //Resets the encoders + lowers arm
                Reset();

                //Sets the robot to strafe right for 1100 ticks at 0.45 power for 2.0 seconds
                strafeLeft(1500, 0.45, 2000);

                //Sets the robot to drive for -500 ticks at 0.45 power for 3.1 seconds
                drive(-500, 0.45, 1000);

                //Stops the robot
                stop();

                //Will run if Right is the last object detected when opmode is started
            } else {
                //Sets the robot to drive for -1150 ticks at 0.45 power for 3.0 seconds
                drive(-1150, 0.45, 3000);

                //Sets the robot to spin right for 920 ticks at 0.45 power for 2.0 seconds
                spinRight(910, 0.45, 2000);

                //Sets the robot to strafe right for 420 ticks at 0.45 power for 2.0 seconds
                strafeRight(300, 0.45, 1000);

                //Sets the robot to drive for 280 ticks at 0.45 power for 3.0 seconds
                drive(250, 0.45, 1000);

                //Sets the robot to open left flipper for 3 seconds
                servoLeftOpen(3000);

                //Sets the robot to drive for -271 ticks at 0.45 power for 2.0 seconds
                drive(-271, 0.45, 1000);

                //sets the robot to lift arm for -1200 ticks at 0.6 power for 1.0 seconds
                arm(-1300,  1000);

                //Sets the robot to drive for -1460 ticks at 0.45 power for 2.0 seconds
                drive(-1500, 0.45, 2000);

                // Sets the robot to open right flipper for 2 seconds
                servoRightOpen(1000);

                drive(200, 0.45, 1000);

                //Resets the encoders + lowers arm
                Reset();

                //Sets the robot to strafe right for 1000 ticks at 0.45 power for 3.0 seconds
                strafeLeft(2000, 0.45, 3000);

                //Sets the robot to drive for -500 ticks a6t 0.45 power for 3.1 seconds
                drive(-500, 0.45, 3100);

                //Stops the robot
                stop();


            }*/

            //Stops robot
            stop();
        }
    }

    //HardwareMap variables + reversing necessary motors
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
    }
}
