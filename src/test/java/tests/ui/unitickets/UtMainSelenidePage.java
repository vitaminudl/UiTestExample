package tests.ui.unitickets;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class UtMainSelenidePage {
    private SelenideElement cityFromField = $x("//input[@placeholder='Откуда']");
    private ElementsCollection listOfCityFrom = $$x("//div[@class='origin field active']//div[@class='city']");
    private SelenideElement cityToField = $x("//input[@placeholder='Куда']");
    private ElementsCollection listOfCityTo = $$x("//div[@class='destination field active']//div[@class='city']");
    private SelenideElement dateForward = $x("//input[@placeholder='Туда']");
    private SelenideElement dateBack = $x("//input[@placeholder='Отбратно']");
    private String dayInCalendar = "//span[text()='%d']";
    private SelenideElement searchBtn = $x("//div[@class='search_btn']");


    public UtMainSelenidePage setCityFrom(String city){
        cityFromField.clear();
        cityFromField.sendKeys(city);
        cityFromField.click();
        listOfCityFrom.find(Condition.partialText(city)).click();
        return this;
    }

    public UtMainSelenidePage setCityTo(String city){
        cityToField.clear();
        cityToField.sendKeys(city);
        cityToField.click();
        listOfCityTo.find(Condition.partialText(city)).click();
        return this;
    }

    public UtMainSelenidePage setDayForward(int day){
        dateForward.click();
        getDay(day).click();
        $x("//h2").click();
        return this;
    }

    public UtMainSelenidePage setDayBack(int day){
        dateBack.click();
        getDay(day).click();
        $x("//h2").click();
        return this;
    }

    private SelenideElement getDay(int day){
        return $x(String.format(dayInCalendar, day));
    }

    public UtSearchSelenidePage search(){
        searchBtn.click();
        return page(UtSearchSelenidePage.class);
    }
}
