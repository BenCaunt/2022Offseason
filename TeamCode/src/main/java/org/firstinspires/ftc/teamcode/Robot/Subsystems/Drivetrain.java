package org.firstinspires.ftc.teamcode.Robot.Subsystems;

import com.ThermalEquilibrium.homeostasis.Utils.Vector;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.CommandFramework.Subsystem;

public class Drivetrain extends Subsystem {

	protected DcMotorEx FrontLeft;
	protected DcMotorEx FrontRight;
	protected DcMotorEx BackRight;
	protected DcMotorEx BackLeft;
	protected double leftPower = 0;
	protected double rightPower = 0;




	@Override
	public void initAuto(HardwareMap hwMap) {
		FrontLeft = hwMap.get(DcMotorEx.class, "FrontLeft");
		FrontRight = hwMap.get(DcMotorEx.class, "FrontRight");
		BackLeft = hwMap.get(DcMotorEx.class, "BackLeft");
		BackRight = hwMap.get(DcMotorEx.class, "BackRight");

		FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		FrontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
		FrontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
		BackLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
		BackRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

		FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		FrontLeft.setDirection(DcMotorEx.Direction.FORWARD);
		FrontRight.setDirection(DcMotorEx.Direction.REVERSE);
		BackLeft.setDirection(DcMotorEx.Direction.FORWARD);
		BackRight.setDirection(DcMotorEx.Direction.REVERSE);

	}

	@Override
	public void periodic() {
		FrontLeft.setPower(leftPower);
		BackLeft.setPower(leftPower);
		BackRight.setPower(rightPower);
		FrontRight.setPower(rightPower);
	}

	public void setPower(double left, double right) {
		this.leftPower = left;
		this.rightPower = right;
	}

	/**
	 * set the power using a 2d vector
	 * @param v,  element 0 is the left power, element 1 is the right
	 */
	public void setPower(Vector v) {
		setPower(v.get(0), v.get(1));
	}


	/**
	 * set power using a forward and turn power
	 * @param forward power
	 * @param turn power
	 */
	public void robotRelative(double forward, double turn) {
		double left = forward + turn;
		double right = forward - turn;
		setPower(left,right);
	}

	@Override
	public void shutdown() {
		setPower(0,0);
	}
}
