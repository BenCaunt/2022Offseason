package org.firstinspires.ftc.teamcode.Purepursuit.AStar;


import android.os.Build;

import androidx.annotation.RequiresApi;

import org.firstinspires.ftc.teamcode.Purepursuit.CurvePoint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;

import static java.util.Collections.reverse;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AStar {


	final int REASONABLE_INFINITY = 10000;

	final int FIELD_SIZE = 144;
	CurvePoint goal;
	CurvePoint initial;
	HashMap<CurvePoint, CurvePoint> cameFrom = new HashMap<>();
	HashMap<CurvePoint, Double> gScore = new HashMap<>();
	HashMap<CurvePoint, Double> fScore = new HashMap<>();
	PriorityQueue<CurvePoint> openSet = new PriorityQueue<>((point, t1) -> -Double.compare(getFScore(t1), getFScore(point)));

	public CurvePoint[][] field;

	public AStar(CurvePoint goal, CurvePoint initial) {
		this.goal = goal;
		this.initial = initial;
		field = new CurvePoint[FIELD_SIZE + 1][FIELD_SIZE + 1];

		for (int x = -FIELD_SIZE/2; x <= FIELD_SIZE/2; ++x) {
			for (int y = -FIELD_SIZE/2; y <= FIELD_SIZE/2; ++y) {
				field[x+FIELD_SIZE/2][y+FIELD_SIZE/2] = new CurvePoint(x,y);
			}
		}
	}


	public ArrayList<CurvePoint> getNeighbors(CurvePoint point) {

		int [][] directions = {
				{1,0},
				{-1,0},
				{0,1},
				{0,-1},
				{1,1},
				{1,-1},
				{-1,-1},
				{-1,1}
		};

		int indexX = (int)point.x + (FIELD_SIZE / 2);
		int indexY = (int)point.y + (FIELD_SIZE / 2);
		ArrayList<CurvePoint> neighbors = new ArrayList<>();

		for (int[] direction : directions) {
			int newX = indexX + direction[0] ;
			int newY = indexY + direction[1] ;
			if (!(newX < 0 || newX > FIELD_SIZE  || newY < 0 || newY > FIELD_SIZE) ) {
				if (!field[newX][newY].isObstacle()) {
					neighbors.add(new CurvePoint(field[newX][newY]));
				}
			}
		}
		return neighbors;
	}





	/**
	 * straight line distance heuristic function
	 * @param n node we are calculating the weight of
	 * @return weight of n relative to the goal
	 */
	public double H(CurvePoint n) {
		return n.distanceTo(goal);
	}

	public ArrayList<CurvePoint> computeAStar() {
		gScore.put(initial,0.0);
		fScore.put(initial, H(initial));
		openSet.add(initial);


		while (!openSet.isEmpty()) {

			CurvePoint current = openSet.peek();
			assert current != null;
			openSet.remove(current);

			if (current.equals(goal)) {
				return reconstructPath(cameFrom, current);
			}


			ArrayList<CurvePoint> neighbors = getNeighbors(current);

			System.out.println(neighbors.size());

			for (CurvePoint neighbor: neighbors) {
				double tentative_gScore = getGScore(current) + current.distanceTo(neighbor);
				if (tentative_gScore < getGScore(neighbor) && !neighbor.isObstacle()) {
					cameFrom.put(neighbor,current);
					gScore.put(neighbor, tentative_gScore);
					fScore.put(neighbor, tentative_gScore + H(neighbor));
					if (!openSet.contains(neighbor)) {
						openSet.add(neighbor);
					}
				}
			}

		}

		return null;

	}

	protected double getGScore(CurvePoint c){
		if (gScore.containsKey(c)) {
			return Objects.requireNonNull(gScore.get(c));
		}
		return REASONABLE_INFINITY;
	}

	protected double getFScore(CurvePoint c){
		if (fScore.containsKey(c)) {
			return Objects.requireNonNull(fScore.get(c));
		}
		return REASONABLE_INFINITY;
	}

	public void addCircleObstacle(double x, double y, double radius) {
		CurvePoint circleOrigin = new CurvePoint(x,y);
		for (CurvePoint[] row: field) {
			for (CurvePoint point:row) {
				if (point.distanceTo(circleOrigin) < radius) {
					point.setObstacle(true);
				}
			}
		}
	}

	protected ArrayList<CurvePoint> reconstructPath(HashMap<CurvePoint, CurvePoint> cameFrom, CurvePoint current) {
		ArrayList<CurvePoint> reversedPath = new ArrayList<>();
		reversedPath.add(current);
		CurvePoint c = new CurvePoint(current);
		while (cameFrom.containsKey(c)) {
			c = new CurvePoint(Objects.requireNonNull(cameFrom.get(c)));
			reversedPath.add(c);
		}
		// now the path is the correct direction
		reverse(reversedPath);
		return reversedPath;
	}
}
