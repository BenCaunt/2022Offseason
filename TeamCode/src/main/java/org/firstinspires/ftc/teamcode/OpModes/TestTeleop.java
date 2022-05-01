package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.Command;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.DrivetrainCommands.DriveTeleop;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.DrivetrainCommands.DriveWithTime;
import org.firstinspires.ftc.teamcode.CommandFramework.Subsystems.Robot;
import org.firstinspires.ftc.teamcode.Simulation.TestCommandsSubsystems.PrintCommand1;
import org.firstinspires.ftc.teamcode.Simulation.TestCommandsSubsystems.PrintSubsystem1;


@TeleOp
public class TestTeleop extends LinearOpMode {
	@Override
	public void runOpMode() throws InterruptedException {

		Robot robot = new Robot(gamepad1, gamepad2);

		PrintSubsystem1 fuck = new PrintSubsystem1();

		CommandScheduler scheduler = new CommandScheduler(hardwareMap, robot.drivetrain, robot.dashboard, robot.gamepad1, robot.gamepad2, robot.odometry, fuck);
		scheduler.initTeleop();

		Command teleopCommand = new DriveTeleop(robot.gamepad1, robot.drivetrain);
		scheduler.forceCommand(teleopCommand);

		waitForStart();
		while (opModeIsActive()) {
			scheduler.run();

			if (robot.gamepad1.isTriangle()) {
				Command print = new PrintCommand1(fuck, "fuck this");
				scheduler.forceCommand(print);
			}
			if (robot.gamepad1.isCrossPressed()) {
				System.out.println("cross is pressed");
				Command AutoDrive = new DriveWithTime(robot.drivetrain,2,-0.3);
				AutoDrive.setNext(teleopCommand);
				scheduler.forceCommand(AutoDrive);
			}

		}
		scheduler.shutdown();
	}
}
