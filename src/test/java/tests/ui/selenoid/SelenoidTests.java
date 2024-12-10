package tests.ui.selenoid;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import listeners.RetryListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.$x;

@ExtendWith(AllureLogsExtension.class)
@ExtendWith(RetryListener.class)
public class SelenoidTests {

    @Test
    @BrowsersType(browser = BrowsersType.Browser.FIREFOX, isRemote = true)
    public void selenoidTest(){
        Selenide.open("https://vk.com");
        $x("//div[@class='login_mobile_header']").should(Condition.text("wrong value"));
    }

    @Test
    @BrowsersType(browser = BrowsersType.Browser.FIREFOX, isRemote = false)
    public void selenoidFirefoxTest(){
        Selenide.open("https://vk.com");
    }
}
