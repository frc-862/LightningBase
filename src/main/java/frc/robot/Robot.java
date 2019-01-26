/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.RobotController;
import frc.lightning.LightningRobot;
import frc.lightning.subsystems.CANDrivetrain;
import frc.lightning.util.FaultMonitor;
import frc.lightning.util.FaultCode.Codes;
import frc.robot.subsystems.Core;
import frc.robot.subsystems.GeminiDriveTrain;
import frc.robot.subsystems.SiriusDrivetrain;
import frc.robot.subsystems.Pneumatics;
import frc.lightning.subsystems.VisionSystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends LightningRobot {
    public static Core core = new Core();
    //public static CANDrivetrain drivetrain = GeminiDrivetrain.create();
    public static SiriusDrivetrain drivetrain = SiriusDrivetrain.create();
    public static VisionSystem vision = new VisionSystem();
    public static OI oi = new OI();
    public static Pneumatics pneumatics = new Pneumatics();

    public Robot() {
        // this.registerAutonomousCommmand(name, command);
        super();
        System.out.println("Initializing our robot");

        // Shuffleboard
        FaultMonitor.register(new FaultMonitor(Codes.INTERNAL_ERROR, () -> RobotController.getUserButton()));
    }
}
