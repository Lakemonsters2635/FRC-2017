package org.usfirst.frc.team2635.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;

public class Shooter
{
	
	public boolean isInitialized;
	enum shootEnum {INIT, SHOOTING, RESET}
	shootEnum shootState = shootEnum.INIT;
	enum doorEnum {IN, CHANGING, OUT}
	doorEnum doorState = doorEnum.IN;
	ShooterVision shooterVision;
	Vision vision;
	int prevmode;
	CANTalon flywheel;
	CANTalon door;
	Joystick stick;
	public static final int FLYWHEEL_BUTTON = 0;
	public static final int DOOR_BUTTON = 1;
	public void shootInit(){
		
		try
		{
			shooterVision = new ShooterVision();
			vision = new Vision();
			vision.camInit();
			shooterVision.createBox();
			isInitialized = true;
		}
		catch(Exception err)
		{
			isInitialized = false;
		}
	}
	
	public void setFlywheel(CANTalon motor) {
		this.flywheel = motor;
	}
	
	public void setDoor(CANTalon motor){
		this.door = motor;
		//Change door to whatever is used to control pneumatics
	}
	
	public void setStick(Joystick stick) {
		this.stick = stick;
	}
	
	public void flywheel(){
		if (stick.getRawButton(FLYWHEEL_BUTTON)) {
			flywheel.set(6);
		}
		else{
			flywheel.set(0);
		}
	}
	
	public void door(Boolean button){
		//Change door to whatever is used to control pneumatics
		switch(doorState){
		case IN:
			prevmode = 0;
			if (stick.getRawButton(DOOR_BUTTON)) doorState = doorEnum.CHANGING;
			
			break;
			
		case CHANGING:
			if (prevmode == 0){
				//push door out
				doorState = doorEnum.OUT;
			}
			else if (prevmode == 1){
				//pull door in
				doorState = doorEnum.IN;
			} 
			else {
				//Well sh*t I didn't think this would happen
			}
			//NOTES: If there is a switch like at bunny bots we will need to use that
			break;
			
		case OUT:
			prevmode = 1;
			if (stick.getRawButton(DOOR_BUTTON)) doorState = doorEnum.CHANGING;
			
			break;
		}
			
		
	}
	
	public void shoot(){
		
		if (isInitialized)
		{
			shooterVision.createBox();
			shooterVision.confirmBox();
			shooterVision.viewShooter();
		}
	}
	
	public void shootUpdate(boolean button){
		switch(shootState){
		case INIT:
			if (button){
				shootState=shootEnum.SHOOTING;
			}
			break;
		case SHOOTING:
			if(button /*||in progress of moving door*/){
			//Open door
			//Already running flywheel
			}
			else {
				shootState=shootEnum.RESET;
			}
			break;
		case RESET:
			//Close door
			break;
		default:
			break;
		}
	}
	//NOTE: Change shoot() to a state, like shoot in bunnybots, as to make capable of firing separate balls and better control
}
