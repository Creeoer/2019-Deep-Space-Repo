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

public class Robot extends IterativeRobot {
  DifferentialDrive myRobot;


  @Override
  public void robotInit() {
    myRobot = new DifferentialDrive(driveL, driveR);
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
