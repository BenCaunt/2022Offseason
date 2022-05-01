package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.Command;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.DrivetrainCommands.DriveWithTime;
import org.firstinspires.ftc.teamcode.CommandFramework.Subsystems.Robot;

@Autonomous
public class TestAuto extends LinearOpMode {

	@Override
	public void runOpMode() throws InterruptedException {
		Robot robot = new Robot(gamepad1, gamepad2);

		CommandScheduler scheduler = new CommandScheduler(hardwareMap, robot.drivetrain, robot.dashboard, robot.gamepad1, robot.gamepad2, robot.odometry);
		scheduler.initAuto();
		waitForStart();

		Command driveForward = new DriveWithTime(robot.drivetrain, 2, 0.3);
		Command stop = new DriveWithTime(robot.drivetrain, 2, 0);
		Command driveBack = new DriveWithTime(robot.drivetrain, 2, -0.3);
		driveForward.setNext(stop);
		stop.setNext(driveBack);
		scheduler.forceCommand(driveForward);


		while (opModeIsActive()) {
			scheduler.run();
		}
		scheduler.shutdown();

	}
}

