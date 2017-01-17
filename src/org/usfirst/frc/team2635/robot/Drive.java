package org.usfirst.frc.team2635.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive
{
	RobotDrive drive;
	
	public Joystick leftJoystick;
	public Joystick rightJoystick;
	
	public void setMotors(CANTalon frontLeft, CANTalon backLeft, CANTalon frontRight, CANTalon backRight) {
		drive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
	}
	
	public void setSticks(Joystick leftStick, Joystick rightStick) {
		this.leftJoystick = leftStick;
		this.rightJoystick = rightStick;
	}
	
	public void tankDrive() {
		drive.tankDrive(leftJoystick, rightJoystick);
	}
	
	
}
