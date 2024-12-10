package tests.ui.screenshots;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import listeners.RetryListener;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(RetryListener.class)
public class ScreenShotsTest {
    private String testName;
    private static File outputDir;

    @BeforeEach
    public void initTestName(TestInfo info){
        info.getTestMethod().get().getName();
    }

    @AfterEach
    public void tearDown(){
        Selenide.closeWindow();
        Configuration.browserCapabilities= new SelenideConfig().browserCapabilities();
    }

    @BeforeAll
    public static void initFolder(){
        outputDir = new File("build/screenshots");
        if (!outputDir.exists()){
            outputDir.mkdirs();
        }
    }

    @Test
    public void web1080Test() throws IOException {
        Configuration.browserSize = "1920x1080";
        Selenide.open("https://github.com/vitaminudl");
        assertFullScreen();
    }

    @Test
    public void mobileIphoneXrTest() throws IOException {
        Configuration.browserSize = "414x896";
        Selenide.open("https://github.com/vitaminudl");
        assertFullScreen();
    }

    public void mobileIphoneXrUsingCollectionTest() throws IOException {
        Map<String, String> mobileEmulator = new HashMap<>();
        mobileEmulator.put("deviceName", "iPhone XR");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulator", mobileEmulator);
        Configuration.browserCapabilities = chromeOptions;

        Selenide.open("https://github.com/vitaminudl");
        assertFullScreen();
    }






    private void assertFullScreen() throws IOException {
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                .takeScreenshot(WebDriverRunner.getWebDriver());
        File actualScreen = new File(outputDir.getAbsolutePath() + "/" + testName + ".png");
        ImageIO.write(screenshot.getImage(),"png", actualScreen);

        File expectedScreen = new File(String.format("src/test/resources/references/%s.png", testName));
        if (!expectedScreen.exists()){
            throw new RuntimeException("No reference image");
        }
        assertImages(actualScreen,expectedScreen);
    }

    private void assertImages(File actual, File expected) throws IOException {
        ImageDiff differ = new ImageDiffer()
                .makeDiff(ImageIO.read(actual), ImageIO.read(expected))
                .withDiffSizeTrigger(10);
        if (differ.hasDiff()){
            BufferedImage diffImage = differ.getMarkedImage();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ImageIO.write(diffImage,"png",bos);
            byte[] image = bos.toByteArray();
            Allure.getLifecycle().addAttachment("diff","image/png", "png", image);
        }
        Assertions.assertFalse(differ.hasDiff());
    }
}
