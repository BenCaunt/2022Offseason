package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class HelloWorldFTC extends LinearOpMode {
	@Override
	public void runOpMode() throws InterruptedException {

		ElapsedTime timer = new ElapsedTime();
		DcMotorEx testMotor = hardwareMap.get(DcMotorEx.class, "NameOnDriverStation");
		// everything before this runs during init
		waitForStart();
		timer.reset();
		while (opModeIsActive()) {

			System.out.println("Delta time" + timer.seconds());
			testMotor.setPower(1);
			timer.reset();
		}
	}
}
