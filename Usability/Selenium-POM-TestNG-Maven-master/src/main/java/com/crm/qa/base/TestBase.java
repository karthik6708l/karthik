package com.crm.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import com.crm.qa.utilities.SeleniumActions;
import com.crm.qa.utilities.TestUtils;
import com.crm.qa.utilities.WebDriverListener;

//import freemarker.log.Logger;

public class TestBase {

	public static WebDriver driver;
	public static Properties properties;
	public static SeleniumActions sele_Actions;
	public static WebDriverListener eventListener;
	public static EventFiringWebDriver e_driver;
	public static ChromeOptions chromeOptions;
	public static Logger log;

	/*
	 * protected ITestResult result; protected ExtentReports extent; protected
	 * ExtentTest extentTest;
	 */
	public TestBase() {

		try {
			properties = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/com/crm/qa/config/config.properties");
			properties.load(ip);
			String log4jConfPath = System.getProperty("user.dir") + "/src/main/java/com/crm/qa/config/log4j.properties";
			PropertyConfigurator.configure(log4jConfPath);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("file not found");
		} catch (IOException e) {
			System.out.println("io exception");

		}

	}

	public static void initializaton() {
		String browserName = properties.getProperty("browser");
		getDriver(browserName);
		log.getLogger(TestBase.class);
		log.info(browserName + " is configured");

		//e_driver = new EventFiringWebDriver(driver);
		eventListener = new WebDriverListener();
		//e_driver.register(eventListener);
		//driver = e_driver;

		driver.manage().window().maximize();
		//driver.manage().deleteAllCookies();

		driver.manage().timeouts().implicitlyWait(TestUtils.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		

		driver.get(properties.getProperty("url"));
		

		sele_Actions = new SeleniumActions();

	}

	public static  void getDriver(String browserName) {
		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", TestUtils.WORKSAPCE_PATH + "//src//drivers//chromedriver.exe");
			//chromeOptions = new ChromeOptions();
			//chromeOptions.addArguments("--start-maximized");
			driver=new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("FF")) {
			System.setProperty("webdriver.gecko.driver", TestUtils.WORKSAPCE_PATH + "//src//geckodriver.exe");
			new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", TestUtils.WORKSAPCE_PATH + "//src//drivers//IEDriverServer.exe");
			new InternetExplorerDriver();
		}
		
	}

	public void tearDownMain() {
		driver.manage().deleteAllCookies();
		driver.close();
	}

	/*
	 * public void setExtend() { extent = new
	 * ExtentReports(System.getProperty("user.dir") +
	 * "//test-output//+NewExtentReport.html", true); Map<String, String> info = new
	 * HashMap<String, String>(); info.put("host name", "Krishna Windows");
	 * info.put("user name", "Krishna"); info.put("Environment", "QA");
	 * 
	 * extent.addSystemInfo(info);
	 * 
	 * }
	 * 
	 * public void FormatResult() { if (result.getStatus() == ITestResult.FAILURE) {
	 * extentTest.log(LogStatus.FAIL, "Failed test case is ::" + result.getName());
	 * extentTest.log(LogStatus.FAIL, "Failed test case is ::" +
	 * result.getThrowable()); TestUtils.takeScreenShot(driver);
	 * extentTest.log(LogStatus.FAIL,
	 * extentTest.addScreenCapture(TestUtils.SCREENSHOT_PATH)); } else if
	 * (result.getStatus() == ITestResult.SKIP) { extentTest.log(LogStatus.SKIP,
	 * "Skipped test case is ::" + result.getName()); } else if (result.getStatus()
	 * == ITestResult.SUCCESS) { extentTest.log(LogStatus.PASS,
	 * "Passed testc case is ::" + result.getName()); }
	 * 
	 * extent.endTest(extentTest);
	 * 
	 * }
	 */}
