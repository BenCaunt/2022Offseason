package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.DriveTeleop;
import org.firstinspires.ftc.teamcode.CommandFramework.Subsystems.Robot;


@TeleOp
public class TestTeleop extends LinearOpMode {
	@Override
	public void runOpMode() throws InterruptedException {

		Robot robot = new Robot(gamepad1, gamepad2);

		CommandScheduler scheduler = new CommandScheduler(hardwareMap, robot.drivetrain, robot.dashboard, robot.gamepad1, robot.gamepad2, robot.odometry);
		scheduler.initTeleop();
		scheduler.enqueueCommand(new DriveTeleop(robot.gamepad1, robot.drivetrain));
		waitForStart();
		while (opModeIsActive()) {
			scheduler.run();
		}
		scheduler.shutdown();
	}
}
