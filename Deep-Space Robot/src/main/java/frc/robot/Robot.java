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
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.MotorSafety;

public class Robot extends TimedRobot {
    private Joystick stickL, stickR;
    private WPI_TalonSRX drive1, drive2, drive3, drive4, actuator2;
    private WPI_VictorSPX ramp, arm, actuator1, actuatorDrive;
    private Servo door;
    private SpeedControllerGroup driveL, driveR;
    private DifferentialDrive myRobot;
    private Timer timer;
    private AnalogInput analogInput;
    private UsbCamera camera1, camera2;
    private Potentiometer pot;
    private CameraServer camServer;
    private double timePassed;

    private int currentArmPos;
    private boolean areArmsOn , isLiftOpen;
    private double currentVoltage;

    public void robotInit(){
        stickL = new Joystick(0);
        stickR = new Joystick(1);

        areArmsOn = false;
        isLiftOpen = false;

        drive1 = new WPI_TalonSRX(1);
        drive2 = new WPI_TalonSRX(2);
        drive3 = new WPI_TalonSRX(3);
        drive4 = new WPI_TalonSRX(4);
        ramp = new WPI_VictorSPX(7);
        arm = new WPI_VictorSPX(8);
        actuator1 = new WPI_VictorSPX(5);
        actuator2 = new WPI_TalonSRX(6);
        actuatorDrive = new WPI_VictorSPX(9);
        door = new Servo(0);
        driveL = new SpeedControllerGroup(drive1, drive2);
        driveR = new SpeedControllerGroup(drive3, drive4);
        analogInput = new AnalogInput(0);
        pot = new AnalogPotentiometer(analogInput, 360, 30);
        timer = new Timer();
        myRobot = new DifferentialDrive(driveL, driveR);
        timer = new Timer();

        currentArmPos = 1;
        
        currentVoltage = analogInput.getVoltage();

        /*
        drive1.setSafetyEnabled(false);
        drive2.setSafetyEnabled(false);
        drive3.setSafetyEnabled(false);
        drive4.setSafetyEnabled(false);
        */

        camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera2 = CameraServer.getInstance().startAutomaticCapture(1);

        camera1.setFPS(25);
        camera2.setFPS(25);
        camera1.setResolution(800, 600);
        camera2.setResolution(800, 600);

        timePassed = timer.get();
    }

    @Override
    public void robotPeriodic(){
    }

    @Override
    public void autonomousInit(){
    }

    @Override
    public void teleopPeriodic(){
        myRobot.tankDrive(-stickL.getY(), -stickR.getY());
        ramp();
        arms();
        lifts();
        door(); 
        liftDrive();

        if(stickL.getRawButton(6)){
            arm.set(2);
        } else if(stickL.getRawButton(7)){
            arm.set(-2);
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
        if(stickR.getRawButton(1)) {
            actuatorDrive.set(1);
        } else {
            actuatorDrive.set(0);
        }

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
        if(stickL.getRawButton(3) && !areArmsOn && currentArmPos == 2){
            areArmsOn = true;
            arm.set(10);
            timer.start();
            if(timePassed > 0.06){
                arm.set(0);
            }
            timer.stop();
           // Timer.delay(0.06);
            //arm.set(0);
            areArmsOn = false;
            currentArmPos = 3;
        } else if(stickL.getRawButton(4) && !areArmsOn && currentArmPos == 1){
            areArmsOn = true; 
            arm.set(10);
            timer.start();    
            //.delay(0.47);
            //arm.set(0);
            if(timePassed > 0.47){
                arm.set(0);
            }
            timer.stop();
            areArmsOn = false;
            currentArmPos = 2;
        } else if(stickL.getRawButton(2) && !areArmsOn && currentArmPos == 2){        
            areArmsOn = true;    
            arm.set(-10);
            //Timer.delay(0.47);
            timer.start();
            if(timePassed > 0.47){
                arm.set(0);
            }
            areArmsOn = false;
            currentArmPos = 1;
        } else if(stickL.getRawButton(5) && !areArmsOn && currentArmPos == 3){
            areArmsOn = true;
            arm.set(-10);
            timer.start();
            if(timePassed > 0.06){
                arm.set(0);
            }
            //Timer.delay(0.06);
            //arm.set(0);
            areArmsOn = false;
            currentArmPos = 2;
        } else {
            arm.set(0);
        }
      
    }

    public void lifts(){
        if(stickR.getRawButton(5) && !isLiftOpen && analogInput.getVoltage() < 5){
            actuator1.set(3);
            actuator2.set(3);
            isLiftOpen = true;
        }
         else if(stickR.getRawButton(3) && !isLiftOpen && analogInput.getVoltage() > 0){
            actuator1.set(-3);
            actuator2.set(-3);
            isLiftOpen = true;
        } else {
            isLiftOpen = false;
            actuator1.set(0);
            actuator2.set(0);
        }
    }

    public void voltageCheck(){
        if(currentVoltage == 5 || currentVoltage == 0){
            actuator1.set(0);
            actuator2.set(0);
        }
    }

    public void door(){
        if(stickR.getRawButton(2)) {
            door.set(0.5);  
        } else {
            door.set(.96);
        }        
    }
}