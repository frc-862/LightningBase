/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.logging.DataLogger;
import frc.lightning.util.FaultMonitor;
import frc.lightning.util.UnchangingFaultMonitor;
import frc.lightning.util.FaultCode.Codes;
import frc.robot.RobotMap;
import frc.robot.commands.PneumaticTest;

/**
 * This is a subsystem that has robot sensor that
 * are general parts of the robot -- there are
 * no active mechanisms in Core, and it is not
 * expected that a command would ever require
 * Core, it is just queried.
 */
public class Core extends Subsystem {
    private AHRS navx;
    private Compressor compressor = new Compressor(RobotMap.compressorCANId);
    private PowerDistributionPanel pdp = new PowerDistributionPanel(RobotMap.pdpCANId);

    private DigitalInput pressure1 = new DigitalInput(0);

    public Core() {
        navx = new AHRS(SPI.Port.kMXP);
        DataLogger.addDataElement("heading", () -> getHeading());

        // monitor if the heading is exactly the same, there is always
        // some jitter in the reading, so this will not be the case
        // if we are getting valid values from the sensor for >= 3 seconds

        // FaultMonitor.register(new UnchangingFaultMonitor(Codes.NAVX_ERROR, () -> navx.getUpdateCount(),
        //     2.0, 0, "NavX unresponsive"));

        // addChild("PDP", pdp);
        addChild("NavX", navx);
        addChild("Compressor", compressor);
    }

    public double getHeading() {
        return navx.getFusedHeading();
    }

    /// Return -1 at far left range
    //  0 when centered,
    //  1 at far right
    public double lineSensor() {
        return 0;
    }

    public boolean frontPress() {
        return false;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    }

    public boolean hatchPressed() {
        return pressure1.get();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Hatch Sensor", hatchPressed());
        SmartDashboard.putData(pressure1);
    }
}
