package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.functions.Timer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;


//Limit Switch:
    //1: DIO 1
    //2: DIO 2
public class Robot extends TimedRobot {
    private Joystick stickL, stickR;
    private WPI_TalonSRX drive1, drive2, drive3, drive4, actuator2, actuatorDrive2;
    private WPI_VictorSPX ramp, arm, actuator1, actuatorDrive1;
    private Servo door;
    private SpeedControllerGroup driveL, driveR, actuatorDrive;
    private DifferentialDrive myRobot;
    private Timer timer;
    private UsbCamera camera1, camera2;
    private CameraServer camServer;
    private double timePassed;
    private boolean actuatorEnabled;
    private Ultrasonic ultraSensor;
    private Gyro gyro;
    private Encoder encoder;
    private double distance, encoderVal;
    //private Potentiometer pot;
    //private AnalogInput ai;
    //private Counter counter1, counter2;
    

    private int currentArmPos;
    private boolean areArmsOn , isLiftOpen;

    public void robotInit(){
        stickL = new Joystick(0);
        stickR = new Joystick(1);
        areArmsOn = false;
        isLiftOpen = false;
        actuatorEnabled = false;
        currentArmPos = 1;
        
        //Drive Init
        drive1 = new WPI_TalonSRX(1);
        drive2 = new WPI_TalonSRX(2);
        drive3 = new WPI_TalonSRX(3);
        drive4 = new WPI_TalonSRX(4);
        driveL = new SpeedControllerGroup(drive1, drive2);
        driveR = new SpeedControllerGroup(drive3, drive4);
        
        //Motor Init
        ramp = new WPI_VictorSPX(7);
        arm = new WPI_VictorSPX(8);
        actuator1 = new WPI_VictorSPX(5);
        actuator2 = new WPI_TalonSRX(6);
        actuatorDrive1 = new WPI_VictorSPX(9);
        actuatorDrive2 = new WPI_TalonSRX(10);
        actuatorDrive = new SpeedControllerGroup(actuatorDrive1, actuatorDrive2);
        
        door = new Servo(0);
        
        //Input Init
        //analogInput = new AnalogInput(0);
        //pot = new AnalogPotentiometer(analogInput, 360, 30);
        timer = new Timer();
       // ultraSensor.setAutomaticMode(true);
        gyro = new AnalogGyro(1);
        //1pot = new AnalogPotentiometer(0, 360, 30);
        //ai = new AnalogInput(1);
       // pot = new AnalogPotentiometer(ai, 360, 30);
        encoder = new Encoder(5, 6, false, Encoder.EncodingType.k4X);
        encoderVal = encoder.getRaw();
    
        myRobot = new DifferentialDrive(driveL, driveR);
        myRobot.setExpiration(0.3);
        distance = encoder.getDistance();
        encoder.reset(); 
        //VARS
        //degrees = pot.get();

        //Cameras
        camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        camera1.setFPS(15);
        camera2.setFPS(15);
        camera1.setResolution(800,600);
        camera2.setResolution(800, 600);
        /*
        camera1.setFPS(25);
        camera2.setFPS(25);
        camera1.setResolution(800, 600);
        camera2.setResolution(800, 600);
        */
       // encoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
       //encoder.reset();
       //double count = encoder.get();
       //double distance = encoder.getDistance();
    }

    @Override
    public void robotPeriodic(){
    }

    @Override
    public void autonomousInit(){
        /*
        Drive forward 14.44 feet 
        Lower arm from pos 3 to pos 1 (get time)

        back up robot on half speed
        */

        //gyro.reset();
    //14.44 feet 
    }

    @Override
    public void autonomousPeriodic() {
        myRobot.arcadeDrive(0.5, -.17); 
    }

    @Override
    public void teleopPeriodic(){
        //John - "The robot will never fall over" 
        myRobot.tankDrive(-stickL.getY(), -stickR.getY());
        ramp();
        arms();
        lifts();
        door(); 
        liftDrive();
        System.out.println("Encoder: " + distance);
        System.out.println("Encoder Raw: " + encoderVal);

        //System.out.println("Encoder: " + encoder1.getDistance());
        //System.out.println("Per Pulse: " + encoder1.getDistancePerPulse());
        //System.out.println("Encoder: " + encoder.getDistance());
        //System.out.println("Degrees: " + degrees);
        //System.out.println("Voltage: " + ai.getVoltage());
        
        //Arm Override
        if(stickL.getRawButton(6)){
            arm.set(5);
        } else if(stickL.getRawButton(7)){
            arm.set(-5); 
        } else {
            arm.set(0);
        }
    }

