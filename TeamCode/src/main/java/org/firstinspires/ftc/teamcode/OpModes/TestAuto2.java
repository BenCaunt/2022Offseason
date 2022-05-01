package org.firstinspires.ftc.teamcode.OpModes;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandFramework.BaseAuto;
import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandFramework.Commands.Command;

@Autonomous
public class TestAuto2 extends BaseAuto {
	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	public Command setupAuto(CommandScheduler scheduler) {
		Command driveForward = drive(40);
		Command turn = turn(Math.toRadians(180));
		Command driveBack = drive(-40);
		Command finalTurn = turn(Math.toRadians(0));

		driveForward.setNext(turn);
		turn.setNext(driveBack);
		driveBack.setNext(finalTurn);

		Command start = driveForward;

		return start;
	}
}
