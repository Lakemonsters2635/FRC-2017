package org.usfirst.frc.team2635.robot;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class ShooterVision {
	CvSink cvSink;
	Mat source;
	GripPipeline GripPipeline;
	CvSource cvSource;
	MatOfPoint new_mat1;
	
	public void shooterCamInit(){
		GripPipeline = new GripPipeline();
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		cvSink = new CvSink("cvSink");
		source = new Mat();
		cvSource = CameraServer.getInstance().putVideo("new", 640, 480);
	}
	
	public void createBox(){
		cvSink.grabFrame(source);
		GripPipeline.process(source);
		new_mat1 = new MatOfPoint(source);
		Imgproc.boundingRect(new_mat1);
		cvSource.putFrame(new_mat1);
		
	}
	
	public double getDistance(){
		return 01;
	}
	
	public double getAngle(){
		return 01;
	}
	
}
