package com.webstaurant.selenium.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductListingPage {

	private WebDriver webDriver;
	private WebElement lastSearchItem;

	@FindBy(css = "#product_listing")
	private WebElement productListingGrid;

	@FindBy(css = "div[class *= 'ag-item'] div[class='details'] a[href *= '.html']")
	private List<WebElement> allSearchItems;

	@FindBy(xpath = "//div[@class='notification-center']//div[@class='notify-body']/a[contains (text(),'View Cart')]")
	private WebElement viewCartBtn;

	public ProductListingPage(WebDriver driver) {
		this.webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	public Map<Integer, String> getAllItemNames(long waitTimeout, long pollingTime) {
		Map<Integer, String> allItemNames = new HashMap<>();
		int itemCount = 1;
		new WebDriverWait(webDriver, waitTimeout, pollingTime)
				.until(ExpectedConditions.visibilityOf(productListingGrid));
		for (WebElement searchItem : allSearchItems) {
			if (itemCount == allSearchItems.size()) {
				lastSearchItem = searchItem;
			}
			allItemNames.put(itemCount, searchItem.getText());
			itemCount++;
		}
		return allItemNames;
	}

	public void addToCart() {
		lastSearchItem
				.findElement(By.xpath(
						"//parent::div/following-sibling::div[@class='add-to-cart']//input[@value='Add to Cart']"))
				.click();
	}

	public void viewCart(long waitTimeout, long pollingTime) {
		new WebDriverWait(webDriver, waitTimeout, pollingTime).until(ExpectedConditions.visibilityOf(viewCartBtn));
		viewCartBtn.click();
	}
}
