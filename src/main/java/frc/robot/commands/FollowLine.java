package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class FollowLine extends Command {
    public FollowLine() {
        requires(Robot.drivetrain);
    }

    /**
     * Find our distance from center, use that as the error in our
     * function, setup a P term and drive to the line
     *
     * Note that the turningVelocity and straightVelocity need not
     * be different if this turns (no pun intended) out to be an issue.
     *
     */
    @Override
    protected void execute() {
        final double turnP = 2;
        final double turningVelocity = 2.5;
        final double straightVelocity = 7;

        // read & weight the sensors
        final double error = Robot.core.lineSensor();
        final double turn = error * turnP;
        final double velocity = (error < 0.5) ? straightVelocity : turningVelocity;

        // drive
        Robot.drivetrain.setVelocity(velocity - turn, velocity + turn);
    }


    /**
     * Is that a wall? If so we are done, if not keep going
     */
    @Override
    protected boolean isFinished() {
        return Robot.core.frontPress();
    }

    /**
     * Stop, don't hurt the robot or the wall...
     */
    @Override
    protected void end() {
        Robot.drivetrain.stop();
    }
}
