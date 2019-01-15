package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.functions.Timer;

@SuppressWarnings("deprecation")

public class Robot extends IterativeRobot {
    private Joystick stickL, stickR;
    private WPI_TalonSRX drive1, drive2, drive3, drive4;
    private WPI_VictorSPX ramp, arm, actuator1, actuator2;
    private Spark light;
    private Servo door;
    private SpeedControllerGroup driveL, driveR;
    private DifferentialDrive myRobot;
    private Timer timer;

    private double delay = 100.0;
    private boolean isRampOn, areArmsOn, isLiftOpen, areDoorsOpen, lightOn;

    public void robotInit(){
        myRobot = new DifferentialDrive(driveL, driveR);
        stickL = new Joystick(0);
        stickR = new Joystick(1);

        isRampOn = false;
        areArmsOn = false;
        isLiftOpen = false;
        areDoorsOpen = false;

        drive1 = new WPI_TalonSRX(1);
        drive2 = new WPI_TalonSRX(2);
        drive3 = new WPI_TalonSRX(3);
        drive4 = new WPI_TalonSRX(4);
        ramp = new WPI_VictorSPX(7);
        arm = new WPI_VictorSPX(8);
        actuator1 = new WPI_VictorSPX(5);
        actuator2 = new WPI_VictorSPX(6);
        door = new Servo(1);
        light = new Spark(0);
        driveL = new SpeedControllerGroup(drive1, drive2);
        driveR = new SpeedControllerGroup(drive3, drive4);

        timer = new Timer();
        CameraServer.getInstance().startAutomaticCapture(0);
        CameraServer.getInstance().startAutomaticCapture(1);
    }

    @Override
    public void robotPeriodic(){

    }

    @Override
    public void autonomousInit(){

    }

    @Override
    public void teleopPeriodic(){
        myRobot.tankDrive(stickL.getY(), stickR.getY());
        ramp();
        arms();
        actuators();
        lifts();
        door();
        light();
    }

    @Override
    public void testPeriodic(){

    }

    public void light(){
        if(stickL.getRawButton(10) && lightOn == false){
            lightOn = true;
            light.set(1);
        }else if(stickL.getRawButton(10) && lightOn == true){
            light.set(0); 
            lightOn = false;
        }
    }
    public void ramp(){
        if(stickL.getRawButton(3) && !isRampOn){
            isRampOn = true;
            ramp.set(4);
        }
        else if(stickL.getRawButton(3) && isRampOn){
            isRampOn = false;
            ramp.set(-4);
        }
    }

    public void arms(){
        if(stickL.getRawButton(5) && !areArmsOn){
            areArmsOn = false;
            arm.set(2);
        }
        else if(stickL.getRawButton(5) && areArmsOn){
            areArmsOn = false;
            arm.set(-2);
        }
    }

    public void actuators(){
    }

    public void lifts(){
        if(stickR.getRawButton(5) && !isLiftOpen){
            isLiftOpen = true;
            actuator1.set(3);
            actuator2.set(3);
        }
        else if(stickR.getRawButton(3) && !isLiftOpen){
            isLiftOpen = true;
            actuator1.set(-3);
            actuator2.set(-3);
        }
    }

    public void door(){
      if(stickL.getRawButton(10) && !areDoorsOpen){
        areDoorsOpen = false;
        door.set(4);
        timer.start();
        if(timer.get() > delay){
          door.set(-1);
          timer.reset();
          if(timer.get() > 15){
            door.set(0);
            timer.stop();
          }
        }
      }else if(stickL.getRawButton(10) && areDoorsOpen){
        areDoorsOpen = false;
        door.set(-4);
        timer.start();
        if(timer.get() > delay){
          door.set(-1);
          timer.reset();
          if(timer.get() > 15){
            door.set(0);
            timer.stop();
          }
        }
      }
    }
}