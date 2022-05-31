package org.firstinspires.ftc.teamcode.OpModes;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandFramework.BaseAuto;
import org.firstinspires.ftc.teamcode.CommandFramework.Command;
import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.Purepursuit.CurvePoint;
import org.firstinspires.ftc.teamcode.Robot.Commands.DrivetrainCommands.DrivePurePursuit;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Autonomous
public class PurePursuitTest extends BaseAuto {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Command setupAuto(CommandScheduler scheduler) {
        ArrayList<CurvePoint> points1 = new ArrayList<>();
        points1.add(new CurvePoint(0,0,0));
        points1.add(new CurvePoint(10,10,0.5,10));
        points1.add(new CurvePoint(20,30,1,10));
        points1.add(new CurvePoint(40,30,1,10));
        points1.add(new CurvePoint(40,60,0.3,8));


        ArrayList<CurvePoint> points2 = new ArrayList<>();
        points2.add(new CurvePoint(40,60,0.2,10));
        points2.add(new CurvePoint(40,10,1,10));
        points2.add(new CurvePoint(0,0,0.5,10));

        Command auto = new DrivePurePursuit(robot,points1)
                .addNext(new DrivePurePursuit(robot, points2))
                .addNext(turn(0));

        return auto;
    }
}
