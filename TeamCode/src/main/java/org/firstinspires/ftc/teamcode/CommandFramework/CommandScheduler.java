package org.firstinspires.ftc.teamcode.CommandFramework;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.CommandFramework.Commands.Command;
import org.firstinspires.ftc.teamcode.CommandFramework.Subsystems.Subsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class CommandScheduler {
    protected HardwareMap hwMap;
    protected ArrayList<Subsystem> subsystems = new ArrayList<Subsystem>();
    protected ArrayList<Command> activeCommands = new ArrayList<Command>();
    protected LinkedList<Command> commandQueue = new LinkedList<Command>();

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

    public void shutdown() {
        for (Subsystem subsystem : subsystems)
            subsystem.shutdown();
    }

    public void handleQueue() {
        if (commandQueue.isEmpty())
            return;

        ArrayList<Subsystem> nextCommandDependencies = commandQueue.peek().getDependencies();

        ArrayList<Subsystem> activeSubsystems = new ArrayList<Subsystem>();
        for (Command command : activeCommands)
            activeSubsystems.addAll(command.getDependencies());

        boolean canAdd = true;
        for (Subsystem subsystem : nextCommandDependencies) {
            if (activeSubsystems.contains(subsystem)) {
                canAdd = false;
                break;
            }
        }

        if (canAdd) {
            Command nextCommand = commandQueue.poll();
            nextCommand.init();

            activeCommands.add(nextCommand);
            handleQueue();
        }
    }

    public void run() {
        Iterator<Command> commands = activeCommands.iterator();

        boolean anyCommandStopped = false;
        while (commands.hasNext()) {
            Command command = commands.next();
            command.periodic();

            if (command.stop()) {
                command.shutdown();
                commands.remove();

                anyCommandStopped = true;
            }
        }

        if (anyCommandStopped)
            handleQueue();
    }

    public void enqueueCommand(Command command) {
        commandQueue.offer(command);
        handleQueue();
    }
}
