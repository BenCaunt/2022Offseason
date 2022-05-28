package org.firstinspires.ftc.teamcode.Purepursuit;

import static com.qualcomm.robotcore.util.Range.clip;
import static org.firstinspires.ftc.teamcode.Purepursuit.PurepursuitMath.lineCircleIntersection;

import android.util.Range;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.AngleController;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Utils.MathUtils;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Math.Controllers.SqrtControl;
import org.firstinspires.ftc.teamcode.Math.Geometry.Pose2d;
import org.firstinspires.ftc.teamcode.Math.Geometry.Rotation2d;
import org.firstinspires.ftc.teamcode.Robot.ControlConstants;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Dashboard;

import java.util.ArrayList;

public class CurveCalculator {
    public  CurvePoint previousIntersection = new CurvePoint(0,0);
    protected boolean endIsTarget = false;
    SqrtControl angleController = new SqrtControl(ControlConstants.angleControl2);
    AngleController angleControl = new AngleController(angleController);
    BasicPID distanceController = new BasicPID(ControlConstants.distanceControl);

    public double[] getDriveSignal(ArrayList<CurvePoint> allPoints, Pose2d robotPose) {
        double sign = 1;

        CurvePoint target = calculateFollowingPoint(allPoints, robotPose);
        Pose2d targetPose = new Pose2d(target.x,target.y,new Rotation2d(0));
        double angle = robotPose.angleBetween(targetPose);
        double distance = robotPose.distanceBetween(targetPose);
        double headingError = -MathUtils.normalizedHeadingError(
                angle,
                robotPose.getHeading()
        );

        // we only want to have the potential to go backwards if we are at the end so we can stabilize on that pose.
        // otherwise we cannot guarantee which direction the robot is going in.
        // we can guarantee that pure pursuit will be stable up until
        // the end without this because of the constant non zero following distance.
        if (endIsTarget) {
            if (trajectoryFollowingSign(headingError) < 0) {
                headingError = -MathUtils.normalizedHeadingError(
                        angle,
                        robotPose.getHeading()
                );
                sign = -1;
            }

        }
        double turnSpeed = angleControl.calculate(0, headingError);

        double forwardSpeed = -distanceController.calculate(
                0,
                distance
        ) * sign;


        double headingScale = Math.abs(Math.cos(headingError));

        forwardSpeed = clip(forwardSpeed,-1,1);
        turnSpeed = clip(turnSpeed,-1,1);

        return new double[] {
                forwardSpeed * target.moveSpeed,
                turnSpeed * headingScale * target.moveSpeed
        };

    }

    public CurvePoint calculateFollowingPoint(ArrayList<CurvePoint> allPoints, Pose2d robotPose) {
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

        return followMe;

    }


    protected CurvePoint getFollowPointPath(ArrayList<CurvePoint> pathPoints, Pose2d robotPose, double followRadius) {
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
        endIsTarget = followMe.equals(pathPoints.get(pathPoints.size()-1));
        previousIntersection = new CurvePoint(followMe);
        return followMe;

    }

    public double trajectoryFollowingSign(double headingError) {
        if (Math.abs(headingError) > Math.toRadians(90))
            return -1;
        return 1;
    }

}
