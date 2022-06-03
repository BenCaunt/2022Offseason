package org.firstinspires.ftc.teamcode.Purepursuit.AStar;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.firstinspires.ftc.teamcode.Purepursuit.CurvePoint;

import java.util.ArrayList;

public class Main {


	@RequiresApi(api = Build.VERSION_CODES.N)
	public static void main(String[] args) {

		AStar astar = new AStar(new CurvePoint(30,30), new CurvePoint(0,0));
//		ArrayList<CurvePoint> neighbors = astar.getNeighbors(new CurvePoint(72,0));
		ArrayList<CurvePoint> path = astar.computeAStar();
		if (path.isEmpty()) return;
		for (CurvePoint n: path) {
			System.out.println(n);
		}
	}

}
