package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.DriveTeleop;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.DriveWithTime;
import org.firstinspires.ftc.teamcode.CommandFramework.Subsystems.Robot;

@Autonomous
public class TestAuto extends LinearOpMode {

	@Override
	public void runOpMode() throws InterruptedException {
		Robot robot = new Robot(gamepad1, gamepad2);

		CommandScheduler scheduler = new CommandScheduler(hardwareMap, robot.drivetrain, robot.dashboard, robot.gamepad1, robot.gamepad2, robot.odometry);
		scheduler.initAuto();
		waitForStart();

		scheduler.enqueueCommand(new DriveWithTime(robot.drivetrain,2,0.5));
		scheduler.enqueueCommand(new DriveWithTime(robot.drivetrain,2,0));
		scheduler.enqueueCommand(new DriveWithTime(robot.drivetrain,2,-0.5));

		while (opModeIsActive()) {
			scheduler.run();
		}
		scheduler.shutdown();

	}
}

