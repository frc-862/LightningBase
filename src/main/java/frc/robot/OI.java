/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.commands.VisionRotateAndApproach;
import frc.lightning.commands.VisionTurn;
import frc.robot.commands.FollowLine;
//import frc.robot.commands.paths.StraightPath;
//import frc.robot.commands.paths.TurnRight;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    private Joystick driver = new Joystick(0);
    private Button followLineButton = new JoystickButton(driver, RobotMap.shootButton);
    private Button rotateApproachButton = new JoystickButton(driver, 2);

    public double getLeftPower() {
        return -driver.getRawAxis(Constants.leftThrottleAxis);
    }
    public double getRightPower() {
        return -driver.getRawAxis(Constants.rightThrottleAxis);
    }

    public OI() {
        followLineButton.whenPressed(new FollowLine());
        rotateApproachButton.whileHeld(new VisionRotateAndApproach());
        //SmartDashboard.putData("Drive Straight", new StraightPath());
        //SmartDashboard.putData("Turn Right", new TurnRight());
        SmartDashboard.putData("VisionTurn", new VisionTurn());

        int jt = DriverStation.getInstance().getJoystickType(0);
    }
}
