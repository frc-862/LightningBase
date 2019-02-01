/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.util;

/**
 * Add your docs here.
 */
public class Target {
    private double standoff;
    private double rotation;
    private double squint;
    public Target(double standoff, double rotation, double squint) {
        this.standoff = standoff;
        this.rotation = rotation;
        this.squint = squint;

    }

    public double standoff() {
        return standoff;
    }
    public double rotation() {
        return rotation;
    }
    public double squint() {
        return squint;
    }
}