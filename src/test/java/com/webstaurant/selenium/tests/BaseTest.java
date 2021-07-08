package com.webstaurant.selenium.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.webstaurant.selenium.utils.PropertyUtils;

public class BaseTest {
	protected final PropertyUtils configUtils = PropertyUtils.getInstance("config.properties");
	protected WebDriver webDriver;
	protected SoftAssert assertion = new SoftAssert();

	@BeforeSuite
	public void initialize() {
		System.setProperty("webdriver.chrome.driver", configUtils.getProperty("chrome.driver"));
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--no-sandbox");
		webDriver = new ChromeDriver(chromeOptions);
		webDriver.get(configUtils.getProperty("app.url"));
		assertion.assertTrue(webDriver.getTitle().contains(configUtils.getProperty("app.title")));
	}

	@AfterSuite
	public void cleanup() {
		webDriver.quit();
	}
}
