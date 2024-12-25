package dhaneshproject.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import dhaneshproject.AbstractComponent.AbstractComponent;

public class CartPage extends AbstractComponent {
	
	WebDriver driver;
	
	public CartPage(WebDriver  driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="div[class='cartSection'] h3")
	List<WebElement> productsInCart;
	
	@FindBy(css=".itemNumber")
	List<WebElement> orderIds;
	
	@FindBy(css=".totalRow button")
	WebElement checkoutButton;
	
	
	public List<WebElement> getProductList()
	{
		return productsInCart;
	}
	
	public boolean verifyProductInCart(String productName)
	{
		boolean match = getProductList().stream().anyMatch(p->p.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public void getOrderIds()
	{
		orderIds.stream().map(o->o.getText()).forEach(System.out::println);
	}
	

	
	public CheckoutPage goToCheckout()
	{
		checkoutButton.click();
		
		return new CheckoutPage(driver);
	}
	
	
	
	

}
