 package org.usfirst.frc.team2635.robot;


import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive
{
	RobotDrive robotDrive;
	
	Joystick leftJoystick;
	Joystick rightJoystick;
	
	public void setMotors(CANTalon leftMotor, CANTalon rightMotor) {
		robotDrive = new RobotDrive(leftMotor, rightMotor);
	}
	
	public void setSticks(Joystick leftStick, Joystick rightStick) {
		this.leftJoystick = leftStick;
		this.rightJoystick = rightStick;
	}
	
	public void tankDrive() {
		robotDrive.tankDrive(leftJoystick, rightJoystick);
	}
	
	
}
