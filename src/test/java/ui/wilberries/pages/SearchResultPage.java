package ui.wilberries.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultPage {
    private WebDriver driver;

    private By allFiltersBtn = By.xpath("//button[@class='dropdown-filter__btn dropdown-filter__btn--all']");
    private By endPriceField = By.xpath("//input[@name='endN']");
    private By startPriceField = By.xpath("//input[@name='startN']");
    private By applyFilterBtn = By.xpath("//button[@class='filters-desktop__btn-main btn-main']");
    private By items = By.xpath("//div[@class='product-card-list']//article");


    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openItem(){
        driver.findElements(items).get(0).click();
    }

    public void openFilters(){
        driver.findElement(allFiltersBtn).click();
    }

    public void applyFilters(){
        driver.findElement(applyFilterBtn).click();
    }

    public void setMinPrice(Integer minValue){
        driver.findElement(startPriceField).clear();
        driver.findElement(startPriceField).sendKeys(String.valueOf(minValue));
    }

    public void setMaxPrice(Integer maxValue){
        driver.findElement(endPriceField).clear();
        driver.findElement(endPriceField).sendKeys(String.valueOf(maxValue));
    }



}
