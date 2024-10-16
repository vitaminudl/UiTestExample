package tests.ui.screenshots;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ScreenShotsTest {
    private String testName;
    private static File outputDir;

    @BeforeEach
    public void initTestName(TestInfo info){
        info.getTestMethod().get().getName();
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
