package dhaneshproject.tests;




import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dhaneshproject.TestComponents.BaseTest;
import dhaneshproject.pageobjects.CartPage;
import dhaneshproject.pageobjects.CheckoutPage;
import dhaneshproject.pageobjects.ConfirmationPage;
import dhaneshproject.pageobjects.OrdersPage;
import dhaneshproject.pageobjects.ProductCatalogue;


public class SubmitOrderTest extends BaseTest {

	String productName = "ADIDAS ORIGINAL";
	
	@Test(dataProvider = "getData", groups = {"Purchase"})
	public void submitOrder(HashMap<String,String> data) throws IOException
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication(data.get("email"), data.get("password"));
		productCatalogue.addProductToCart(data.get("productName"));
		CartPage cartPage = productCatalogue.goToCart();
		boolean match = cartPage.verifyProductInCart(data.get("productName"));
		Assert.assertTrue(match);
		cartPage.getOrderIds();
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("India");
		ConfirmationPage confirmationPage = checkoutPage.placeOrder();
		String confirmationMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase("Thankyou for the order."));
	}
	
	@Test(dependsOnMethods = "submitOrder")
	public void orderHistory()
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("dhanesh123@gmail.com", "Dhanesh@123");
		OrdersPage ordersPage = productCatalogue.goToOrdersPage();
		boolean match = ordersPage.verifyProductInOrders(productName);
		Assert.assertTrue(match);
	}
	
	@DataProvider
	public Object[] getData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//dhaneshproject//data//PurchaseOrder.json");
		return new Object[] {data.get(0),data.get(1)}; 
	}
	
	
	
	/* Method 2
	@DataProvider
	public Object[][] getData()
	{
		return new Object[][] {{"dhanesh123@gmail.com","Dhanesh@123","ADIDAS ORIGINAL"},{"rajish123@gmail.com","Rajish@123","ZARA COAT 3"}}; 
	}
	*/
	
	/* Method 3
	@DataProvider
	public Object[] getData()
	{
		HashMap<String, String> data1 = new HashMap<String, String>();
		data1.put("email", "dhanesh123@gmail.com");
		data1.put("password", "Dhanesh@123");
		data1.put("productName", "ADIDAS ORIGINAL");
		
		HashMap<String, String> data2 = new HashMap<String, String>();
		data2.put("email", "rajish123@gmail.com");
		data2.put("password", "Rajish@123");
		data2.put("productName", "ZARA COAT 3");
		return new Object[] {data1,data2}; 
	}
	*/

}
