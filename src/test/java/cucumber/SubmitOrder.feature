
@tag
Feature: Purchase the order from Ecommerce website
  I want to use this template for my feature file

	Background:
	Given I landed on Ecommerce Page

  @Regression
  Scenario Outline: Positive Test of Submitting the order
    Given Logged in with username <name> and password <password>
    When I add product <productName> to cart
    And Checkout <productName> and Submit the order
    Then "Thankyou for the order." message displayed on confirmation page

    Examples: 
      | name  							 | password    | productName  |
      | dhanesh123@gmail.com | Dhanesh@123 | ZARA COAT 3  |
      
