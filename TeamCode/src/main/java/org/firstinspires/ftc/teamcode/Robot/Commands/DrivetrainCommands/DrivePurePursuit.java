package org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands;

import static org.firstinspires.ftc.teamcode.Purepursuit.RobotMovementCalculations.followCurve;

import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.Purepursuit.CurvePoint;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Robot;

import java.util.ArrayList;

public class DrivePurePursuit extends Command {

    Robot robot;
    ArrayList<CurvePoint> points;

    public DrivePurePursuit(Robot robot, ArrayList<CurvePoint> points) {
        this.robot = robot;
        this.points = points;
    }


    @Override
    public void init() {

    }

    @Override
    public void periodic() {

        followCurve(points, robot.odometry.getPose());

    }

    @Override
    public boolean completed() {
        return false;
    }

    @Override
    public void shutdown() {

    }
}
