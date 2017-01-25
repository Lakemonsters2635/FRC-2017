package org.usfirst.frc.team2635.robot;
import com.ctre.CANTalon;

public class RobotMotionProfile {

	public MotionProfile frontLeftTalonProfile;
	public MotionProfile frontRightTalonProfile;
	public MotionProfile rearLeftTalonProfile;
	public MotionProfile rearRightTalonProfile;
	
	public RobotMotionProfile (CANTalon frontLeft, CANTalon frontRight, CANTalon rearLeft, CANTalon rearRight)
	{
		frontLeftTalonProfile = new MotionProfile(frontLeft);
		frontRightTalonProfile = new MotionProfile(frontRight);
		rearLeftTalonProfile = new MotionProfile(rearLeft);
		rearRightTalonProfile = new MotionProfile(rearRight);
	}
	
	public void reset() {
		frontLeftTalonProfile.reset();
		frontRightTalonProfile.reset();
		rearLeftTalonProfile.reset();
		rearRightTalonProfile.reset();
	}
	
	public void startMotionProfile(){
		frontLeftTalonProfile.startMotionProfile();
		frontRightTalonProfile.startMotionProfile();
		rearLeftTalonProfile.startMotionProfile();
		rearRightTalonProfile.startMotionProfile();
	}
	
	/**
	 * Called every loop.
	 */
	public void control() {
		frontLeftTalonProfile.control();
		frontRightTalonProfile.control();
		rearLeftTalonProfile.control();
		rearRightTalonProfile.control();
	}
}

