package org.firstinspires.ftc.teamcode.CommandFramework.Commands;

import org.firstinspires.ftc.teamcode.CommandFramework.Subsystems.Subsystem;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Command {
    protected Command nextCommand = null;

    public void addNext(Command command) { nextCommand = command; }

    public Command getNext() { return nextCommand; }

    protected ArrayList<Subsystem> dependencies = new ArrayList<Subsystem>();

    public ArrayList<Subsystem> getDependencies() {
        return dependencies;
    }

    public Command(Subsystem ...subsystems) {
        Collections.addAll(dependencies, subsystems);
    }

    public abstract void init();

    public abstract void periodic();

    public abstract boolean stop();

    public abstract void shutdown();
}
