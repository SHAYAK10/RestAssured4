package com.seleniumScripts;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigRead;

public class SeleniumScripts {
	String currentWorkingDirectory;
	String configFilename;
	Properties configProperties;
	public static String accessCode;

	public String getAuthorizationCodeForActivites() throws IOException {
		currentWorkingDirectory = System.getProperty("user.dir");
		configFilename = currentWorkingDirectory + "/src/test/resources/config/config.properties";
		configProperties = ConfigRead.readConfigProperties(configFilename);
		

		String url = "https://www.strava.com/oauth/authorize?" + "client_id="
				+ configProperties.getProperty("client_id")
				+ "&response_type=code&redirect_uri=http://localhost/exchange_token&approval_prompt=force&scope=activity:read_all";

		String uri = "https://www.strava.com/login";
		
		
		// setting headless browser
		WebDriverManager.chromedriver().setup();
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("headless");
		WebDriver driver = new ChromeDriver(opt);
		System.out.println("Wait running Selenium Script");
		

		driver.get(uri);
		System.out.println("Successfully visited www.strava.com");
		driver.findElement(By.id("email")).sendKeys(configProperties.getProperty("email"));
		driver.findElement(By.id("password")).sendKeys(configProperties.getProperty("password"));
		driver.findElement(By.id("login-button")).click();
		System.out.println("Successfully logged in");

		driver.get(url);
		driver.findElement(By.id("authorize")).click();
		System.out.println("Successfully authorized");
		System.out.println("sending authorization code to test case");

		String currentUrl = driver.getCurrentUrl();

		String[] currentUrlItems = currentUrl.split("&");
		String[] finalItems = currentUrlItems[1].split("=");

		accessCode = finalItems[1];

		return accessCode;
	}

}
