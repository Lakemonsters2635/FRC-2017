package org.usfirst.frc.team2635.robot;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
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
	ArrayList<Rect> boundRect;
	
	public void shooterCamInit(){
		GripPipeline = new GripPipeline();
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		cvSink = new CvSink("cvSink");
		cvSink.setSource(camera);
		source = new Mat();
		cvSource = CameraServer.getInstance().putVideo("new", 640, 480);
	}
	
	public void createBox(){
		boundRect = new ArrayList<Rect>();
		cvSink.grabFrame(source);
		GripPipeline.process(source);
		ArrayList<MatOfPoint> grip = GripPipeline.findContoursOutput();
		for( int i = 0; i< grip.size(); i++ ){
			Imgproc.drawContours(source, grip, i, new Scalar(0, 255,0),1);	
			boundRect.add(Imgproc.boundingRect(grip.get(i)));
			Rect rect = Imgproc.boundingRect(grip.get(i));
			Imgproc.rectangle( source, rect.tl(), rect.br(), new Scalar(0,0,255), 2, 8, 0 );
		    }
		cvSource.putFrame(source);
		
		
	}
	
	public double getDistance(){
		return 01;
	}
	
	public double getAngle(){
		return 01;
	}
	
}
