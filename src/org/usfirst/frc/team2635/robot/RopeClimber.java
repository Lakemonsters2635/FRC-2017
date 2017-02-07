package org.usfirst.frc.team2635.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;

public class RopeClimber {
	CANTalon motor;
	Joystick stick;
	
	public void setMotor(CANTalon motor) {
		this.motor = motor;
	}
	
	public void setStick(Joystick stick) {
		this.stick = stick;
	}
}
