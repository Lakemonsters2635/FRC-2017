
package org.usfirst.frc.team2635.robot;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team2635.robot.commands.ExampleCommand;
import org.usfirst.frc.team2635.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	
	//Constants
	public static final int RIGHT_MOTOR_CHANNEL = 0;
	public static final int RIGHT_FOLLOWER_CHANNEL = 6;
	public static final int LEFT_MOTOR_CHANNEL = 0;
	public static final int LEFT_FOLLOWER_CHANNEL = 12;
	
	public static final int CLIMB_MOTOR_CHANNEL = 0;
	public static final int CLIMB_FOLLOWER_CHANNEL = 0; 
	public static final float CLIMB_MOTOR_MAX_VOLTAGE = 12.0f;
	public static final int CLIMB_BUTTON_ID = 1;
	public static final int CLIMB_MOTOR_VOLTAGE = 12;
	
	public static final int PICKUP_MOTOR_CHANNEL = 9;
	public static final float PICKUP_MOTOR_MAX_VOLTAGE = 12.0f;
	public static final int PICKUP_CLOCKWISE_BUTTON_ID = 4;
	public static final int PICKUP_COUNTER_CLOCKWISE_BUTTON_ID = 5;
	public static final int PICKUP_MOTOR_VOLTAGE = 6;
	
	public static final int LEFT_STICK_CHANNEL = 1;
	public static final int RIGHT_STICK_CHANNEL = 0;

	
	//public RobotMotionProfile robotMotionProfile = new RobotMotionProfile(_leftMotor, _rightMotor);
    Command autonomousCommand;
    SendableChooser chooser;

    //Physical Object Declaration
    CANTalon rightMotor;
    CANTalon rightFollower;
    CANTalon leftMotor;
    CANTalon leftFollower;
    CANTalon climbMotor;
    CANTalon climbFollower;
    CANTalon pickupMotor;
    Joystick leftStick;
    Joystick rightStick;
    //todo:Pneumatic piston for gear delivery
    //todo:Pneumatic piston for door
    
    //Method Object Declaration
    Shooter shooter;
    BallPickup pickup;
    RopeClimber climber;
    Drive drive;
    Compressor c;
    DoubleSolenoid sol1;
    DoubleSolenoid sol2;
    
    //Variable Declaration
    boolean pickupForward;
    boolean pickupOn;

    ButtonState [] _leftBtnStates = new ButtonState[10];
    ButtonState [] _rightBtnStates = new ButtonState[10];
    
    public void robotInit() {
		oi = new OI();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new ExampleCommand());
