package dhaneshproject.pageobjects;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import dhaneshproject.AbstractComponent.AbstractComponent;

public class CheckoutPage extends AbstractComponent {
	
	WebDriver driver;
	
	public CheckoutPage(WebDriver  driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[placeholder='Select Country']")
	WebElement countryInput;
	
	@FindBy(css="span[class='ng-star-inserted']")
	List<WebElement> countryOptions;
	
	@FindBy(css=".action__submit")
	WebElement placeOrderButton;
	
	public void selectCountry(String country)
	{
		countryInput.sendKeys(country);
		countryOptions.stream().filter(c->c.getText().equalsIgnoreCase(country)).findFirst().ifPresent(WebElement::click);
	}
	
	
	public ConfirmationPage placeOrder()
	{
		//placeOrderButton.click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", placeOrderButton);
		
		return new ConfirmationPage(driver);
	}
	
	

}
