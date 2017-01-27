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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterVision {
	CvSink cvSink;
	Mat source;
	GripPipeline GripPipeline;
	CvSource cvSource;
	ArrayList<Rect> boundRect;
	ArrayList<MatOfPoint> grip;
	
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
		grip = GripPipeline.findContoursOutput();
		for( int i = 0; i< grip.size(); i++ ){
			Imgproc.drawContours(source, grip, i, new Scalar(0, 255,0),1);	
			boundRect.add(Imgproc.boundingRect(grip.get(i)));
			Rect rect = Imgproc.boundingRect(grip.get(i));
			Imgproc.rectangle( source, rect.tl(), rect.br(), new Scalar(0,0,255), 2, 8, 0 );
		    }
		cvSource.putFrame(source);
		
		
	}
	
	public void confirmBox(){
		for( int b = 0; b < grip.size()-1; b = b+2 ){
			if (boundRect.get(b) != null && boundRect.get(b+1) != null){
			Rect rect1 = boundRect.get(b);
			Rect rect2 = boundRect.get(b+1);
			Rect temp = Imgproc.boundingRect(new MatOfPoint(rect1.tl(),rect2.br()));
			
				int topH = rect1.height;
				int topW = rect1.width;
				int botH = rect2.height;
				int botW = rect2.width;
				
				int topHalfHeight = topH/2;
				float comp1 = topHalfHeight/botH;
				double totalHeight = temp.height*0.4;
				double comp2 = totalHeight/topH;
				SmartDashboard.putDouble("hi", comp2);
				if (0.95<comp1&&comp1<1.05&&0.95<comp2&&comp2<1.05){
					System.out.println("I found a target!");
					b=10000;
				}
				
				
			}
			else{
				System.out.println("Rectangle was null");
			}
			System.out.println("this is confirmation");
		}
		SmartDashboard.putInt("hi", boundRect.size());
	}
	
	public double getDistance(){
		return 01;
	}
	
	public double getAngle(){
		return 01;
	}
	
}
