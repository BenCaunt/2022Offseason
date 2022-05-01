package org.firstinspires.ftc.teamcode.CommandFramework.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.Command;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.DrivetrainCommands.DriveTeleop;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.DrivetrainCommands.DriveWithTime;

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
			setupAuto();
		} else if (opMode.equals(OpMode.Teleop)) {
			scheduler.initTeleop();
			setupTeleop();
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

	public void setupAuto() {
		Command driveForward = new DriveWithTime(drivetrain, 2, 0.3);
		Command stop = new DriveWithTime(drivetrain, 2, 0);
		Command driveBack = new DriveWithTime(drivetrain, 2, -0.3);

		driveForward.setNext(stop);
		stop.setNext(driveBack);

		scheduler.forceCommand(driveForward);
	}

	public void setupTeleop() {
		Command teleopCommand = new DriveTeleop(gamepad1, drivetrain);

		scheduler.forceCommand(teleopCommand);
	}
}
