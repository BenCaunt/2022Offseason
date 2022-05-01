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
public class
TestTeleop extends LinearOpMode {
	@Override
	public void runOpMode() throws InterruptedException {
		waitForStart();
		Robot robot = new Robot(hardwareMap, Robot.OpMode.Teleop, gamepad1, gamepad2);
		while (opModeIsActive()) {
			robot.update();
		}
	}
}
