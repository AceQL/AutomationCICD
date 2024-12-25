package dhaneshproject.tests;


import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import dhaneshproject.TestComponents.BaseTest;
import dhaneshproject.TestComponents.Retry;
import dhaneshproject.pageobjects.CartPage;
import dhaneshproject.pageobjects.ProductCatalogue;


public class ErrorValidationTest extends BaseTest {

	@Test(groups = {"Error Handling"}, retryAnalyzer = Retry.class)
	public void loginErrorValidation() throws IOException
	{
		landingPage.loginApplication("dhanesh123@gmail.com", "Dhanesh@123###");
		String errorMessage = landingPage.getErrorMessage();
		//Assert.assertEquals(errorMessage, "Incorrect email or password.");
		Assert.assertEquals(errorMessage, "Incorrect email or password.");
	}
	
	@Test(groups = {"Error Handling"})
	public void productErrorValidation() throws IOException
	{
		String productName = "ADIDAS ORIGINAL";
		
		ProductCatalogue productCatalogue = landingPage.loginApplication("dhanesh123@gmail.com", "Dhanesh@123");
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCart();
		boolean match = cartPage.verifyProductInCart("Nike");
		Assert.assertFalse(match);
		
	}

}
