package edu.csc150;

import javax.swing.*;

@SuppressWarnings("serial")
public class ConfigurationMenu extends JFrame {
	
	public static JFrame frame;
	public static String[] args = null;
	
	public static void main(String[] args) {
		ConfigurationMenu.args = args;
		initialize();
	}
	
	public static void initialize() {
		frame = new JFrame("RPG Phase 3!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ConfigurationPanel panel = new ConfigurationPanel();
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setVisible(true);
	}
}
