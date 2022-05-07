package org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Dashboard;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Odometry;

public class AccelerationIdentification extends Command {
    Drivetrain drivetrain;
    Odometry odometry;

    ElapsedTime timer = new ElapsedTime();
    double maxLength;
    double kV;
    double kS;
    double power;

    double[] velocityMeasurementsLeft = {0, 0, 0, 0};
    double[] velocityMeasurementsRight = {0, 0, 0, 0};
    double[] timeMeasurements = {0, 0, 0, 0};

    public AccelerationIdentification(Drivetrain drivetrain, Odometry odometry, double kV, double kS, double power, double maxLength) {
        super(drivetrain, odometry);

        this.drivetrain = drivetrain;
        this.odometry = odometry;
        this.kV = kV;
        this.kS = kS;
        this.power = power;
        this.maxLength = maxLength;
    }


    @Override
    public void init() {
        timer.reset();
        drivetrain.setPower(power,power);
    }

    @Override
    public void periodic() {
        velocityMeasurementsLeft[0] = velocityMeasurementsLeft[1];
        velocityMeasurementsLeft[1] = velocityMeasurementsLeft[2];
        velocityMeasurementsLeft[2] = velocityMeasurementsLeft[3];
        velocityMeasurementsLeft[3] = Odometry.encoderTicksToInches(odometry.leftEncoder.getVelocity());

        velocityMeasurementsRight[0] = velocityMeasurementsRight[1];
        velocityMeasurementsRight[1] = velocityMeasurementsRight[2];
        velocityMeasurementsRight[2] = velocityMeasurementsRight[3];
        velocityMeasurementsRight[3] = Odometry.encoderTicksToInches(odometry.rightEncoder.getVelocity());

        timeMeasurements[0] = timeMeasurements[1];
        timeMeasurements[1] = timeMeasurements[2];
        timeMeasurements[2] = timeMeasurements[3];
        timeMeasurements[3] = timer.seconds();

        double accelerationLeft = (velocityMeasurementsLeft[3] - velocityMeasurementsLeft[0]) / (timeMeasurements[3] - timeMeasurements[0]);
        double accelerationRight = (velocityMeasurementsRight[3] - velocityMeasurementsRight[0]) / (timeMeasurements[3] - timeMeasurements[0]);

        double leftAccelerationPower = drivetrain.getLeftPower() - (kV * Odometry.encoderTicksToInches(odometry.leftEncoder.getVelocity()) + kS);
        double rightAccelerationPower = drivetrain.getRightPower() - (kV * Odometry.encoderTicksToInches(odometry.rightEncoder.getVelocity()) + kS);

        Dashboard.packet.put("Velocity", Odometry.encoderTicksToInches(odometry.leftEncoder.getVelocity()));
        Dashboard.packet.put("Acceleration", accelerationLeft);
        Dashboard.packet.put("Velocity Delta", velocityMeasurementsLeft[3] - velocityMeasurementsLeft[0]);
        Dashboard.packet.put("Velocity 1", velocityMeasurementsLeft[0]);
        Dashboard.packet.put("Velocity 2", velocityMeasurementsLeft[3]);

        RobotLog.ii("SysID (P/V)^2", (timeMeasurements[3] - timeMeasurements[0]) + " " + leftAccelerationPower + ":" + accelerationLeft + "," + rightAccelerationPower + ":" + accelerationRight);
    }

    @Override
    public boolean completed() {
        return timer.seconds() > maxLength;
    }

    @Override
    public void shutdown() {
        drivetrain.setPower(0,0);
    }
}
