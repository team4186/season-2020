package frc.robot

import edu.wpi.first.wpilibj.RobotBase
import frc.robot.variants.shinDestroyer

fun main() {
//  RobotBase.startRobot { ConfigurableRobot(clinky) }
//  RobotBase.startRobot { ConfigurableRobot(dinky) }
  RobotBase.startRobot { ConfigurableRobot(shinDestroyer) }
//  RobotBase.startRobot(::Janky)
//  RobotBase.startRobot{ object :TimedRobot() {}  }
}
