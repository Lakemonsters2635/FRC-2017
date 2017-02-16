package org.usfirst.frc.team2635.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;

public class RopeClimber {
	public int buttonID;
	public int motorVoltage;
	CANTalon motor;
	Joystick stick;
	
	public void init(CANTalon motor, Joystick stick, int buttonID, int motorVoltage) {
		this.motor = motor;
		this.stick = stick;
		this.buttonID = buttonID;
		this.motorVoltage = motorVoltage;
	}
	
	public void ropeClimb() {
		if (stick.getRawButton(buttonID)) {
			motor.set(motorVoltage);
		} else {
			motor.set(0);
		}
	}
}
