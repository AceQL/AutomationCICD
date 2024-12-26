package dhaneshproject.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dhaneshproject.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
//dhanesh
public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage; 
	
	public WebDriver initializeBrowser() throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//dhaneshproject//resources//GlobalData.properties");
		prop.load(fis);
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser"); //get value from command line or property file
		//String browserName = prop.getProperty("browser");
		
		if(browserName.contains("chrome"))
		{
			ChromeOptions option = new ChromeOptions();
			if(browserName.contains("headless"))
			{
				option.addArguments("headless");
			}
			//System.setProperty("webdriver.chrome.driver", "D://Drivers//chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			
			driver = new ChromeDriver(option);
		}
		else if(browserName.equals("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if(browserName.equals("edge"))
		{
			WebDriverManager.edgedriver();
			driver = new EdgeDriver();
		}
		else
		{
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		return driver;
	}
	
	@BeforeMethod(alwaysRun = true)
	public void launchApplication() throws IOException
	{
		driver = initializeBrowser();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown()
	{
		driver.quit();
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		//Read JSON to String 
		String jsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		
		//Convert String to HashMap (using Jackson Databind dependency)
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){	
		});
		return data;
	}
	
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, destination);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}
	
	
}
