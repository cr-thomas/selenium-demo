package com.webstaurant.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	private WebDriver webDriver;

	@FindBy(css = "#searchval")
	private WebElement searchInput;

	@FindBy(xpath = "//input[@class='btn btn-info banner-search-btn']")
	private WebElement searchBtn;

	public HomePage(WebDriver driver) {
		this.webDriver = driver;
		PageFactory.initElements(webDriver, this);
	}

	public void searchItem(String itemToSearch) {
		searchInput.sendKeys(itemToSearch);
		searchBtn.click();
	}
}
