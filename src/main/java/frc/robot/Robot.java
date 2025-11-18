// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.XboxController;

/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
    SparkMax frontRightMotor = new SparkMax(Constants.FRONT_RIGHT_ID, MotorType.kBrushless);

    SparkMax frontLeftMotor = new SparkMax(Constants.FRONT_LEFT_ID, MotorType.kBrushless);

    SparkMax backRightMotor = new SparkMax(Constants.BACK_RIGHT_ID, MotorType.kBrushless);

    SparkMax backLeftMotor = new SparkMax(Constants.BACK_LEFT_ID, MotorType.kBrushless);
    
    XboxController controller = new XboxController(0);
  /** Called once at the beginning of the robot program. */
  public Robot() {
     // Factory Defaults
SparkMaxConfig config = new SparkMaxConfig();

config
    .inverted(true)
    .idleMode(IdleMode.kBrake);
config.encoder
    .positionConversionFactor(1000)
    .velocityConversionFactor(1000);
config.closedLoop
    .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
    .pid(1.0, 0.0, 0.0);

SparkMaxConfig configb = new SparkMaxConfig();
configb
    .inverted(false)
    .idleMode(IdleMode.kBrake);
configb.encoder
    .positionConversionFactor(1000)
    .velocityConversionFactor(1000);
configb.closedLoop
    .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
    .pid(1.0, 0.0, 0.0);
    
frontLeftMotor.configure(configb, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
frontRightMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
backLeftMotor.configure(configb, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
backRightMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    

   /*  frontRightMotor.restoreFactoryDefaults();
    frontLeftMotor.restoreFactoryDefaults();
    backRightMotor.restoreFactoryDefaults();
    backLeftMotor.restoreFactoryDefaults();

    // Coast Mode
    frontRightMotor.setIdleMode(IdleMode.kBrake);
    frontLeftMotor.setIdleMode(IdleMode.kBrake);
    backRightMotor.setIdleMode(IdleMode.kBrake);
    backLeftMotor.setIdleMode(IdleMode.kBrake);

    // Inversions
    frontRightMotor.setInverted(Constants.FRONT_RIGHT_INVERT);
    frontLeftMotor.setInverted(Constants.FRONT_LEFT_INVERT);
    backRightMotor.setInverted(Constants.BACK_RIGHT_INVERT);
    backLeftMotor.setInverted(Constants.BACK_LEFT_INVERT);*/
    
  }

  @Override
  public void teleopInit() {
    frontRightMotor.set(0);
    frontLeftMotor.set(0);
    backRightMotor.set(0);
    backLeftMotor.set(0);
  }

  @Override
  public void teleopPeriodic() {
    double drive = controller.getLeftY();
    drive = (Math.abs(drive) < Constants.JOYSTICK_DEADBAND) ? 0 : drive * Constants.MAX_SPEED_PERCENT;

    double turn = -controller.getRightX();
    turn = (Math.abs(turn) < Constants.JOYSTICK_DEADBAND) ? 0 : turn * Constants.MAX_SPEED_PERCENT;

    frontRightMotor.set(drive - turn);
    backRightMotor.set(drive - turn);
    frontLeftMotor.set(drive + turn);
    backLeftMotor.set(drive + turn);

   


   
  }
}
