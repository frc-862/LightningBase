package frc.lightning.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class VelocityMotionProfile extends Command {
    protected double[][] leftPath;
    protected double[][] rightPath;
    private int index = 0;

    public VelocityMotionProfile(double[][] left, double[][] right) {
        requires(Robot.drivetrain);
        leftPath = left;
        rightPath = right;
    }

    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
        index = 0;
        Robot.drivetrain.resetDistance();
    }

    final double kP = 0.0;
    final double kA = 0.0;
    final double kTheta = 0.0;

    private double calcBaseVelocity(double[] point, double dist) {
        double error = point[0] - Robot.drivetrain.getLeftDistance();
        double velocity = point[1];
        double acceleration = point[2];
        return velocity + error * kP + acceleration * kA;
    }

    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        final double leftVel = calcBaseVelocity(leftPath[index], Robot.drivetrain.getLeftDistance());
        final double rightVel = calcBaseVelocity(rightPath[index], Robot.drivetrain.getRightDistance());

        final double thetaError = leftPath[index][3] - Robot.core.getHeading();
        final double turn = thetaError * kTheta;

        Robot.drivetrain.setVelocity(leftVel - turn, rightVel + turn);
    }


    /**
     */
    @Override
    protected boolean isFinished() {
        return index >= leftPath.length;
    }


    /**
     */
    @Override
    protected void end() {
        Robot.drivetrain.stop();
    }
}
