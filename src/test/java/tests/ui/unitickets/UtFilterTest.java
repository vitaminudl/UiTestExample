package tests.ui.unitickets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.ui.BaseTest;

public class UtFilterTest extends BaseTest {

    @BeforeEach
    public void openSite(){
        driver.get("https://uniticket.ru/");
    }

    @Test
    public void filterTest(){
        int expectedDayForward = 25;
        int expectedDayBack = 30;
        UtMainPage mainPage = new UtMainPage(driver);
        mainPage.setCityFrom("Казань")
                .setCityTo("Дубай")
                .setDayForward(expectedDayForward)
                .setDayBack(expectedDayBack)
                .search();
    }
}
