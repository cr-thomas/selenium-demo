package com.webstaurant.selenium.tests;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import com.webstaurant.selenium.pages.HomePage;
import com.webstaurant.selenium.pages.ProductListingPage;
import com.webstaurant.selenium.pages.ViewCartPage;

public class AddToCartTest extends BaseTest {
	private HomePage homePage;
	private ProductListingPage productListingPage;
	private ViewCartPage viewCartPage;

	@Test
	public void runTest() {
		homePage = new HomePage(webDriver);
		productListingPage = new ProductListingPage(webDriver);
		viewCartPage = new ViewCartPage(webDriver);
		long waitTimeout = Long.parseLong(configUtils.getProperty("wait.timeout"));
		long pollingTime = Long.parseLong(configUtils.getProperty("pollingTime"));

		homePage.searchItem("stainless work table");
		Map<Integer, String> allItemNames = productListingPage.getAllItemNames(waitTimeout, pollingTime);
		for (Entry<Integer, String> eachItem : allItemNames.entrySet()) {
			assertion.assertTrue(StringUtils.contains(eachItem.getValue(), "Table"),
					"Item " + eachItem + " does not contains the word - Table");
		}
		productListingPage.addToCart();
		productListingPage.viewCart(waitTimeout, pollingTime);
		viewCartPage.emptyCart(waitTimeout, pollingTime);
		assertion.assertAll();
	}
}
