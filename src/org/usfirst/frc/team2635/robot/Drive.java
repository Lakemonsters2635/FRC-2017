package org.usfirst.frc.team2635.robot;


import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive
{
	RobotDrive robodrive;
	
	public Joystick leftJoystick;
	public Joystick rightJoystick;
	
	public void setMotors(CANTalon frontLeft, CANTalon backLeft, CANTalon frontRight, CANTalon backRight) {
		robodrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
	}
	
	public void setSticks(Joystick leftStick, Joystick rightStick) {
		this.leftJoystick = leftStick;
		this.rightJoystick = rightStick;
	}
	
	public void tankDrive() {
		robodrive.tankDrive(leftJoystick, rightJoystick);
	}
	
	
}
