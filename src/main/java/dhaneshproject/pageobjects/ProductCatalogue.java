package dhaneshproject.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import dhaneshproject.AbstractComponent.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {
	
	WebDriver driver;
	
	public ProductCatalogue(WebDriver  driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement spiner;
	
	@FindBy(xpath="//button[contains(text(),'Cart')]")
	WebElement cart;

	@FindBy(css="[routerlink='/dashboard/myorders']")
	WebElement orders;
	
	By productBy = By.cssSelector(".mb-3");
	By addToCartBy = By.xpath(".//button[normalize-space(text())='Add To Cart']");
	By toastMessageBy = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productBy);
		return products;
	}
	
	public WebElement getProductByName(String productName)
	{
		WebElement prod = getProductList().stream().filter(p->p.findElement(By.xpath(".//h5")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}
	
	public void addProductToCart(String productName)
	{
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCartBy).click();
		
		//waitForElementToAppear(toastMessageBy);
		//waitForElementToDisappera(spiner);
		
		try 
		{
			Thread.sleep(2000);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public CartPage goToCart()
	{
		cart.click();
		
		return new CartPage(driver);
	}
	
	public OrdersPage goToOrdersPage()
	{
		orders.click();
		
		return new OrdersPage(driver);
		
	}
	
	

}
