package org.usfirst.frc.team2635.robot;

public class ShooterAim {
	ShooterVision shooterVision;
	public void aimInit(){
		shooterVision = new ShooterVision();
		shooterVision.camInit();
	}
	
	public void aim(){
		shooterVision.createBox();
		shooterVision.confirmBox();
		shooterVision.viewShooter();
	}
	
	public double getAngle(){
		double angle = shooterVision.getAngle();
		//get distance from point decided after robot built
		return angle;
	}
	
	public double getDistance(){
		double distance = shooterVision.getDistance();
		//get distance from point decided after robot built
		return distance;
	}
	
	
	
	
}