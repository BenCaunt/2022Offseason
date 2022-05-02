package org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedforward.BasicFeedforward;
import com.ThermalEquilibrium.homeostasis.Filters.FilterAlgorithms.LowPassFilter;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Dashboard;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Input;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Odometry;

import static org.firstinspires.ftc.teamcode.Math.Controllers.ControlConstants.AngularVelocityTeleop2;
import static org.firstinspires.ftc.teamcode.Math.Controllers.ControlConstants.AngularVelocityTeleopFF;

public class ClosedLoopTeleop extends Command {

	public final double MAX_ANGULAR_VELOCITY = 8; // radians / s
//	SqrtControl control = new SqrtControl(AngularVelocityTeleop);
	BasicPID control = new BasicPID(AngularVelocityTeleop2);
	BasicFeedforward feedforward = new BasicFeedforward(AngularVelocityTeleopFF);
	Drivetrain drivetrain;
	Odometry odom;
	Input gamepad;
	double initialAngularVelocity = 0;
	LowPassFilter filter = new LowPassFilter(0.2);

	public ClosedLoopTeleop(Drivetrain drivetrain,Odometry odom,Input gamepad) {
		super(drivetrain,odom,gamepad);
		this.drivetrain = drivetrain;
		this.odom = odom;
		this.gamepad = gamepad;
	}

	@Override
	public void init() {

	}

	@Override
	public void periodic() {

		double targetVelocity = gamepad.getRight_stick_x() * MAX_ANGULAR_VELOCITY;
		Dashboard.packet.put("target velocity", targetVelocity);
		double estimatedVelocity = filter.estimate(odom.getVelocity().get(2));
		double command = feedforward.calculate(0,targetVelocity,0) + control.calculate(targetVelocity, estimatedVelocity);
		double forward = -gamepad.getLeft_stick_y();
		drivetrain.robotRelative(forward, command);

	}

	@Override
	public boolean completed() {
		return false;
	}

	@Override
	public void shutdown() {

	}

}
