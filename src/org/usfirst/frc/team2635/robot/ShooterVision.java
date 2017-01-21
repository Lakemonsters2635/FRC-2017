package org.usfirst.frc.team2635.robot;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class ShooterVision {
	CvSink cvSink;
	Mat source;
	GripPipeline GripPipeline;
	CvSource cvSource;
	
	public void shooterCamInit(){
		GripPipeline = new GripPipeline();
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		cvSink = new CvSink("cvSink");
		source = new Mat();
	}
	
	public void createBox(){
		cvSink.grabFrame(source);
		GripPipeline.process(source);
		cvSource.putFrame(source);
		cvSource = CameraServer.getInstance().putVideo("new", 640, 480);
		
		
		
		
		
		
		
	}
	
	public double getDistance(){
		return 01;
	}
	
	public double getAngle(){
		return 01;
	}
	
}
