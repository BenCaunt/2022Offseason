package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CommandFramework.BaseTeleop;
import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands.DriveTeleop;


@TeleOp
public class TestTeleop extends BaseTeleop {

	@Override
	public Command setupTeleop(CommandScheduler scheduler) {
		return new DriveTeleop(robot.gamepad1,robot.drivetrain);
	}
}
