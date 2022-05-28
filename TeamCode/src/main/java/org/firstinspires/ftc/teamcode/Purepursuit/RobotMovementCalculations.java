package org.firstinspires.ftc.teamcode.Purepursuit;

import static org.firstinspires.ftc.teamcode.Purepursuit.PurepursuitMath.lineCircleIntersection;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Math.Geometry.Pose2d;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Dashboard;

import java.util.ArrayList;

public class RobotMovementCalculations {


    public static CurvePoint previousIntersection = new CurvePoint(0,0);

    public static void followCurve(ArrayList<CurvePoint> allPoints, Pose2d robotPose) {
        for (int i = 0; i < allPoints.size()-1; ++i) {
            Dashboard.packet.fieldOverlay()
                    .strokeLine(allPoints.get(i).x,allPoints.get(i).y,
                            allPoints.get(i + 1).x,allPoints.get(i + 1).y);
        }

        CurvePoint followMe = getFollowPointPath(allPoints,robotPose,allPoints.get(0).followDistance);

        Dashboard.packet.fieldOverlay()
                .setStroke("Orange")
                .setFill("Orange")
                .strokeCircle(followMe.x, followMe.y, 4);

        Dashboard.packet.fieldOverlay()
                .setStroke("Blue")
                .setFill("Blue")
                .strokeCircle(robotPose.getX(),robotPose.getY(),followMe.followDistance);

    }

    public static CurvePoint getFollowPointPath(ArrayList<CurvePoint> pathPoints, Pose2d robotPose, double followRadius) {
        CurvePoint followMe = new CurvePoint(previousIntersection);
        ArrayList<Point> allIntersections = new ArrayList<>();
        int indexAtLastIntersectionUpdate = 0;
        boolean intersectionsOccurBeforeFinalPoint = false;
        for (int i = 0; i < pathPoints.size() - 1; ++i) {
            CurvePoint startLine = pathPoints.get(i);
            CurvePoint endLine = pathPoints.get(i + 1);

            ArrayList<Point> intersections = lineCircleIntersection(robotPose.getX(), robotPose.getY(), followRadius, startLine.x, startLine.y, endLine.x, endLine.y);

            double closestAngle = 1000000;

            for (Point intersection : intersections) {
                if (indexAtLastIntersectionUpdate == pathPoints.size() - 2) {
                    intersectionsOccurBeforeFinalPoint = true;
                }
                double angle = Math.atan2(intersection.y - robotPose.getY(), intersection.x - robotPose.getX());
                double deltaAngle = Math.abs(AngleUnit.normalizeRadians(angle - robotPose.getHeading()));
                if (deltaAngle < closestAngle) {
                    closestAngle = deltaAngle;
                    followMe.setPoint(intersection);
                    indexAtLastIntersectionUpdate = i;
                }
                allIntersections.add(intersection);
            }



        }
        if (allIntersections.size() == 1 && indexAtLastIntersectionUpdate == pathPoints.size() - 2 && !intersectionsOccurBeforeFinalPoint) {
            followMe = pathPoints.get(pathPoints.size() - 1);
        }
        previousIntersection = new CurvePoint(followMe);
        return followMe;

    }

    public static double[] goToPosition(double x, double y, double movementSpeed, double preferredAngle, double turnSpeed) {


        return new double[] {0,0};
    }

}
