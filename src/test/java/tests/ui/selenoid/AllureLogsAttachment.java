package tests.ui.selenoid;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.nio.charset.StandardCharsets;


public class AllureLogsAttachment {

    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] pageSource(){
        return WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "Page screen", type = "img/png")
    public static byte[] pageScreen(){
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Browser logs", type = "text/plain")
    public static String getLogs(){
        String browser = ((RemoteWebDriver)WebDriverRunner.getWebDriver()).getCapabilities().getBrowserName();
        if (browser.equals("chrome")){
            return String.join("\n", Selenide.getWebDriverLogs(LogType.BROWSER));
        }
        return null;
    }

    @Attachment(value = "Video Html", type = "text/plain", fileExtension = ".html")
    public static String getVideo(String sessionId){
        String url = "http://localhost:8080/video/" + sessionId + ".mp4";
        return "<html><body><a href=\"" + url + "\">" + url + "</a><video width='100%' height='400px' controls autoplay><source src='"
                + url + "'type='video/mp4'></video></body></html>";
    }


}
