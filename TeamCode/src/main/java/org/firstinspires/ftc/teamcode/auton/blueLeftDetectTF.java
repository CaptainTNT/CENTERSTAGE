//States the package the code is in
package org.firstinspires.ftc.teamcode.auton;

//Imports methods/classes
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

//States the name of the code in the Driver Hub and groups codes of same color together
@Autonomous(name = "blueLeftDetectTF", group = "Blue")
//States the class and adds the methods from LinearOpMode
public class blueLeftDetectTF extends LinearOpMode {

    //Tells code code to use the webcam
    private static final boolean USE_WEBCAM = true;

    //Calls the .tflite model from the assets folder and sets it to variable
    private static final String TFOD_MODEL_ASSET = "blueModel.tflite";

    //Sets label names to variable
    private static final String[] LABELS = {
       "blueProp",
    };

    //Creates tfod and vision portal variables
    private TfodProcessor tfod;
    private VisionPortal visionPortal;

    //Sets variables for detection
    boolean Middle = false;
    boolean Left = false;


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
        telemetry.addLine("Right");

        //Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            //Adds telemetry for confidence of detection and label
            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);

            //Code for position and size of the object (not currently using
            //telemetry.addData("- Position", "%.0f / %.0f", x, y);
            //telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());

            //Logic for deciding what tape the object is on
            if (x<=330){
                Left = true;
                Middle = false;
                telemetry.addLine("Left");
            } else if ( 330 <= x) {
                Left = false;
                Middle = true;
                telemetry.addLine("Middle");
            }else {
                Left = false;
                Middle = false;
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

            // Push telemetry to the Driver Station
            telemetry.update();
        }

        //Closes vision portal for cpu savings
        visionPortal.close();

        //Stops code until opmode is started
        waitForStart();

        //Runs when opmode is started
        if (opModeIsActive()) {

            //Will run if Left is the last object detected when opmode is started
            if(Left){

                // Sets the robot to drive for -123 ticks at 0.4 power for 1.0 seconds
                drive(-123, 0.4, 1000);

                // Sets the robot to spinLeft for 920 ticks at 0.4 power for 1.0 seconds
                spinLeft(920, 0.4, 1000);

                // Sets the robot to drive for -620 ticks at 0.4 power for 1.0 seconds
                drive(-620, 0.4, 1000);

                // Sets the robot to strafeLeft for 1500 ticks at 0.4 power for 1.8 seconds
                strafeLeft(1500, 0.4, 1800);

                // Sets the robot to servoLeftOpen for 1000 ticks
                servoLeftOpen(1000);

                // Sets the robot to drive for -890 ticks at 0.4 power for 1.2 seconds
                drive(-890, 0.4, 1200);

                // Sets the robot to arm for -1200 ticks at 0.5 power for 2.0 seconds
                arm(-1200, 0.5, false, 2000);

                // Sets the robot to strafeRight for 450 ticks at 0.4 power for 1.0 seconds
                strafeRight(450, 0.4, 1000);

                // Sets the robot to servoRightOpen for 2000 ticks
                servoRightOpen(2000);

                // Sets the robot to drive for 200 ticks at 0.4 power for 1.0 seconds
                drive(200, 0.4, 1000);

                // Resets the robot
                Reset();

                // Sets the robot to strafeRight for 1000 ticks at 0.4 power for 3.0 seconds
                strafeRight(1000, 0.4, 3000);

                // Sets the robot to drive for -500 ticks at 0.4 power for 1.0 seconds
                drive(-500, 0.4, 1000);

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
