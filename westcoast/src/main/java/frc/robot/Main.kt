/*----------------------------------------------------------------------------*/ /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */ /* Open Source Software - may be modified and shared by FRC teams. The code   */ /* must be accompanied by the FIRST BSD license file in the root directory of */ /* the project.                                                               */ /*----------------------------------------------------------------------------*/
package frc.robot

import edu.wpi.first.wpilibj.RobotBase

/**
 * Do NOT add any static variables to this class, or any initialization at all.
 * Unless you know what you are doing, do not modify this file except to
 * change the parameter class to the startRobot call.
 */
object Main {
  /**
   * Main initialization function. Do not perform any initialization here.
   *
   *
   * If you change your main robot class, change the parameter type.
   */
  @JvmStatic fun main(args: Array<String>) {
    // RobotBase.startRobot(Clinky::new);
    RobotBase.startRobot { Dinky() }
    // RobotBase.startRobot(Janky::new);
    // RobotBase.startRobot(ShinDestroyer::new);
  }
}