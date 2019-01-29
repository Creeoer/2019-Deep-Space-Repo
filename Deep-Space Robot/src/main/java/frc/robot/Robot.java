package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.functions.Timer;
import edu.wpi.cscore.UsbCamera;


/*TODO:
 - Fix Drive
 - Test Others
 - Config CAN
*/
@SuppressWarnings("deprecation")

public class Robot extends IterativeRobot {
    private Joystick stickL, stickR;
    private WPI_TalonSRX drive1, drive2, drive3, drive4;
    private WPI_VictorSPX ramp, arm, actuator1, actuator2;
    private Servo door;
    private SpeedControllerGroup driveL, driveR;
    private DifferentialDrive myRobot;
    private Timer timer;
    private UsbCamera camera1, camera2;

    private double delay = 100.0;
    private boolean isRampOn, areArmsOn, isLiftOpen, areDoorsOpen;

    public void robotInit(){
        //Attack on Port 1
        //Extreme 3D Pro on Port 2
        stickL = new Joystick(1);
        stickR = new Joystick(2);
        timer = new Timer();

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
        door = new Servo(1);
        //actuator1 = new WPI_VictorSPX(5);
        //actuator2 = new WPI_VictorSPX(6);

        driveL = new SpeedControllerGroup(drive1, drive2);
        driveR = new SpeedControllerGroup(drive3, drive4);

        camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        camera1.setResolution(1280, 720);
        camera2.setResolution(1280, 720);

        myRobot = new DifferentialDrive(driveL, driveR);
    }

    @Override
    public void robotPeriodic(){

    }

    @Override
    public void autonomousInit(){
    }

    @Override
    public void teleopPeriodic(){
        myRobot.tankDrive(-stickL.getY(), stickR.getY());

        ramp();
        arms();
        //lifts();
        door();
    }

    @Override
    public void testPeriodic(){

    }

    public void ramp(){
        if(stickL.getRawButton(5) && !isRampOn){
            isRampOn = true;
            ramp.set(4);
        }
        else if(stickL.getRawButton(5) && isRampOn){
            isRampOn = false;
            ramp.set(-4);
        }
    }

    public void arms(){
        if(stickR.getRawButton(6) && !areArmsOn){
            areArmsOn = false;
            arm.set(2);
        }
        else if(stickR.getRawButton(4) && !areArmsOn){
            areArmsOn = false;
            arm.set(-2);
        }
    }
    /*Commented Out Lifts
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
    */

    public void door(){
        if(stickL.getRawButton(4) && !areDoorsOpen){
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
            } else if(stickL.getRawButton(4) && areDoorsOpen){
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
}
