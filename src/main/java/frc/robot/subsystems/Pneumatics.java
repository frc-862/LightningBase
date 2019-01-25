package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.PneumaticTest;

public class Pneumatics extends Subsystem {
    private DoubleSolenoid solenoid = new DoubleSolenoid(11, 0, 1);
    private DoubleSolenoid.Value value = DoubleSolenoid.Value.kReverse;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.


    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        setDefaultCommand(new PneumaticTest());
    }

    public void toggle() {
        if (value == DoubleSolenoid.Value.kForward) {
            value = DoubleSolenoid.Value.kReverse;
        } else {
            value = DoubleSolenoid.Value.kForward;
        }
        solenoid.set(value);
    }
}

