package org.firstinspires.ftc.teamcode.CommandFramework.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Robot {

	public Dashboard dashboard = new Dashboard();
	public Input gamepad1;
	public Input gamepad2;
	public Drivetrain drivetrain = new Drivetrain();
	public Odometry odometry = new Odometry();

	public Robot(Gamepad gamepad1, Gamepad gamepad2) {
		this.gamepad1 = new Input(gamepad1);
		this.gamepad2 = new Input(gamepad2);
	}

}
