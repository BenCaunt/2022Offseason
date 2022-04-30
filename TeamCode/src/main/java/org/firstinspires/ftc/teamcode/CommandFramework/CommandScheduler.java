package org.firstinspires.ftc.teamcode.CommandFramework;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.CommandFramework.Commands.Command;
import org.firstinspires.ftc.teamcode.CommandFramework.Subsystems.Subsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class CommandScheduler {
    protected HardwareMap hwMap;
    protected ArrayList<Subsystem> subsystems;
    protected ArrayList<Command> activeCommands;

    public CommandScheduler(HardwareMap hardwareMap, Subsystem ...initSubsystems) {
        hwMap = hardwareMap;
        Collections.addAll(subsystems, initSubsystems);
    }

    public void initAuto() {
        for (Subsystem subsystem : subsystems)
            subsystem.initAuto(hwMap);
    }

    public void initTeleop() {
        for (Subsystem subsystem : subsystems)
            subsystem.initTeleop(hwMap);
    }

    public void run() {
        Iterator<Command> commands = activeCommands.iterator();
        while (commands.hasNext()) {
            Command command = commands.next();
            command.periodic();

            if (command.stop()) {
                command.shutdown();
                commands.remove();
            }
        }
    }

    public void schedule() {

    }
}
