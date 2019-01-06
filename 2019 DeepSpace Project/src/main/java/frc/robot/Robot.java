/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Actuator;

public class Robot extends IterativeRobot {
  DifferentialDrive myRobot;
  Joystick stickL, stickR;
  Talon d1, d2, d3, d4;
  Actuator lift1, lift2, lift3, lift4;
  

  @Override
  public void robotInit() {
    myRobot = new DifferentialDrive(driveL, driveR);
    d1 = new Talon(0);
    d2 = new Talon(1);
    d3 = new Talon(3);
    d4 = new Talon(4);
    stickL = new Joystick(0);
    stickR = new Joystick(1);
  }


  @Override
  public void autonomousInit() {
  }


  @Override
  public void autonomousPeriodic() {
  }


  @Override
  public void teleopInit() {
  }


  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testPeriodic() {
  }
}
