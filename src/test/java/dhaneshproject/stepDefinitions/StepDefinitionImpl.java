package dhaneshproject.stepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import dhaneshproject.TestComponents.BaseTest;
import dhaneshproject.pageobjects.CartPage;
import dhaneshproject.pageobjects.CheckoutPage;
import dhaneshproject.pageobjects.ConfirmationPage;
import dhaneshproject.pageobjects.LandingPage;
import dhaneshproject.pageobjects.ProductCatalogue;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImpl extends BaseTest {

	ProductCatalogue productCatalogue;
	ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void i_landed_on_Ecommerce_Page() throws IOException
	{
		launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String userName, String password)
	{
		productCatalogue = landingPage.loginApplication(userName, password);
	}
	
	@When("^I add product (.+) to cart$")
	public void i_add_product_to_cart(String productName)
	{
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and Submit the order$")
	public void checkout_and_Submit_the_order(String productName)
	{
		CartPage cartPage = productCatalogue.goToCart();
		boolean match = cartPage.verifyProductInCart(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("India");
		confirmationPage = checkoutPage.placeOrder();
	}
	
	@Then( "{string} message displayed on confirmation page")
	public void message_displayed_on_confirmation_page(String message)
	{
		String confirmationMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase(message));
		driver.close();
	}
	
	@Then ("{string} message is displayed")
	public void error_message_is_displayed(String message)
	{
		String errorMessage = landingPage.getErrorMessage();
		Assert.assertEquals(errorMessage, message); 
		driver.close();
	}
}
