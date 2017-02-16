package org.usfirst.frc.team2635.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;

public class BallPickup {
	public int clockwiseID;
	public int counterClockwiseID;
	public int motorVoltage;
	
	CANTalon motor;
	Joystick stick;
	
	public void init(CANTalon motor, Joystick stick, int clockwiseID, int counterClockwiseID, int motorVoltage) {
		this.motor = motor;
		this.stick = stick;
		this.clockwiseID = clockwiseID;
		this.counterClockwiseID = counterClockwiseID;
		this.motorVoltage = motorVoltage;
		
	}
	
	public void ballPickup(boolean on, boolean forward) {
		if(on && forward) {
			motor.set(motorVoltage);
		} 
		else if(!on) {
			motor.set(0);
		} 
		else if(on && !forward) {
			motor.set(-motorVoltage);
		}
	}
}