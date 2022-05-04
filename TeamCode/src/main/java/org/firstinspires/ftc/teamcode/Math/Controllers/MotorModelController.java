package org.firstinspires.ftc.teamcode.Math.Controllers;

import com.qualcomm.robotcore.util.Range;

public class MotorModelController {
    double kV;
    double kA;
    double kS;

    // Make sure to determine these with reference to (0-1) voltage rather than 0-12
    public MotorModelController(double kV, double kA, double kS) {
        this.kV = kV;
        this.kA = kA;
        this.kS = kS;
    }

    public double calculate(double reference, double state, double maxAccel) {
        if (reference < state) {
            return 0;
        }

        double accel = Range.clip(reference - state, 0, maxAccel);
        double voltage = kV * state + kA * accel + kS;

        return voltage / 12;
    }
}