//        chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        
        //Physical Object Initialization
        rightMotor = new CANTalon(RIGHT_MOTOR_CHANNEL);
        rightFollower = new CANTalon(RIGHT_FOLLOWER_CHANNEL);
        leftMotor = new CANTalon(LEFT_MOTOR_CHANNEL);
        leftFollower = new CANTalon(LEFT_FOLLOWER_CHANNEL);
        climbMotor = new CANTalon(CLIMB_MOTOR_CHANNEL);
        climbFollower = new CANTalon(CLIMB_FOLLOWER_CHANNEL);
        pickupMotor = new CANTalon(PICKUP_MOTOR_CHANNEL);
        leftStick = new Joystick(LEFT_STICK_CHANNEL);
        rightStick = new Joystick(RIGHT_STICK_CHANNEL);
        c = new Compressor(0);
        sol1 = new DoubleSolenoid(0,1);
        sol2 = new DoubleSolenoid(2,3);
        
        //robotMotionProfile
        
        //Method Object Initialization
        shooter = new Shooter();
        pickup = new BallPickup();
        climber = new RopeClimber();
        drive = new Drive();
        
        //Variable Initialization
        pickupForward = true;
        
        //Motor Follower Initialization
        rightFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
        rightFollower.set(rightMotor.getDeviceID());
        leftFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
        leftFollower.set(leftMotor.getDeviceID());
        climbFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
        climbFollower.set(climbMotor.getDeviceID());
        
        //Motor Mode Initialization
        pickupMotor.changeControlMode(CANTalon.TalonControlMode.Voltage);
        pickupMotor.configPeakOutputVoltage(PICKUP_MOTOR_MAX_VOLTAGE, -PICKUP_MOTOR_MAX_VOLTAGE);
        climbMotor.changeControlMode(CANTalon.TalonControlMode.Voltage);
        climbMotor.configPeakOutputVoltage(CLIMB_MOTOR_MAX_VOLTAGE, -CLIMB_MOTOR_MAX_VOLTAGE);
        
        //Method Object Setup
 
        	shooter.shootInit();

        pickup.init(pickupMotor, leftStick, PICKUP_CLOCKWISE_BUTTON_ID, PICKUP_COUNTER_CLOCKWISE_BUTTON_ID, PICKUP_MOTOR_VOLTAGE);
        climber.init(climbMotor, leftStick, CLIMB_BUTTON_ID, CLIMB_MOTOR_VOLTAGE);
        drive.init(leftStick, rightStick, leftMotor, rightMotor);
        
        for (int i=1; i < _leftBtnStates.length; i++)
        {
        	ButtonState leftState = new ButtonState();
        	leftState.buttonId = i;
        	_leftBtnStates[i]  = leftState;
        	
        	ButtonState rightState = new ButtonState();
        	rightState.buttonId = i;
        	_rightBtnStates[i]  = leftState;
        	
        }
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }
public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        if(rightStick.getRawButton(7)){
			sol1.set(DoubleSolenoid.Value.kForward);
		} else if (rightStick.getRawButton(8)){
			sol1.set(DoubleSolenoid.Value.kReverse);
		} else {
			sol1.set(DoubleSolenoid.Value.kOff);
		}
		if(rightStick.getRawButton(9)){
			sol2.set(DoubleSolenoid.Value.kForward);
		} else if (rightStick.getRawButton(10)){
			sol2.set(DoubleSolenoid.Value.kReverse);
		} else {
			sol2.set(DoubleSolenoid.Value.kOff);
		}
        
        for (int i=1; i < _leftBtnStates.length; i++)
        {
        	if (!_leftBtnStates[i].IsPressed)
        	{
        		_leftBtnStates[i].IsPressed = leftStick.getRawButton(i);
        	}
        	else
        	{
        		boolean currentlyPressed = leftStick.getRawButton(i);
        		if (!currentlyPressed)
        		{
        			_leftBtnStates[i].IsReleased = true;
        			_leftBtnStates[i].IsPressed = false;
        		}
        	}
        }
        
        
        for (int i=1; i < _rightBtnStates.length; i++)
        {
        	if (!_rightBtnStates[i].IsPressed)
        	{
        		_rightBtnStates[i].IsPressed = rightStick.getRawButton(i);
        	}
        	else
        	{
        		boolean currentlyPressed = rightStick.getRawButton(i);
        		if (!currentlyPressed)
        		{
        			_rightBtnStates[i].IsReleased = true;
        			_rightBtnStates[i].IsPressed = false;
        		}
        	}
        }
        
        
        
        
		ButtonState pickupButtonState = _leftBtnStates[PICKUP_CLOCKWISE_BUTTON_ID];
		if (pickupButtonState.IsReleased)
		{
			pickupOn = !pickupOn;
			pickupForward = true;
			pickupButtonState.IsReleased = false;
			
		}
		
		pickupButtonState = _leftBtnStates[PICKUP_COUNTER_CLOCKWISE_BUTTON_ID];
		if (pickupButtonState.IsReleased)
		{
			pickupOn = !pickupOn;
			pickupForward = false;
			pickupButtonState.IsReleased = false;
			
		}

        shooter.shoot();

        pickup.ballPickup(pickupOn, pickupForward);
        climber.ropeClimb();

        drive.tankDrive();

    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
