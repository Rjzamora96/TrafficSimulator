package edu.csc150;

import greenfoot.export.GreenfootScenarioMain;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

@SuppressWarnings("serial")
public class ConfigurationPanel extends JPanel {
	
	private JButton runButton = new JButton("Run");
	private JPanel controlPanel = new JPanel();
	private JLabel spawnInterval = new JLabel("Car Generation Interval:");
	private JTextField intervalValue = new JTextField();
	private JLabel simulationLength = new JLabel("Simulation Length:");
	private JTextField lengthValue = new JTextField();
	private JCheckBox allowRed = new JCheckBox("Allow Red Cars");
	private JCheckBox allowBlue = new JCheckBox("Allow Blue Cars");
	private JCheckBox allowYellow = new JCheckBox("Allow Yellow Cars");
	private JCheckBox allowPurple = new JCheckBox("Allow Purple Cars");
	/*private JLabel worldHeight = new JLabel("World Height:");
	private JTextField heightValue = new JTextField();
	private JLabel worldWidth = new JLabel("World Width:");
	private JTextField widthValue = new JTextField();
	private JLabel horiRoads = new JLabel("Horizontal Roads:");
	private JTextField horiValue = new JTextField();
	private JLabel vertRoads = new JLabel("Vertical Roads:");
	private JTextField vertValue = new JTextField();*/
	private JLabel title = new JLabel("Configuration");
	public static Configuration currentConfiguration = new Configuration();
	
	public ConfigurationPanel() {
		currentConfiguration = grabConfiguration();
		this.setLayout(new BorderLayout());
		controlPanel.setLayout(new GridLayout(4, 1));
/*		controlPanel.add(worldHeight);
		heightValue.setText(Integer.toString(currentConfiguration.getHeightConfig()));
		controlPanel.add(heightValue);
		controlPanel.add(worldWidth);
		widthValue.setText(Integer.toString(currentConfiguration.getWidthConfig()));
		controlPanel.add(widthValue);
		controlPanel.add(horiRoads);
		horiValue.setText(Integer.toString(currentConfiguration.getHorizontalRoads()));
		controlPanel.add(horiValue);
		controlPanel.add(vertRoads);
		vertValue.setText(Integer.toString(currentConfiguration.getVerticalRoads()));
		controlPanel.add(vertValue);	*/
		controlPanel.add(spawnInterval);
		intervalValue.setText(Integer.toString(currentConfiguration.getIntervalConfig()));
		controlPanel.add(intervalValue);
		controlPanel.add(simulationLength);
		lengthValue.setText(Integer.toString(currentConfiguration.getLengthConfig()));
		controlPanel.add(lengthValue);
		allowRed.setSelected(currentConfiguration.isAllowRed());
		controlPanel.add(allowRed);
		allowBlue.setSelected(currentConfiguration.isAllowBlue());
		controlPanel.add(allowBlue);
		allowYellow.setSelected(currentConfiguration.isAllowYellow());
		controlPanel.add(allowYellow);
		allowPurple.setSelected(currentConfiguration.isAllowPurple());
		controlPanel.add(allowPurple);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(title, BorderLayout.NORTH);
		this.add(controlPanel, BorderLayout.CENTER);
		this.add(runButton, BorderLayout.SOUTH);
		ButtonListener listener = new ButtonListener();
		allowRed.addActionListener(listener);
		allowBlue.addActionListener(listener);
		allowYellow.addActionListener(listener);
		allowPurple.addActionListener(listener);
		runButton.addActionListener(listener);
	}
	
	public static void saveConfiguration() {
		try {
			FileOutputStream file = new FileOutputStream("configuration.sav");
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(currentConfiguration);
			out.close();
			file.close();
		} catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	public static Configuration grabConfiguration() {
		Configuration configuration = new Configuration();
		try {
			FileInputStream file = new FileInputStream("configuration.sav");
			ObjectInputStream in = new ObjectInputStream(file);
			configuration = (Configuration) in.readObject();
			in.close();
			file.close();
		} catch(IOException i) {
			System.out.println("File not found, will create a new one!");
		} catch(ClassNotFoundException c) {
			c.printStackTrace();
		} return configuration;
	}
	
	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source == allowRed) {
				if(currentConfiguration.isAllowRed()) {
					currentConfiguration.setAllowRed(false);
				} else {
					currentConfiguration.setAllowRed(true);
				}
			} if(source == allowBlue) {
				if(currentConfiguration.isAllowBlue()) {
					currentConfiguration.setAllowBlue(false);
				} else {
					currentConfiguration.setAllowBlue(true);
				}
			} if(source == allowYellow) {
				if(currentConfiguration.isAllowYellow()) {
					currentConfiguration.setAllowYellow(false);
				} else {
					currentConfiguration.setAllowYellow(true);
				}
			} if(source == allowPurple) {
				if(currentConfiguration.isAllowPurple()) {
					currentConfiguration.setAllowPurple(false);
				} else {
					currentConfiguration.setAllowPurple(true);
				}
			} if(source == runButton) {
				boolean isValid = false;
				try {
					/*currentConfiguration.setHeightConfig(Integer.parseInt(heightValue.getText()));
					currentConfiguration.setWidthConfig(Integer.parseInt(widthValue.getText()));
					currentConfiguration.setHorizontalRoads(Integer.parseInt(horiValue.getText()));
					currentConfiguration.setVerticalRoads(Integer.parseInt(vertValue.getText()));*/
					currentConfiguration.setIntervalConfig(Integer.parseInt(intervalValue.getText()));
					currentConfiguration.setLengthConfig(Integer.parseInt(lengthValue.getText()));
					isValid = true;
				} catch(NumberFormatException exception) {
					exception.printStackTrace();
					System.out.println("One of your entries is not valid! Please correct it!");
				} if(isValid) {
					saveConfiguration();
					TrafficWorld.setSettings(currentConfiguration);
					ConfigurationMenu.frame.dispose();
					GreenfootScenarioMain.main(ConfigurationMenu.args);
				}
			}
		}
		
	}
}
