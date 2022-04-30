package org.firstinspires.ftc.teamcode.Simulation;

import com.qualcomm.robotcore.util.ElapsedTime;

public class RunSimulation {
    public static void main(String[] args) {
        ElapsedTime timer = new ElapsedTime();

        System.out.println("Running simulation!");

        System.out.println("Time elapsed: " + timer.seconds());
    }
}
