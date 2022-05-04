package org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Odometry;

public class QuasiStaticVelocity extends Command {
    Drivetrain drivetrain;
    Odometry odometry;
    ElapsedTime timer = new ElapsedTime();

    double rampRate;
    double maxLength;

    public QuasiStaticVelocity(Drivetrain drivetrain, Odometry odometry, double rampRate, double maxLength) {
        super(drivetrain, odometry);

        this.drivetrain = drivetrain;
        this.odometry = odometry;
        this.rampRate = rampRate;
        this.maxLength = maxLength;
    }


    @Override
    public void init() {
        timer.reset();
        drivetrain.setPower(0,0);
    }

    @Override
    public void periodic() {
        double leftVoltage = drivetrain.getLeftVoltage();
        double leftVelocity = Odometry.encoderTicksToInches(odometry.FrontLeft.getVelocity());

        double rightVoltage = drivetrain.getRightVoltage();
        double rightVelocity = Odometry.encoderTicksToInches(odometry.FrontLeft.getVelocity());

        RobotLog.ii("SysID (V/v)", "(" + leftVoltage + "," + leftVelocity + "),(" + rightVoltage + "," + rightVelocity + ")");

        double newVoltage = timer.seconds() * rampRate / 12;
        drivetrain.setPower(newVoltage, newVoltage);
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
