package org.firstinspires.ftc.teamcode.CommandFramework;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands.DriveDistance;
import org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands.TurnCommand;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Robot;

public abstract class BaseAuto extends LinearOpMode {

	protected Robot robot;

	@Override
	public void runOpMode() {
		robot = new Robot(hardwareMap, Robot.OpMode.Auto, gamepad1, gamepad2);
		waitForStart();

		robot.getScheduler().forceCommand(setupAuto(robot.getScheduler()));

		while(opModeIsActive())
			robot.update();
	}

	public abstract Command setupAuto(CommandScheduler scheduler);


	public DriveDistance drive(double distance) {
		return new DriveDistance(robot.drivetrain, robot.odometry, distance);
	}
	@RequiresApi(api = Build.VERSION_CODES.N)
	public TurnCommand turn(double radians) {
		return new TurnCommand(robot.drivetrain, robot.odometry, radians);
	}
}
