package org.firstinspires.ftc.teamcode.Purepursuit;

/**
 * point in our pure pursuit curve that we will actively move toward
 */
public class CurvePoint {

    public double x;
    public double y;
    public double moveSpeed;
    public double turnSpeed;
    public double followDistance;
    public double pointLength;
    public double slowDownTurnRadians;
    public double slowDownTurnAmount;

    public final double DEFAULT_FOLLOW_DIST = 5;

    public CurvePoint(double x, double y, double moveSpeed, double followDistance,
                      double pointLength, double slowDownTurnRadians, double slowDownTurnAmount) {
        this.x = x;
        this.y = y;
        this.moveSpeed = moveSpeed;
        this.followDistance = followDistance;
        this.pointLength = pointLength;
        this.slowDownTurnRadians = slowDownTurnRadians;
        this.slowDownTurnAmount = slowDownTurnAmount;
    }


    public CurvePoint(double x, double y, double moveSpeed) {
        this.x = x;
        this.y = y;
        this.moveSpeed = moveSpeed;
        this.followDistance = DEFAULT_FOLLOW_DIST;
        this.pointLength = 0;
        this.slowDownTurnRadians = 0;
        this.slowDownTurnAmount = 0;
    }
    public CurvePoint(double x, double y, double moveSpeed, double followDistance) {
        this.x = x;
        this.y = y;
        this.moveSpeed = moveSpeed;
        this.followDistance = followDistance;
        this.pointLength = 0;
        this.slowDownTurnRadians = 0;
        this.slowDownTurnAmount = 0;
    }
    public CurvePoint(double x, double y) {
        this.x = x;
        this.y = y;
        this.moveSpeed = 1;
        this.followDistance = DEFAULT_FOLLOW_DIST;
        this.pointLength = 0;
        this.slowDownTurnRadians = 0;
        this.slowDownTurnAmount = 0;
    }

    public CurvePoint(CurvePoint thisPoint) {
        this.x = thisPoint.x;
        this.y = thisPoint.y;
        this.moveSpeed = thisPoint.moveSpeed;
        this.turnSpeed = thisPoint.turnSpeed;
        this.followDistance = thisPoint.followDistance;
        this.pointLength = thisPoint.pointLength;
        this.slowDownTurnRadians = thisPoint.slowDownTurnRadians;
        this.slowDownTurnAmount = thisPoint.slowDownTurnAmount;
    }


    public void setPoint(Point intersection) {
        this.x = intersection.x;
        this.y = intersection.y;
    }

    public boolean equals(CurvePoint other) {
        return this.x == other.x && this.y == other.y;
    }
    public boolean equals(Point other) {
        return this.x == other.x && this.y == other.y;
    }
}
