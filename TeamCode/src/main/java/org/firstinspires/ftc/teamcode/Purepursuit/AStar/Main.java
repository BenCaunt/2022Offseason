package org.firstinspires.ftc.teamcode.Purepursuit.AStar;

import org.firstinspires.ftc.teamcode.Purepursuit.CurvePoint;

import java.util.ArrayList;

public class Main {


	public static void main(String[] args) {

		AStar astar = new AStar(new CurvePoint(30,30), new CurvePoint(0,0));
		ArrayList<CurvePoint> neighbors = astar.getNeighbors(new CurvePoint(72,0));

		if (neighbors.isEmpty()) return;
		for (CurvePoint n: neighbors) {
			System.out.println(n);
		}
	}

}
