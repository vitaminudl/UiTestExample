package tests.ui.unitickets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.ui.BasePage;

public class UtMainPage extends BasePage {

    private By cityFromField = By.xpath("//input[@placeholder='Откуда']");
    private By listOfCityFrom = By.xpath("//div[@class='origin field active']//div[@class='city']");
    private By cityToField = By.xpath("//input[@placeholder='Куда']");
    private By listOfCityTo = By.xpath("//div[@class='destination field active']//div[@class='city']");
    private By dateForward = By.xpath("//input[@placeholder='Туда']");
    private By dateBack = By.xpath("//input[@placeholder='Отбратно']");
    private String dayInCalendar = "//span[text()='%d']";
    private By searchBtn = By.xpath("//div[@class='search_btn']");


    public UtMainPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(cityFromField));
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
    }

    public UtMainPage setCityFrom(String city){
        clearTextFieldFull(cityFromField);
        driver.findElement(cityFromField).sendKeys(city);
        driver.findElement(cityFromField).click();
        waitForTextPresentedInList(listOfCityFrom,city).click();
        return this;
    }

    public UtMainPage setCityTo(String city){
        driver.findElement(cityToField).clear();
        driver.findElement(cityToField).sendKeys(city);
        driver.findElement(cityToField).click();
        waitForTextPresentedInList(listOfCityTo,city).click();
        return this;
    }

    public UtMainPage setDayForward(int day){
        getDay(day).click();
        wait.until(ExpectedConditions.invisibilityOf(getDay(day)));
        return this;
    }

    public UtMainPage setDayBack(int day){
        getDay(day).click();
        return this;
    }

    private WebElement getDay(int day){
        By dayLocator = By.xpath(String.format(dayInCalendar, day));
        return  driver.findElement(dayLocator);
    }

    public UtSearchPage search(){
        driver.findElement(searchBtn).click();
        return new UtSearchPage(driver);
    }
}
