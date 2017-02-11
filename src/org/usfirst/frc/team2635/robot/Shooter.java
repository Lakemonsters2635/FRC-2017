package org.usfirst.frc.team2635.robot;

public class Shooter
{
	enum shootEnum {INIT, SHOOTING, RESET}
	shootEnum shootState = shootEnum.INIT;
	ShooterVision shooterVision;
	Vision vision;
	public void shootInit(){
		shooterVision = new ShooterVision();
		vision = new Vision();
		vision.camInit();
	}
	
	public void flywheelStart(){
		//code to run fly wheel or have in separate class
	}
	
	public void shoot(){
		shooterVision.createBox();
		shooterVision.confirmBox();
		shooterVision.viewShooter();
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
			//Start agitator
			//Already running fly wheel
			}
			else {
				shootState=shootEnum.RESET;
			}
			break;
		case RESET:
			//Close door
			//Stop agitator
			break;
		default:
			break;
		}
	}
	//NOTE: Change shoot() to a state, like shoot in bunnybots, as to make capable of firing separate balls and better control
}
