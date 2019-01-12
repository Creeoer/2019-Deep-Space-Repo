package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Timer;

@SuppressWarnings("deprecation")

public class Robot extends IterativeRobot {
    private Joystick stickL, stickR;
    private WPI_TalonSRX drive1, drive2, drive3, drive4;
    private WPI_VictorSPX ramp, arm, actuator1, actuator2, door;
    private SpeedControllerGroup driveL, driveR;
    private DifferentialDrive myRobot;
    private boolean isRampOn, areArmsOn, areRampsOn, isLiftOpen, areDoorsOpen;

    public void robotInit(){
        stickL = new Joystick(0);
        stickR = new Joystick(1);

        drive1 = new WPI_TalonSRX(1);
        drive2 = new WPI_TalonSRX(2);
        drive3 = new WPI_TalonSRX(3);
        drive4 = new WPI_TalonSRX(4);
        ramp = new WPI_VictorSPX(5);
        arm = new WPI_VictorSPX(6);
        actuator1 = new WPI_VictorSPX(7);
        actuator2 = new WPI_VictorSPX(8);
        door = new WPI_VictorSPX(9);

        driveL = new SpeedControllerGroup(drive1, drive2);
        driveR = new SpeedControllerGroup(drive3, drive4);
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
    }

    @Override
    public void testPeriodic(){

    }

    public void ramp(){
        if(stickL.getRawButton(3) && !isRampOn){
            isRampOn = true;
            ramp.set(4);
        }
        else if(stickL.getRawButton(2) && !isRampOn){
            isRampOn = true;
            ramp.set(-4);
        }
    }

    public void arms(){
        if(stickL.getRawButton(5) && !areArmsOn){
            areArmsOn = true;
            arm.set(2);
        }
        else if(stickL.getRawButton(4) && !areArmsOn){
            areArmsOn = true;
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
        if(stickR.getRawButton(6) && !areDoorsOpen){
            door.set(3);
        }
        else if(stickR.getRawButton(4) && !areDoorsOpen){
            door.set(-3);
        }
    }
}

