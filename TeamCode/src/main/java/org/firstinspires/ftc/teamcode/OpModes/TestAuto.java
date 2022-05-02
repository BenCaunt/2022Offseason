package org.firstinspires.ftc.teamcode.OpModes;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ThermalEquilibrium.homeostasis.Utils.Vector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.sun.tools.javac.util.List;

import org.firstinspires.ftc.teamcode.CommandFramework.BaseAuto;
import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.Math.AsymmetricProfile.DirectTrajectory;
import org.firstinspires.ftc.teamcode.Math.Geometry.Pose2d;
import org.firstinspires.ftc.teamcode.Math.Geometry.Rotation2d;
import org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands.DriveTrajectory;

import java.util.ArrayList;

@Autonomous
public class TestAuto extends BaseAuto {
	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	public Command setupAuto(CommandScheduler scheduler) {


//		Command auto = drive(40)
//				.addNext(turn(Math.toRadians(180)))
//				.addNext(drive(-40))
//				.addNext(turn(Math.toRadians(0)))
//				.addNext(drive(-80));

		DirectTrajectory trajectory = new DirectTrajectory(
				new ArrayList<>(List.of(
						new Pose2d(0, 0, new Rotation2d(0)),
						new Pose2d(0, 10, new Rotation2d(0)),
						new Pose2d(5, 15, new Rotation2d(0)),
						new Pose2d(10, 20, new Rotation2d(0))
				)),
				new ArrayList<Double>(List.of(
						0.0,
						2.0,
						4.0,
						6.0
				))
		);

		Command followTrajectory = new DriveTrajectory(robot.drivetrain, robot.odometry, trajectory);

		return followTrajectory;
	}
}
