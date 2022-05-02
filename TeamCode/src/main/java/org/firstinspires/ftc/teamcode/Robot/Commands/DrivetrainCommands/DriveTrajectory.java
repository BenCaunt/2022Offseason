package org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Utils.MathUtils;
import com.ThermalEquilibrium.homeostasis.Utils.Vector;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.Math.AsymmetricProfile.DirectTrajectory;
import org.firstinspires.ftc.teamcode.Math.Controllers.ControlConstants;
import org.firstinspires.ftc.teamcode.Math.Controllers.TurnOnlyControl;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Odometry;

public class DriveTrajectory extends Command {
    Drivetrain drivetrain;
    Odometry odometry;

    DirectTrajectory trajectory;
    TurnOnlyControl turnController;
    BasicPID distanceController = new BasicPID(ControlConstants.distanceControl);
    ElapsedTime timer;

    public DriveTrajectory(Drivetrain drivetrain, Odometry odometry, DirectTrajectory trajectory) {
        super(drivetrain, odometry);

        this.drivetrain = drivetrain;
        this.odometry = odometry;
        this.trajectory = trajectory;
        this.timer = new ElapsedTime();

        this.turnController = new TurnOnlyControl(() -> odometry.getPosition().get(2), trajectory.targetPose(timer.seconds()).getHeading());
    }

    @Override
    public void init() { }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void periodic() {
        double forwardSpeed = distanceController.calculate(
                odometry.getPose().distanceBetween(trajectory.targetPose(timer.seconds())),
                0
        );
        Vector turnSpeeds = turnController.calculate();

        double headingError = MathUtils.normalizedHeadingError(
                odometry.getPose().getHeading(),
                trajectory.targetPose(timer.seconds()).getHeading()
        );
        double headingScale = Range.clip(Math.cos(headingError), 0, 1);

        drivetrain.setPower(
                forwardSpeed * headingScale + turnSpeeds.get(0),
                forwardSpeed * headingScale + turnSpeeds.get(1)
        );
    }

    @Override
    public boolean completed() {
        return false;
    }

    @Override
    public void shutdown() {
        drivetrain.setPower(0,0);
    }
}
