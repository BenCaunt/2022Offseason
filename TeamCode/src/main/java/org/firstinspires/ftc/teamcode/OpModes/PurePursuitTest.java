package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandFramework.BaseAuto;
import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.Purepursuit.CurvePoint;
import org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands.DrivePurePursuit;

import java.util.ArrayList;

@Autonomous
public class PurePursuitTest extends BaseAuto {
    @Override
    public Command setupAuto(CommandScheduler scheduler) {
        ArrayList<CurvePoint> points = new ArrayList<>();
        points.add(new CurvePoint(0,0));
        points.add(new CurvePoint(10,10));
        points.add(new CurvePoint(20,30));
        Command auto = new DrivePurePursuit(robot,points);
        return auto;
    }
}
