package org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Odometry;
import org.firstinspires.ftc.teamcode.Math.Controllers.TurnOnlyControl;

import java.util.function.DoubleSupplier;

public class TurnCommand extends Command {

	Drivetrain drivetrain;
	boolean isComplete = false;
	Odometry odometry;

	double referenceAngle;
	TurnOnlyControl controller;

	@RequiresApi(api = Build.VERSION_CODES.N)
	public TurnCommand(Drivetrain drivetrain, Odometry odometry, double referenceAngle) {
		this.drivetrain = drivetrain;
		this.odometry = odometry;
		this.referenceAngle = referenceAngle;
		this.controller = new TurnOnlyControl(new DoubleSupplier() {
			@Override
			public double getAsDouble() {
				return odometry.getPosition().get(2);
			}
		},referenceAngle);
	}

	@Override
	public void init() {

	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	public void periodic() {
		drivetrain.setPower(controller.calculate());
		isComplete = Math.abs(controller.getEndGoalError()) < Math.toRadians(2)
				&& Math.abs(odometry.getVelocity().get(2)) < Math.toRadians(10);
	}

	@Override
	public boolean stop() {
		return isComplete;
	}

	@Override
	public void shutdown() {
		drivetrain.setPower(0,0);
	}
}
