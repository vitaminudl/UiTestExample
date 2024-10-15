package ui.wilberries.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ItemPage {
    private WebDriver driver;

    private By itemHeaderName = By.xpath("//div[@class='product-page__header']//h1");
    private By priceItem = By.xpath("//span[@class='price-block__price']");

    public ItemPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getItemName(){
        String text = driver.findElement(itemHeaderName).getText();
        return text;
    }

    public Integer getItemPrice(){
        String priceText = driver.findElement(priceItem).getText();
        priceText = priceText.replaceAll("[^0-9.]", "");
        return Integer.parseInt(priceText);
    }
}
