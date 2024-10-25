package tests.ui.selenoid;

import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BrowserTypeAnnotationProcessing implements BeforeAllCallback, BeforeEachCallback {
    private static ExtensionContext.Namespace space = ExtensionContext.Namespace.create(BrowserTypeAnnotationProcessing.class);
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        BrowsersType annotation = context.getRequiredTestClass().getAnnotation(BrowsersType.class);
        context.getStore(space).put("annotation", annotation);
    }


    private Capabilities getCaps(BrowsersType type){
        switch (type.browser()){
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("browserVersion", "124.0");
                return firefoxOptions;
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("browserVersion", "128.0");
                return chromeOptions;
            default:throw new IllegalArgumentException("Bad browser type");
        }
    }
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        BrowsersType methodAnnotation = context.getRequiredTestMethod().getAnnotation(BrowsersType.class);
        BrowsersType classAnnotation = context.getStore(space).get("annotation",BrowsersType.class);
        if (methodAnnotation == null){
            methodAnnotation = classAnnotation;
        }
        if (methodAnnotation!=null){
            if (methodAnnotation.isRemote()) {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                Map<String, Object> selenoidOptions = new HashMap<>();
                selenoidOptions.put("enableVNC",true);
                selenoidOptions.put("enableVideo", true);
                capabilities.setCapability("selenoid:options", selenoidOptions);
                capabilities.merge(getCaps(methodAnnotation));
                WebDriverRunner.setWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities));
            } else {
                SelenideConfig config = new SelenideConfig().browser(methodAnnotation.browser().name().toLowerCase());
                SelenideDriver driver = new SelenideDriver(config);
                WebDriverRunner.setWebDriver(driver.getAndCheckWebDriver());
            }
        }
    }
}
