package org.usfirst.frc.team2635.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;

public class RopeClimber {
	public static final int CLIMB_BUTTON_ID = 1;
	CANTalon motor;
	Joystick stick;
	
	public void setMotor(CANTalon motor) {
		this.motor = motor;
	}
	
	public void setStick(Joystick stick) {
		this.stick = stick;
	}
	
	public void ropeClimb() {
		if (stick.getRawButton(CLIMB_BUTTON_ID)) {
			motor.set(6);
		} else {
			motor.set(0);
		}
	}
}
