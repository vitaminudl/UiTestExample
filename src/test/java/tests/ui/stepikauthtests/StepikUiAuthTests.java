package tests.ui.stepikauthtests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.netty.handler.ssl.OpenSslX509KeyManagerFactory;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;

public class StepikUiAuthTests {
    private String login = "victovik319@gmail.com";
    private String pass = "y1FA6xW1nXUL";


    @Test
    public void uiAuthTest(){
        Selenide.open("https://stepik.org/catalog");
        $x("//a[(@class='ember-view navbar__auth navbar__auth_login st-link st-link_style_button')]").click();
        $x("//input[@name='login']").sendKeys(login);
        $x("//input[@name='password']").sendKeys(pass);
        $x("//button[@type='submit']").click();
        $x("//img[@class='navbar__profile-img']").should(Condition.visible).click();
        $x("//li[@data-qa='menu-item-profile']").should(Condition.visible).click();
        $x("//h1").should(Condition.text("Jogh Сena"));

    }
}
