package org.usfirst.frc.team2635.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
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
	DoubleSolenoid sol2;
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
			sol2 = new DoubleSolenoid(2,3);
		}
		catch(Exception err)
		{
			isInitialized = false;
		}
	}
	
	public void setFlywheel(CANTalon motor) { 
		flywheel = motor;
	}
	
	public void setDoor(CANTalon motor){
		door = motor;
		//Change door to whatever is used to control pneumatics
	}
	
	public void setStick(Joystick givenStick) {
		stick = givenStick;
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
				sol2.set(DoubleSolenoid.Value.kForward);
				prevmode = 2;
				doorState = doorEnum.OUT;
			}
			else if (prevmode == 1){
				sol2.set(DoubleSolenoid.Value.kReverse);
				prevmode = 2;
				doorState = doorEnum.IN;
				
			} 
			else {
				sol2.set(DoubleSolenoid.Value.kOff);
			}
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
			//Already running flyweel
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
