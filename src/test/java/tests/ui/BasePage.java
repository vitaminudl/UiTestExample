package tests.ui;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;


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

    public void waitPageLoadsWb(){
        By pageLoader = By.xpath("//div[@class='general-preloader']");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(pageLoader));
    }

    public void waitForElementUpdated(By locator){
        wait.until(ExpectedConditions.stalenessOf(driver.findElement(locator)));
    }

    public void clearTextFieldFull(By locator){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(Keys.LEFT_CONTROL + "A");
        driver.findElement(locator).sendKeys(Keys.BACK_SPACE);
    }

    public WebElement waitForTextPresentedInList(By list, String value){
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(list));
        return driver.findElements(list).stream()
                .filter(x->x.getText().contains(value))
                .findFirst()
                .orElseThrow(()-> new NoSuchElementException("Города нет" + value));
    }
}
