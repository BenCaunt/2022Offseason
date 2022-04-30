package org.firstinspires.ftc.teamcode.CommandFramework.Subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Dashboard extends Subsystem  {


	public static TelemetryPacket packet = new TelemetryPacket();
	FtcDashboard dashboard = FtcDashboard.getInstance();
	ElapsedTime dashboardTimer = new ElapsedTime();

	@Override
	public void initAuto(HardwareMap hwMap) {
		dashboard.sendTelemetryPacket(packet);
		packet = new TelemetryPacket();
	}

	@Override
	public void periodic() {
		dashboard.sendTelemetryPacket(packet);
		packet = new TelemetryPacket();
		packet.put("Loop time",dashboardTimer.milliseconds());
		dashboardTimer.reset();
	}

	@Override
	public void shutdown() {
	}
}
