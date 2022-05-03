package org.firstinspires.ftc.teamcode.Utils;

import com.ThermalEquilibrium.homeostasis.Utils.Vector;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.teamcode.Math.Geometry.Pose2d;
import org.firstinspires.ftc.teamcode.Math.Geometry.Rotation2d;
import org.firstinspires.ftc.teamcode.Math.Geometry.Vector2d;


public class ExtraUtils {

	/**
	 * rotate a 2 item vector by an angle radians
	 * @param v two item vector, x:0, y:1
	 * @param angleRadians angle in radians
	 */
	public static void rotate(Vector v, double angleRadians) {
		double cosA = Math.cos(angleRadians);
		double sinA = Math.sin(angleRadians);

		double x = v.get(0) * cosA - v.get(1) * sinA;
		double y = v.get(0) * sinA - v.get(1) * cosA;

		v.set(x,0);
		v.set(y,1);
	}

	public static double calculateDistance(Vector v1, Vector v2) {
		double x1 = v1.get(0);
		double x2 = v2.get(0);
		double y1 = v1.get(1);
		double y2 = v2.get(1);
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	public static double atan2(Vector v) {
		return Math.atan2(v.get(1), v.get(0));
	}


	public static final double FIELD_SCALE_FACTOR = 3;

	public static void drawRobot(Vector position, TelemetryPacket packet) {

		Pose2d pose = new Pose2d(position.get(0),position.get(1),new Rotation2d(position.get(2)));
		double ROBOT_RADIUS = 8;
		Vector2d v = new Vector2d(Math.cos(position.get(2)), Math.sin(position.get(2))).times(ROBOT_RADIUS);

		double x1 = pose.getX() + v.getX() / 2;
		double y1 = pose.getY() + v.getY() / 2;
		double x2 = pose.getX() + v.getX();
		double y2 = pose.getY() + v.getY();


		packet.fieldOverlay()
				.setStroke("black")
				.strokeCircle(pose.getX() / FIELD_SCALE_FACTOR, pose.getY() / FIELD_SCALE_FACTOR, ROBOT_RADIUS)
				.strokeLine(x1 / FIELD_SCALE_FACTOR, y1 / FIELD_SCALE_FACTOR, x2 / FIELD_SCALE_FACTOR, y2 / FIELD_SCALE_FACTOR);


	}
	public static void drawRobotTarget(Vector position, TelemetryPacket packet) {

		Pose2d pose = new Pose2d(position.get(0),position.get(1),new Rotation2d(position.get(2)));
		double ROBOT_RADIUS = 8;
		Vector2d v = new Vector2d(Math.cos(position.get(2)), Math.sin(position.get(2))).times(ROBOT_RADIUS);

		double x1 = pose.getX() + v.getX() / 2;
		double y1 = pose.getY() + v.getY() / 2;
		double x2 = pose.getX() + v.getX();
		double y2 = pose.getY() + v.getY();


		packet.fieldOverlay()
				.setStroke("Green")
				.strokeCircle(pose.getX(), pose.getY(), ROBOT_RADIUS)
				.strokeLine(x1, y1, x2, y2);


	}
	public static void drawRobotTarget(Pose2d pose, TelemetryPacket packet) {

		double ROBOT_RADIUS = 8;
		Vector2d v = new Vector2d(Math.cos(pose.getHeading()), Math.sin(pose.getHeading())).times(ROBOT_RADIUS);

		double x1 = pose.getX() + v.getX() / 2;
		double y1 = pose.getY() + v.getY() / 2;
		double x2 = pose.getX() + v.getX();
		double y2 = pose.getY() + v.getY();


		packet.fieldOverlay()
				.setStroke("Green")
				.strokeCircle(pose.getX() / FIELD_SCALE_FACTOR, pose.getY() / FIELD_SCALE_FACTOR, ROBOT_RADIUS)
				.strokeLine(x1 / FIELD_SCALE_FACTOR, y1 / FIELD_SCALE_FACTOR, x2 / FIELD_SCALE_FACTOR, y2 / FIELD_SCALE_FACTOR);


	}
}
