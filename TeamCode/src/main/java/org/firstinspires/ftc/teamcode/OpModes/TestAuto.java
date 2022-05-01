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

		waitForStart();
		Robot robot = new Robot(hardwareMap, Robot.OpMode.Auto, gamepad1, gamepad2);

		while (opModeIsActive()) {
			robot.update();
		}
		robot.shutdown();

	}
}

