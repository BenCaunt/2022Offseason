package org.firstinspires.ftc.teamcode.CommandFramework.Commands.DrivetrainCommands;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ThermalEquilibrium.homeostasis.Utils.Vector;

import org.firstinspires.ftc.teamcode.CommandFramework.Commands.Command;
import org.firstinspires.ftc.teamcode.CommandFramework.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.CommandFramework.Subsystems.Odometry;
import org.firstinspires.ftc.teamcode.Math.Controllers.DistanceDriveControl;
import org.firstinspires.ftc.teamcode.Utils.ExtraUtils;

import java.util.function.DoubleSupplier;

public class DriveDistance extends Command {

	DistanceDriveControl control;
	Drivetrain drivetrain;
	Odometry odom;

	double targetDistance;

	Vector initialPosition;
	private boolean isComplete = false;

	public DriveDistance(Drivetrain drivetrain, Odometry odom, double targetDistance) {
		this.drivetrain = drivetrain;
		this.odom = odom;
		this.targetDistance = targetDistance;
	}


	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	public void init() {
		initialPosition = odom.getPosition();
		double initialAngle = odom.getPosition().get(2);

		control = new DistanceDriveControl(new DoubleSupplier() {
			@Override
			public double getAsDouble() {
				return odom.getPosition().get(2);
			}
		}, initialAngle);

	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	public void periodic() {
		double traveled = ExtraUtils.calculateDistance(initialPosition,
				odom.getPosition());
		drivetrain.setPower(control.calculate(targetDistance,traveled));
		isComplete = Math.abs(control.endPoseError()) < 1 && Math.abs(odom.getVelocity().get(0)) < 3;

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
