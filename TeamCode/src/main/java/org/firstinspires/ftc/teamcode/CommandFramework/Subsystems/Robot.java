package org.firstinspires.ftc.teamcode.CommandFramework.Subsystems;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.Command;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.DrivetrainCommands.DriveDistance;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.DrivetrainCommands.DriveTeleop;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.DrivetrainCommands.DriveWithTime;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.DrivetrainCommands.TurnCommand;

public class Robot {

	public enum OpMode {
		Auto,
		Teleop
	}

	public Dashboard dashboard = new Dashboard();
	public Input gamepad1;
	public Input gamepad2;
	public Drivetrain drivetrain = new Drivetrain();
	public Odometry odometry = new Odometry();

	protected CommandScheduler scheduler;

	public Robot(HardwareMap hwMap, OpMode opMode, Gamepad gamepad1, Gamepad gamepad2) {
		this.gamepad1 = new Input(gamepad1);
		this.gamepad2 = new Input(gamepad2);

		scheduler = new CommandScheduler(hwMap, drivetrain, odometry, dashboard);

		if (opMode.equals(OpMode.Auto)) {
			scheduler.initAuto();
		} else if (opMode.equals(OpMode.Teleop)) {
			scheduler.initTeleop();
		}
	}

	public void update() {
		updateInput();
		scheduler.run();
	}

	public void shutdown() {
		scheduler.shutdown();
	}

	public void updateInput() {
		gamepad1.periodic();
		gamepad2.periodic();
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	public void setupAuto() {
//		Command driveForward = new DriveWithTime(drivetrain, 2, 0.3);
//		Command driveBack = new DriveWithTime(drivetrain, 2, -0.3);
		Command driveForward = new DriveDistance(drivetrain,odometry, 40);
		Command turn = new TurnCommand(drivetrain,odometry, Math.toRadians(180));
		Command driveBack = new DriveDistance(drivetrain,odometry, 40);
		driveForward.setNext(turn);
		turn.setNext(driveBack);

		scheduler.forceCommand(driveForward);
	}

	public void setupTeleop() {
		Command teleopCommand = new DriveTeleop(gamepad1, drivetrain);

		scheduler.forceCommand(teleopCommand);
	}

	public CommandScheduler getScheduler() {
		return scheduler;
	}
}
