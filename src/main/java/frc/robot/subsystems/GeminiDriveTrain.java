/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.*;
import frc.lightning.subsystems.CANDrivetrain;
import frc.lightning.util.MotorConfig;
import frc.robot.commands.TankDrive;

import java.util.function.Consumer;

/**
 * Gemini is a 4 CIM, single speed drivetrain. One
 * of the simplest we have fielded in years. That
 * said we need a really solid model of its characteristics
 * so we can run profiles and optimize our usage of it.
 */
public class GeminiDriveTrain extends CANDrivetrain {
    MotorConfig driveMotorConfig = MotorConfig.get("drive.json");

    public static GeminiDriveTrain create() {
        return new GeminiDriveTrain(
                   new WPI_TalonSRX(1),
                   new WPI_VictorSPX(2),
                   new WPI_TalonSRX(4),
                   new WPI_VictorSPX(5));
    }

    public GeminiDriveTrain(WPI_TalonSRX left, WPI_VictorSPX left2,
                            WPI_TalonSRX right, WPI_VictorSPX right2) {
        super(left, right);
        addLeftFollower(left2);
        addRightFollower(right2);

        configureMotors();
        withEachMotor(driveMotorConfig::registerMotor);
    }

    public void configureMotors() {
        getRightMaster().setInverted(true);
        super.configureMotors();

        withEachMaster((m) -> {
//          m.configOpenloopRamp(0.2);
//          m.configClosedloopRamp(0.2);
        });

        withEachMotor(driveMotorConfig::resetMotor);
        enableLogging();
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new TankDrive());
    }
}
