package org.usfirst.frc.team2635.robot;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearVision extends Vision {
	Rect confRectLeft;
	Rect confRectRight;
	Rect confRectFull;
	
	@SuppressWarnings("deprecation")
	public void confirmBox(){
		for( int b = 0; b < boundRect.size(); b++ ){
			for (int j = 1; j< boundRect.size(); j++){
			//int j = b;
			if (boundRect.get(b) != null && boundRect.get(j) != null&&b!=j&&j>b){
			
			Rect rect1 = boundRect.get(b);
			Rect rect2 = boundRect.get(j);
			Rect temp;
			int leftH;
			int leftW;
			int rightH;
			int rightW;
			int check;
			//Post height of rectangles for debug
			SmartDashboard.putInt("rect1y",rect1.y);
			SmartDashboard.putInt("rect2y", rect2.y);
			//Decide which rectangle is top
				if(rect1.x>rect2.x){
					leftH = rect1.height;
					leftW = rect1.width;
					rightH = rect2.height;
					rightW = rect2.width;
					temp = Imgproc.boundingRect(new MatOfPoint(rect2.br(),rect1.tl()));
					//Uncomment to see what box is being tested
					//Imgproc.rectangle( source, rect1.tl(), rect1.br(), new Scalar(0,0,255), 2, 8, 0 );
					check = 0;
				}
				else {
					leftH = rect2.height;
					leftW = rect2.width;
					rightH = rect1.height;
					rightW = rect1.width;
					temp = Imgproc.boundingRect(new MatOfPoint(rect1.br(),rect2.tl()));
					//Uncomment to see what box is being tested
					//Imgproc.rectangle( source, rect2.tl(), rect2.br(), new Scalar(0,0,255), 2, 8, 0 );
					check = 1;
				}
				//Create variables to be used for confirmation
				
				//Do checks on rectangle pair
				double comp1 = leftH/rightH;
				double comp2 = leftW/rightW;
				double comp3;
				if(check==0){
					double comp31 = rect1.width*4.375;
					double comp32 = rect1.x+comp31;
					comp3 = comp32/rect2.x;
				} else{
					double comp31 = rect2.width*4.375;
					double comp32 = rect2.x+comp31;
					comp3 = comp32/rect1.x;
				}
				
				//Post used variables
				
				//Post results of checks
				SmartDashboard.putDouble("comp1", comp1);
				SmartDashboard.putDouble("comp2", comp2);
				SmartDashboard.putDouble("comp3", comp3);
				
				if (0.85<comp1&&comp1<1.15&&0.85<comp2&&comp2<1.15&&0.85<comp3&&comp3<1.15){
					System.out.println("Target Found");
					//Break out of for loop
					b=10000;
					j=10000;
					//Draw confirmed rectangles
					Imgproc.rectangle( source, rect2.tl(), rect2.br(), new Scalar(0,0,255), 2, 8, 0 );
					Imgproc.rectangle( source, rect1.tl(), rect1.br(), new Scalar(0,0,255), 2, 8, 0 );
					Imgproc.rectangle( source, temp.tl(),  temp.br(),  new Scalar(0,255,0), 2, 8, 0);
					//Create new variables for correct boxes
					confRectFull=temp; 
					if(rect1.x>rect2.x){
						confRectLeft=rect1;
						confRectRight=rect2;
					} else{
						confRectLeft=rect2;
						confRectRight=rect1;
					}
				}else {
					//NO Target Found!!!
					System.out.println("No Target");
					//NOTE: Use something better than print to alert operator that no target was found
					//and do something to continue, like try the cycle again OR change to find the best target.
				}
	}
	
			}
		}
	}
	public void viewGear(){
		//put the processed image with rectangles on smartdashboard and beware of the triangles
		cvSource.putFrame(source);
	}
	
	public double getDistance(){
		//get x of middle of rect
		Point right = confRectRight.br();
		Point left = confRectLeft.tl();
		double parthalf = right.x-left.x;
		parthalf = parthalf/2;
		double half = left.x + parthalf;
		return half;
	}
	
	public double getAngle(){
		//get y of middle of rect 
		Point bot = confRectLeft.br();
		Point top = confRectLeft.tl();
		double parthalf = bot.y-top.y;
		parthalf = parthalf/2;
		double half = top.y + parthalf;
		return half;
	}
}
