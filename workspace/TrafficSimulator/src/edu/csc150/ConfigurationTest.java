package edu.csc150;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void testSetting() {
		ConfigurationPanel.currentConfiguration.setIntervalConfig(10);
		assertEquals(ConfigurationPanel.currentConfiguration.getIntervalConfig(), 10);
	}
	
	public void testSaving() {
		ConfigurationPanel.currentConfiguration.setIntervalConfig(10);
		try {
			ConfigurationPanel.saveConfiguration();
		} catch(Exception e) {
			fail("Failed to save!");
		}
	}
	
	public void testLoading() {
		ConfigurationPanel.currentConfiguration.setIntervalConfig(15);
		ConfigurationPanel.saveConfiguration();
		ConfigurationPanel.currentConfiguration.setIntervalConfig(10);
		ConfigurationPanel.grabConfiguration();
		assertEquals(ConfigurationPanel.currentConfiguration.getIntervalConfig(), 15);
	}

}
