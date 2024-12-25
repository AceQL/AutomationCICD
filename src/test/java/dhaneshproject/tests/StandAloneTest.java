package dhaneshproject.tests;

import java.time.Duration;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
		
		String productName = "ADIDAS ORIGINAL";
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		driver.get("https://rahulshettyacademy.com/client");
		
		driver.findElement(By.id("userEmail")).sendKeys("dhanesh123@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Dhanesh@123");
		driver.findElement(By.id("login")).click();
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".mb-3")));

		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		 /*
		for(WebElement prod : products)
		{
			 String pro = prod.findElement(By.xpath(".//h5")).getText();
			 
			 if(pro.equalsIgnoreCase("ADIDAS ORIGINAL"))
			 {
				 System.out.println(pro);
				 prod.findElement(By.xpath(".//button[normalize-space(text())='View']")).click();
				 break;
			 }
		}
		*/
		
		//products.stream().map(p->p.findElement(By.xpath(".//h5")).getText()).forEach(System.out::println);
		WebElement prod = products.stream().filter(p->p.findElement(By.xpath(".//h5")).getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.xpath(".//button[normalize-space(text())='Add To Cart']")).click();
		
		
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		driver.findElement(By.xpath("//button[contains(text(),'Cart')]")).click();
		
		
		List<WebElement> productsInCart = driver.findElements(By.cssSelector("div[class='cartSection'] h3"));
		
		boolean match = productsInCart.stream().anyMatch(p->p.getText().equalsIgnoreCase(productName));
		
		Assert.assertTrue(match);
		
		List<WebElement> orderIds = driver.findElements(By.cssSelector(".itemNumber"));
		orderIds.stream().map(o->o.getText()).forEach(System.out::println);
		
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys("India");
		
		List<WebElement> countries = driver.findElements(By.cssSelector("span[class='ng-star-inserted']"));
		
		countries.stream().filter(c->c.getText().equalsIgnoreCase("India")).findFirst().ifPresent(WebElement::click);
		
		
		
		WebElement placeOrderButton = driver.findElement(By.cssSelector(".action__submit"));
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", placeOrderButton);
		
		
		String successMsg = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(successMsg.equalsIgnoreCase("Thankyou for the order."));
	}

}
