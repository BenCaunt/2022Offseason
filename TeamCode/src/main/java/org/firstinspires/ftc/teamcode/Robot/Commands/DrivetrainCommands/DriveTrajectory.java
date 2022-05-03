package org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands;

import static org.firstinspires.ftc.teamcode.Utils.ExtraUtils.drawRobotTarget;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.AngleController;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Utils.MathUtils;
import com.ThermalEquilibrium.homeostasis.Utils.Vector;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.Math.AsymmetricProfile.DirectTrajectory;
import org.firstinspires.ftc.teamcode.Math.Controllers.ControlConstants;
import org.firstinspires.ftc.teamcode.Math.Controllers.SqrtControl;
import org.firstinspires.ftc.teamcode.Math.Controllers.TurnOnlyControl;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Dashboard;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Odometry;

public class DriveTrajectory extends Command {
    Drivetrain drivetrain;
    Odometry odometry;

    DirectTrajectory trajectory;
//    TurnOnlyControl turnController;
    SqrtControl angleController = new SqrtControl(ControlConstants.angleControl);
    BasicPID angleController2 = new BasicPID(ControlConstants.AngularVelocityTeleop);
    AngleController angleControl = new AngleController(angleController2);
    BasicPID distanceController = new BasicPID(ControlConstants.distanceControl);
    ElapsedTime timer;

    public DriveTrajectory(Drivetrain drivetrain, Odometry odometry, DirectTrajectory trajectory) {
        super(drivetrain, odometry);

        this.drivetrain = drivetrain;
        this.odometry = odometry;
        this.trajectory = trajectory;
        this.timer = new ElapsedTime();

//        this.turnController = new TurnOnlyControl(() -> odometry.getPosition().get(2), trajectory.targetPose(timer.seconds()).getHeading());
    }

    @Override
    public void init() { }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void periodic() {
//        double headingErrorPre = MathUtils.normalizedHeadingError(
//                odometry.getPose().getHeading(),
//                odometry.getPose().angleBetween(trajectory.targetPose(timer.seconds()))
//        );
        double headingErrorPre = MathUtils.normalizedHeadingError(
                odometry.getPose().getHeading(),
                odometry.getPose().angleBetween(trajectory.targetPose(timer.seconds()))
        );

        double headingError = headingErrorPre;

        if (trajectoryFollowingSign(headingErrorPre) < 0)
            headingError = MathUtils.normalizedHeadingError(
                    odometry.getPose().rotate(Math.toRadians(180)).getHeading(),
                    odometry.getPose().rotate(Math.toRadians(180)).angleBetween(trajectory.targetPose(timer.seconds()))
            );
//
        double turnSpeed = angleControl.calculate(0, headingError);

        double forwardSpeed = -distanceController.calculate(
                0,
                odometry.getPose().distanceBetween(trajectory.targetPose(timer.seconds()))
        ) * trajectoryFollowingSign(headingError);
        forwardSpeed = Range.clip(forwardSpeed, 0, 0.5);

        double headingScale = Range.clip(Math.cos(headingError), 0, 1);

//        Vector turnSpeeds = turnController.calculate();
//
//        drivetrain.setPower(
//                forwardSpeed * headingScale + turnSpeeds.get(0),
//                forwardSpeed * headingScale + turnSpeeds.get(1)
//        );
        drivetrain.robotRelative(forwardSpeed * headingScale, turnSpeed);
        Dashboard.packet.put("Current Pose", odometry.getPose());
        Dashboard.packet.put("Target Pose", trajectory.targetPose(timer.seconds()));
        Dashboard.packet.put("Time", timer.seconds());
        Dashboard.packet.put("Distance Between", odometry.getPose().distanceBetween(trajectory.targetPose(timer.seconds())));
        Dashboard.packet.put("Angle Between", odometry.getPose().angleBetween(trajectory.targetPose(timer.seconds())));
//        Dashboard.packet.put("Angle Between", headingError);

        drawRobotTarget(trajectory.targetPose(timer.seconds()), Dashboard.packet);
//        drivetrain.setPower(
//                forwardSpeed * headingScale + turnSpeed,
//                forwardSpeed * headingScale - turnSpeed
//        );
    }

    public double trajectoryFollowingSign(double headingError) {
        if (Math.abs(headingError) > Math.toRadians(90))
            return -1;
        else
            return 1;
    }

    @Override
    public boolean completed() {
//        return timer.seconds() > trajectory.endTime && odometry.getPose().distanceBetween(trajectory.targetPose(timer.seconds())) < 1;
        return false;
    }

    @Override
    public void shutdown() {
        drivetrain.setPower(0,0);
    }
}
