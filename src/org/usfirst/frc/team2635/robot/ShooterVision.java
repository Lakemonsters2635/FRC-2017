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
	Rect confRectTop;
	Rect confRectBot;
	Rect confRectFull;
	
	public void shooterCamInit(){
		GripPipeline = new GripPipeline();
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		cvSink = new CvSink("cvSink");
		cvSink.setSource(camera);
		source = new Mat();
		cvSource = CameraServer.getInstance().putVideo("new", 640, 480);
		Rect confRectTop = new Rect();
		Rect confRectBot = new Rect();
		Rect confRectFull = new Rect();
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
		
		
	}
	
	public void confirmBox(){
		for( int b = 0; b < boundRect.size(); b++ ){
			for (int j = 1; j< boundRect.size(); j++){
			//int j = b;
			if (boundRect.get(b) != null && boundRect.get(j) != null&&b!=j&&j>b){
			
			Rect rect1 = boundRect.get(b);
			Rect rect2 = boundRect.get(j);
			Rect temp = Imgproc.boundingRect(new MatOfPoint(rect1.br(),rect2.tl()));
			int topH;
			int topW;
			int botH;
			int botW;
			//post height of rectangles for debug
			SmartDashboard.putInt("rect1y",rect1.y);
			SmartDashboard.putInt("rect2y", rect2.y);
			//decide which rectangle is top
				if(rect1.y<rect2.y){
					topH = rect1.height;
					topW = rect1.width;
					botH = rect2.height;
					botW = rect2.width;
					//Uncomment to see what box is being tested
					//Imgproc.rectangle( source, rect1.tl(), rect1.br(), new Scalar(0,0,255), 2, 8, 0 );
				}
				else {
					topH = rect2.height;
					topW = rect2.width;
					botH = rect1.height;
					botW = rect1.width;
					//Uncomment to see what box is being tested
					//Imgproc.rectangle( source, rect2.tl(), rect2.br(), new Scalar(0,0,255), 2, 8, 0 );
				}
				//create variables to be used for confirmation
				double topHalfHeight = topH/2;
				double totalHeight = temp.height*0.4;
				//Do checks on rectangle pair
				double comp1 = topHalfHeight/botH;
				double comp2 = totalHeight/topH;
				double comp3 = botW/topW;
				//post used variables
				SmartDashboard.putDouble("topHalfHeight", topHalfHeight);
				SmartDashboard.putDouble("botH", botH);
				//put results of checks
				SmartDashboard.putDouble("comp1", comp1);
				SmartDashboard.putDouble("comp2", comp2);
				SmartDashboard.putDouble("comp3", comp3);
				if (0.85<comp1&&comp1<1.15&&0.85<comp2&&comp2<1.15&&0.85<comp3&&comp3<1.15){
					System.out.println("I found a target!");
					//break out of for loop
					b=10000;
					j=10000;
					//Draw confirmed rectangles
					Imgproc.rectangle( source, rect2.tl(), rect2.br(), new Scalar(0,0,255), 2, 8, 0 );
					Imgproc.rectangle( source, rect1.tl(), rect1.br(), new Scalar(0,0,255), 2, 8, 0 );
					Imgproc.rectangle(source, temp.tl(), temp.br(), new Scalar(0,255,0), 2, 8, 0);
					//create new variables for correct boxes
					confRectFull=temp;
					if(rect1.y<rect2.y){
						confRectTop=rect1;
						confRectBot=rect2;
					} else{
						confRectTop=rect2;
						confRectBot=rect1;
					}
				}
			
				
			}
			
			/*else{
				if(boundRect.get(b) == null || boundRect.get(j) == null){
					//Rectangle is null
					//System.out.println("Rectangle was null");
				} else if(b!=j&&j>b){
					//bad combo
					//System.out.println("Bad combo of rectangles");
				}
			}*/
			}
			//System.out.println("this is confirmation");
		}
		//post size of boundRect arraylist
		SmartDashboard.putInt("boundrect size", boundRect.size());
		
	}
	
	public void viewShooter(){
		//put the processed image with rectangles on smartdashboard
		cvSource.putFrame(source);
	}
	
	public double getDistance(){
		return confRectTop.width;
	}
	
	public double getAngle(){
		return confRectTop.x;
	}
	
}
