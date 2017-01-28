package org.usfirst.frc.team2635.robot;

public class Shooter
{
	ShooterVision shooterVision;
	public void shootInit(){
		shooterVision= new ShooterVision();
		shooterVision.shooterCamInit();
	}
	
	public void shoot(){
		shooterVision.createBox();
		shooterVision.confirmBox();
		shooterVision.viewShooter();
	}
	
}
