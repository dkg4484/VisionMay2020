package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class FunctionalLibrary {

	static Properties prop;

	static WebDriver driver;

//	public FunctionalLibrary() {
//
//		prop = new Properties();
//
//		try {
//			prop.load(new FileInputStream(System.getProperty("user.dir") + "/configuration/config.properties"));
//		} catch (Exception e) {
//			System.out.println("===========File not found exception" + e.getMessage());
//		}
//
//	}

	public static String readConfigFile(String key) {

		prop = new Properties();

		try {
			prop.load(new FileInputStream(System.getProperty("user.dir") + "/configuration/config.properties"));
		} catch (Exception e) {
			System.out.println("===========File not found exception" + e.getMessage());
		}

		return prop.getProperty(key);

	}

	public static String readObjectFile(String key) {

		prop = new Properties();

		try {
			prop.load(new FileInputStream(System.getProperty("user.dir") + "/configuration/object.properties"));
		} catch (Exception e) {
			System.out.println("===========File not found exception" + e.getMessage());
		}

		return prop.getProperty(key);

	}

	public static void initialization(String key) {

		String browser = readConfigFile(key);

		if (browser.toUpperCase().equals("CHROME")) {

			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");

			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");

			driver = new ChromeDriver();
		}

		else if (browser.toUpperCase().equals("IE"))

		{

			System.setProperty("webdriver.ie.driver", "./driver/IEDriverServer.exe");

			driver = new InternetExplorerDriver();

		}

		else {

			System.setProperty("webdriver.gecko.driver", "./driver/geckodriver.exe");

			driver = new FirefoxDriver();
		}

		driver.get(prop.getProperty("url"));

		captureScreenShot("After Google Launched");

		driver.manage().window().maximize();

		System.out.println(driver.getTitle() + " " + driver.getCurrentUrl());

	}

	public static void captureScreenShot(String testcasename) {

		try {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
					new File(System.getProperty("user.dir") + "/ScreenShot/" + testcasename + System.currentTimeMillis()
							+ ".png"));
		} catch (WebDriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static WebElement getWebElement(String key) {

		WebElement element;

		String value = readObjectFile(key);

		String[] sp = value.split("#");

		String locator = sp[0];

		String elementAddress = sp[1];

		switch (locator.toUpperCase()) {

		case "ID":

			element = driver.findElement(By.id(elementAddress));
			break;

		case "NAME":

			element = driver.findElement(By.name(elementAddress));
			break;

		case "CLASS":

			element = driver.findElement(By.className(elementAddress));
			break;

		case "TAG":

			element = driver.findElement(By.tagName(elementAddress));
			break;

		case "LINK":

			element = driver.findElement(By.linkText(elementAddress));
			break;

		case "PARTIAL":
			element = driver.findElement(By.partialLinkText(elementAddress));
			break;

		case "XPATH":

			element = driver.findElement(By.xpath(elementAddress));
			break;

		default:

			element = driver.findElement(By.cssSelector(elementAddress));

		}

		return element;

	}

	public static void enterText(String location, String key) {

		getWebElement(location).sendKeys(readConfigFile(key));

	}

	public static void clickButton(String location) {

		getWebElement(location).click();

	}

}
