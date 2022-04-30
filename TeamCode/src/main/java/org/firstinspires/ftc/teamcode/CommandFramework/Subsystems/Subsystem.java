package org.firstinspires.ftc.teamcode.CommandFramework.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class Subsystem {
    public abstract void initAuto(HardwareMap hwMap);

    public void initTeleop(HardwareMap hwMap) {
        initAuto(hwMap);
    }

    public abstract void periodic() throws Exception;

    public abstract void shutdown();
}
