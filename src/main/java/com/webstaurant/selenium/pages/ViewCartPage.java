package com.webstaurant.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ViewCartPage {

	private WebDriver webDriver;

	@FindBy(xpath = "//a[@class='emptyCartButton btn btn-mini btn-ui pull-right']")
	private WebElement emptyCartBtn;

	public ViewCartPage(WebDriver driver) {
		this.webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	public void emptyCart(long waitTimeout, long pollingTime) {
		new WebDriverWait(webDriver, waitTimeout, pollingTime).until(ExpectedConditions.visibilityOf(emptyCartBtn));
		emptyCartBtn.click();
	}
}
