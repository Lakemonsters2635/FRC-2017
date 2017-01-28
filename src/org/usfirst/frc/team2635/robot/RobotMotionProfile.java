package org.usfirst.frc.team2635.robot;
import com.ctre.CANTalon;

public class RobotMotionProfile {

	public MotionProfile LeftTalonProfile;
	public MotionProfile RightTalonProfile;

	
	public RobotMotionProfile (CANTalon left, CANTalon right)
	{

		LeftTalonProfile = new MotionProfile(left);
		RightTalonProfile = new MotionProfile(left);
	}
	
	public void reset() {
		LeftTalonProfile.reset();
		RightTalonProfile.reset();

	}
	
	public void startMotionProfile(){
		LeftTalonProfile.startMotionProfile();
		RightTalonProfile.startMotionProfile();

	}
	
	
	public void CaptureRobotStatus()
	{
		LeftTalonProfile.CaptureTalonStatus("leftTaon",true);
		RightTalonProfile.CaptureTalonStatus("rightTalon", true);
	}
	
	

	
	/**
	 * Called every loop.
	 */
	public void control() {
		LeftTalonProfile.control();
		RightTalonProfile.control();

	}
}

