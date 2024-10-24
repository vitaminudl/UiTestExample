package tests.ui.selenoid;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

@BrowsersType(browser = BrowsersType.Browser.FIREFOX, isRemote = false)
public class SelenoidTests {

    @Test
    public void selenoidTest(){
        Selenide.open("https://vk.com");
    }
}
