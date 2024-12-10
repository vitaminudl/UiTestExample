package tests.ui.unitickets;

import com.codeborne.selenide.Selenide;
import listeners.RetryListener;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@Tag("SMOKE")
@ExtendWith(RetryListener.class)
public class UtSelenideTest {

    @AfterAll
    public static  void saveFailed(){
        RetryListener.saveFailedTests();
    }

    @Test
    public void firstSelenideTest(){
        int expectedDayForward = 25;
        int expectedDayBack = 30;

        Selenide.open("https://uniticket.ru/");

        UtMainSelenidePage mainPage = new UtMainSelenidePage();
        mainPage.setCityFrom("Казань")
                .setCityTo("Дубай")
                .setDayForward(expectedDayForward)
                .setDayBack(expectedDayBack)
                .search()
                .waitForPage()
                .waitForTittleDisappear()
                .assertMainDayBack(expectedDayBack)
                .assertMainDayForward(expectedDayForward)
                .assertAllDaysBackShouldHaveDay(expectedDayBack)
                .assertAllDaysForwardShouldHaveDay(expectedDayForward);
        Assertions.fail();
    }
}
