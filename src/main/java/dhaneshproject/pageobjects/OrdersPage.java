package dhaneshproject.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import dhaneshproject.AbstractComponent.AbstractComponent;

public class OrdersPage extends AbstractComponent {
	
	WebDriver driver;
	
	public OrdersPage(WebDriver  driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="td:nth-child(3)")
	List<WebElement> productsInOrders;
	
	
	
	public List<WebElement> getProductList()
	{
		return productsInOrders;
	}
	
	public boolean verifyProductInOrders(String productName)
	{
		boolean match = getProductList().stream().anyMatch(p->p.getText().equalsIgnoreCase(productName));
		return match;
	}

}
