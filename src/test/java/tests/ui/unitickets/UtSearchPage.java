package tests.ui.unitickets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import tests.ui.BasePage;

import java.util.List;

public class UtSearchPage extends BasePage {
    private By tittleLoader = By.xpath("//div[@class='countdown-tetle']");
    private By priceSelectedMain = By.xpath("//li[@class='price--current']//span[@class='prices__price currency_font currency_font--rub']");
    private By selectedDayForward = By.xpath("//li[@class='price--current']//a/span[1]");
    private By selectedDayBack = By.xpath("//li[@class='price--current']//a/span[3]");
    private By listOfForwardDays = By.xpath("//div[@class='ticket    ticket--with-labels   ticket--one_label_baggage']//following::span[@class='flight-brief-date__day'][1]");
    private By listOfBackDays = By.xpath("//div[@class='ticket    ticket--with-labels   ticket--one_label_baggage']//following::span[@class='flight-brief-date__day'][3]");

    public UtSearchPage(WebDriver driver) {
        super(driver);
    }

    public List<Integer> getDaysForward(){
        return getDigitalFromList(listOfForwardDays);
    }

    public List<Integer> getDaysBack(){
        return getDigitalFromList(listOfBackDays);
    }

    public Integer getMainDayForward(){
        return getDigitFromWebElement(driver.findElement(selectedDayForward));
    }

    public Integer getMainDayBack(){
        return getDigitFromWebElement(driver.findElement(selectedDayBack));
    }

    public void waitForPage(){
        waitForElementAppear(selectedDayForward);
        waitForTextMatchesRegex(priceSelectedMain, "\\d+");
    }

    public void waitForTittleDisappear(){
        waitForElementDisappear(tittleLoader);
    }


}
