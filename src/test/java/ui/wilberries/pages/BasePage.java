package ui.wilberries.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    private By pageLoader = By.xpath("//div[@class='general-preloader']");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;

    }

    public String getTextJs(By element){
        return (String) js.executeScript("return arguments[0].textContent;", driver.findElement(element));
    }

    public void jsClick(By element){
        js.executeScript("arguments[0].click;", driver.findElement(element));
    }

    public void waitPageLoads(){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageLoader));
    }

    public void waitForElementUpdated(By locator){
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(locator)));
    }
}
