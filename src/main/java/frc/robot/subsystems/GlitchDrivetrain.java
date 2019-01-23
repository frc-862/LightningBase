/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.function.Consumer;

import com.ctre.phoenix.motorcontrol.can.*;
import frc.lightning.subsystems.CANDrivetrain;
import frc.lightning.util.MotorConfig;
import frc.robot.commands.TankDrive;

/**
 * Add your docs here.
 */
public class GlitchDrivetrain extends CANDrivetrain {
    public static GlitchDrivetrain create() {
        return new GlitchDrivetrain(
                   new WPI_TalonSRX(1),
                   new WPI_VictorSPX(2),
                   new WPI_VictorSPX(3),
                   new WPI_TalonSRX(4),
                   new WPI_VictorSPX(5),
                   new WPI_VictorSPX(6));
    }

    public GlitchDrivetrain(WPI_TalonSRX left, WPI_VictorSPX left2, WPI_VictorSPX left3, WPI_TalonSRX right, WPI_VictorSPX right2, WPI_VictorSPX right3) {
        super(left, right);
        getLeftMaster().setInverted(true);
        addLeftFollower(left2);
        addLeftFollower(left3);

        addRightFollower(right2, true);
        addRightFollower(right3, true);

        configureMotors();

        MotorConfig drive = MotorConfig.get("drive.json");
        withEachMotor((m) -> drive.registerMotor(m));
    }

    public void configureMotors() {
        super.configureMotors();
        getLeftMaster().setInverted(true);
        enableLogging();
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new TankDrive());
    }
}
