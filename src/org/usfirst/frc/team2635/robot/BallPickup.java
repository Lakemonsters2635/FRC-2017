package org.usfirst.frc.team2635.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;

public class BallPickup {
	public static final int CLOCKWISE_BUTTON_ID = 1;
	public static final int COUNTER_CLOCKWISE_BUTTON_ID = 2;
	public static final int MOTOR_VOLTAGE = 6;
	
	CANTalon motor;
	Joystick stick;
	
	public void setMotor(CANTalon motor) {
		this.motor = motor;
	}
	
	public void setStick(Joystick stick) {
		this.stick = stick;
	}
	
	public void ballPickup() {
		if(stick.getRawButton(CLOCKWISE_BUTTON_ID) && !stick.getRawButton(COUNTER_CLOCKWISE_BUTTON_ID)) {
			motor.set(MOTOR_VOLTAGE);
		} 
		else if(!stick.getRawButton(CLOCKWISE_BUTTON_ID) && !stick.getRawButton(COUNTER_CLOCKWISE_BUTTON_ID)) {
			motor.set(0);
		} 
		else if(!stick.getRawButton(CLOCKWISE_BUTTON_ID) && stick.getRawButton(COUNTER_CLOCKWISE_BUTTON_ID)) {
			motor.set(-MOTOR_VOLTAGE);
		}
	}
}