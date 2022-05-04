package org.firstinspires.ftc.teamcode.OpModes;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ThermalEquilibrium.homeostasis.Utils.Vector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.sun.tools.javac.util.List;

import org.firstinspires.ftc.teamcode.CommandFramework.BaseAuto;
import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.Math.AsymmetricProfile.DirectTrajectory;
import org.firstinspires.ftc.teamcode.Math.Geometry.Pose2d;
import org.firstinspires.ftc.teamcode.Math.Geometry.Rotation2d;
import org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands.DriveTrajectory;
import org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands.QuasiStaticVelocity;

import java.util.ArrayList;

@Autonomous
public class QuasiStaticVelocityTest extends BaseAuto {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Command setupAuto(CommandScheduler scheduler) {
        robot.odometry.setEstimate(new Vector(new double[]{0, 0, 0}));

        QuasiStaticVelocity quasiStaticTest = new QuasiStaticVelocity(robot.drivetrain, robot.odometry, 0.25, 10);

        return quasiStaticTest;
    }
}
