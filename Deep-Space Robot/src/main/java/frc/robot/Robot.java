package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

@SuppressWarnings("deprecation")
public class Robot extends IterativeRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private Joystick stickL, stickR;
  private WPI_TalonSRX drive1, drive2, drive3, drive4;
  private WPI_VictorSPX lift1, lift2, arm, ramp1, ramp2;
  private DifferentialDrive myRobot;
  private SpeedControllerGroup driveL, driveR;
  private Potentiometer pot;


  //Vision Code
  private static final int IMG_WIDTH = 600;
  private static final int IMG_HEIGHT = 450;

  private VisionThread visionThread;
  private double centerX = 0;
  private final Object imgLock = new Object();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    stickL = new Joystick(0);
    stickR = new Joystick(1);
    drive1 = new WPI_TalonSRX(0);
    drive2 = new WPI_TalonSRX(1);
    drive3 = new WPI_TalonSRX(2);
    drive4 = new WPI_TalonSRX(3);
    lift1 = new WPI_VictorSPX(4);
    lift2 = new WPI_VictorSPX(5);
    arm = new WPI_VictorSPX(6);
    ramp1 = new WPI_VictorSPX(7);
    ramp2 = new WPI_VictorSPX(8);
    driveL = new SpeedControllerGroup(drive1, drive2);
    driveR = new SpeedControllerGroup(drive3, drive4);
    myRobot = new DifferentialDrive(driveL, driveR);

    pot = new AnalogPotentiometer(0, 360, 30);
    AnalogInput ai = new AnalogInput(1);
    pot = new AnalogPotentiometer(ai, 360, 30);

    //Vision Code
    UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture();
    camera1.setResolution(IMG_WIDTH,IMG_HEIGHT);



  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    myRobot.tankDrive(stickL.getY(), stickR.getY());
    
    cameraCode();
      
    }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

//This is the camera code from GRIP.
public void cameraCode(){
  while(stickL.getTrigger()){
    double centerX;

    synchronized(imgLock){
      centerX = this.centerX;
    }
    
    double turn = centerX - (IMG_WIDTH /2);
    myRobot.arcadeDrive(-0.6, turn * 0.005);
  }
  double degrees = pot.get();

  if(degrees > 80){
}}};