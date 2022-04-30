package org.firstinspires.ftc.teamcode.Simulation;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.CommandFramework.CommandScheduler;
import org.firstinspires.ftc.teamcode.Simulation.TestCommandsSubsystems.PrintCommand1;
import org.firstinspires.ftc.teamcode.Simulation.TestCommandsSubsystems.PrintSubsystem1;

public class RunSimulation {
    public static void main(String[] args) {
        PrintSubsystem1 printSub = new PrintSubsystem1();
        PrintCommand1 printCommand1 = new PrintCommand1(printSub, "Test 1");
        PrintCommand1 printCommand2 = new PrintCommand1(printSub, "Test 2");

        CommandScheduler scheduler = new CommandScheduler(null, printSub);
        scheduler.initAuto();

        scheduler.enqueueCommand(printCommand1);
        scheduler.enqueueCommand(printCommand2);

        scheduler.run();
        scheduler.run();

        scheduler.shutdown();
    }
}
