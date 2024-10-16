package tests.ui.unitickets;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class UtSearchSelenidePage {
    private SelenideElement tittleLoader = $x("//div[@class='countdown-tetle']");
    private SelenideElement priceSelectedMain = $x("//li[@class='price--current']//span[@class='prices__price currency_font currency_font--rub']");
    private SelenideElement selectedDayForward = $x("//li[@class='price--current']//a/span[1]");
    private SelenideElement selectedDayBack = $x("//li[@class='price--current']//a/span[3]");
    private ElementsCollection listOfForwardDays = $$x("//div[@class='ticket    ticket--with-labels   ticket--one_label_baggage']//following::span[@class='flight-brief-date__day'][1]");
    private ElementsCollection listOfBackDays = $$x("//div[@class='ticket    ticket--with-labels   ticket--one_label_baggage']//following::span[@class='flight-brief-date__day'][3]");

    public UtSearchSelenidePage assertAllDaysForwardShouldHaveDay(int expectedForwardDay){
        String day = String.valueOf(expectedForwardDay);
        listOfForwardDays.should(CollectionCondition.containExactTextsCaseSensitive(day));
        return this;
    }

    public UtSearchSelenidePage assertAllDaysBackShouldHaveDay(int expectedBackDay){
        String day = String.valueOf(expectedBackDay);
        listOfBackDays.should(CollectionCondition.containExactTextsCaseSensitive(day));
        return this;
    }

    public UtSearchSelenidePage assertMainDayForward(int expectedDay){
        selectedDayForward.should(Condition.partialText(String.valueOf(expectedDay)));
        return this;
    }

    public UtSearchSelenidePage assertMainDayBack(int expectedDay){
        selectedDayBack.should(Condition.partialText(String.valueOf(expectedDay)));
        return this;
    }

    public UtSearchSelenidePage waitForPage(){
        priceSelectedMain.should(Condition.matchText("\\d+"));
        return this;
    }

    public UtSearchSelenidePage waitForTittleDisappear(){
        tittleLoader.should(Condition.disappear, Duration.ofSeconds(30));
        return this;
    }
}