    /*

    Position 1 to 3: button 1 1.02 seconds
    Postion 3 to 2: button 2 .33 seconds
    Postion 2 to 1: button 3 .86 seconds
      
    */
    @Override
    public void testPeriodic(){

    }

    public void liftDrive() {
/*
        if(stickL.getRawButton(7)) {
            actuatorDrive1.set(3);
            actuatorDrive2.set(-3);
        } else if(stickL.getRawButton(8)) {
            actuatorDrive1.set(-3);
            actuatorDrive2.set(3);
        } else {
            actuatorDrive.set(0);
        }
*/
    }

    public void ramp(){
        if(stickR.getRawButton(6)){
            ramp.set(-0.5);
        }else if(stickR.getRawButton(4)){
            ramp.set(0.5);
        } else {
            ramp.set(0);
        }
    }
    public void arms(){
        /*
        if(stickL.getRawButton(3) && !areArmsOn && currentArmPos == 2){
     
            areArmsOn = true;
            arm.set(10);
            Timer.delay(0.06);
            arm.set(0);
            areArmsOn = false;
            currentArmPos = 3;
        } else if(stickL.getRawButton(4) && !areArmsOn && currentArmPos == 1){
         
            areArmsOn = true; 
            arm.set(10);
            Timer.delay(0.47);
            arm.set(0);
            areArmsOn = false;
            currentArmPos = 2;
        } else if(stickL.getRawButton(2) && !areArmsOn && currentArmPos == 2){   
     
            areArmsOn = true;    
            arm.set(-10);
            Timer.delay(0.47);
            arm.set(0);
            areArmsOn b= false;
            currentArmPos = 1;
        } else if(stickL.getRawButton(5) && !areArmsOn && currentArmPos == 3){
            areArmsOn = true;
            arm.set(-10);
            Timer.delay(0.06);
            arm.set(0);
            areArmsOn = false;

            currentArmPos = 2;
        } else {
            arm.set(0);
        }
        */
        if(stickL.getRawButton(4) && currentArmPos == 1){
            arm.set(5);
            Timer.delay(.44);
            arm.set(0);
            currentArmPos = 2;
        } else if(stickL.getRawButton(3) && currentArmPos == 2){
            arm.set(5);
            Timer.delay(.15);
            arm.set(0);
            currentArmPos = 3;
        } else if(stickL.getRawButton(5) && currentArmPos == 3){
            arm.set(-5);
            Timer.delay(.15);
            arm.set(0);
            currentArmPos = 2;
        } else if(stickL.getRawButton(2) && currentArmPos == 2){
            arm.set(-5);
            Timer.delay(.41);
            arm.set(0);
            currentArmPos = 1;
        }
    }
    
    public void lifts(){
        /*
        if(isSwitch1Set()){
            ramp.set(0);
        }else if(isSwitch2Set()){
            ramp.set(0);
        }
        */

        if(stickR.getRawButton(5) && !isLiftOpen){
            actuator1.set(5);
            actuator2.set(5);
            isLiftOpen = true;
        }
         else if(stickR.getRawButton(3) && !isLiftOpen){
            actuator1.set(-3);
            actuator2.set(-3.5);
            isLiftOpen = true;
         } else if(stickR.getRawButton(9) && !isLiftOpen){
            actuator1.set(3);
         } else if(stickR.getRawButton(10) && !isLiftOpen){
            actuator2.set(3);
         } else if(stickR.getRawButton(11) && !isLiftOpen){
            actuator1.set(-3);
        } else if(stickR.getRawButton(12) && !isLiftOpen){
            actuator2.set(-3);
        } else {
            isLiftOpen = false;
            actuator1.set(0);
            actuator2.set(0);
        }
    }

    public void door(){
        if(stickR.getRawButton(2)) {
            door.set(0.46);  
        } else {
            door.set(.952);
        }        
    }
} 