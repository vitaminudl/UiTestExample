package ui.wilberries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ui.wilberries.pages.ItemPage;
import ui.wilberries.pages.MainPage;
import ui.wilberries.pages.SearchResultPage;

public class WbFilterTests extends BaseTest {

    @Test
    public void searchResultTest(){
        String expectedItem = "iPhone";
        Integer expectedPriceMax = 60000;
        Integer expectedPriceMin = 36000;

        MainPage mainPage = new MainPage(driver);
        mainPage.searchItem(expectedItem);

        SearchResultPage resultPage = new SearchResultPage(driver);
        resultPage.openFilters();
        resultPage.setMinPrice(expectedPriceMin);
        resultPage.setMaxPrice(expectedPriceMax);
        resultPage.applyFilters();
        resultPage.openItem();

        ItemPage itemPage = new ItemPage(driver);
        String actualName = itemPage.getItemName();
        Integer actualPrice = itemPage.getItemPrice();

        Assertions.assertTrue(actualName.toLowerCase().contains(expectedItem.toLowerCase()));
        Assertions.assertTrue(actualPrice >= expectedPriceMin && actualPrice <= expectedPriceMax);

    }
}
