 package org.usfirst.frc.team2635.robot;


import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive
{
	RobotDrive robotDrive;
	
	Joystick leftJoystick;
	Joystick rightJoystick;
	
	public void init(Joystick leftStick, Joystick rightStick, CANTalon leftMotor, CANTalon rightMotor) {
		this.leftJoystick = leftStick;
		this.rightJoystick = rightStick;
		robotDrive = new RobotDrive(leftMotor, rightMotor);
	}
	public void tankDrive() {
		robotDrive.tankDrive(leftJoystick, rightJoystick);
	}
	
	
}
