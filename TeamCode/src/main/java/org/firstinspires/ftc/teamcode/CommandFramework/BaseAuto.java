package org.firstinspires.ftc.teamcode.CommandFramework;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CommandFramework.Subsystems.Robot;

public class BaseAuto extends LinearOpMode {

	Robot robot;

	@Override
	public void runOpMode() throws InterruptedException {

		robot = new Robot();

	}
}
