package tests.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SeleniumTests {
    private WebDriver driver;
    private String downloadFolder = System.getProperty("user.dir") + File.separator + "build" + File.separator + "downloadFiles";

    @BeforeAll
    public static void downloadDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        Map<String,String> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFolder);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @AfterEach
    public void tearDown(){
        driver.close();
    }

    @Test
    public void simpleUiTest(){
        String expectedTitle = "Олег Пендрак - Инженер по автоматизации тестирования QA Automation";

        driver.get("https://threadqa.ru");
        String actualTittle = driver.getTitle();

        Assertions.assertEquals(expectedTitle,actualTittle);
    }

    @Test
    public void simpleFromTest(){
        String expectedName = "Tomas Anderson";
        String expectedEmail = "tomas@matrix.ru";
        String expectedCurrentAndress = "USA Los Angeles";
        String expectedPermanentAddress = "USA Miami";

        driver.get("http://85.192.34.140:8081/");
        WebElement  elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementsCard.click();
        WebElement elementsTextBox = driver.findElement(By.xpath("//span[@class='text']"));
        elementsTextBox.click();

        WebElement fullName = driver.findElement(By.id("userName"));
        WebElement email = driver.findElement(By.id("userEmail"));
        WebElement currentAddress = driver.findElement(By.id("currentAddress"));
        WebElement permanentAddress = driver.findElement(By.id("permanentAddress"));
        WebElement submit = driver.findElement(By.id("submit"));

        fullName.sendKeys(expectedName);
        email.sendKeys(expectedEmail);
        currentAddress.sendKeys(expectedCurrentAndress);
        permanentAddress.sendKeys(expectedPermanentAddress);
        submit.click();

        WebElement nameNew = driver.findElement(By.id("name"));
        WebElement emailNew = driver.findElement(By.id("email"));
        WebElement currentAddressNew = driver.findElement(By.xpath("//div[@id ='output']//p[@id='currentAddress']"));
        WebElement permanentAddressNew = driver.findElement(By.xpath("//div[@id ='output']//p[@id='permanentAddress']"));

        String actualName = nameNew.getText();
        String actualEmail = emailNew.getText();
        String actualCurrentAddressNew = currentAddressNew.getText();
        String actualPermanentAddressNew = permanentAddressNew.getText();

        Assertions.assertTrue(actualName.contains(expectedName));
        Assertions.assertTrue(actualEmail.contains(expectedEmail));
        Assertions.assertTrue(actualCurrentAddressNew.contains(expectedCurrentAndress));
        Assertions.assertTrue(actualPermanentAddressNew.contains(expectedPermanentAddress));
    }

    @Test
    public void uploadTest(){
        driver.get("http://85.192.34.140:8081/");
        WebElement  elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementsCard.click();
        WebElement elementsTextBox = driver.findElement(By.xpath("//span[text()='Upload and Download']"));
        elementsTextBox.click();

        WebElement uploadBtn = driver.findElement(By.id("uploadFile"));
        uploadBtn.sendKeys(System.getProperty("user.dir") + "/src/test/resources/UITestsExample – SeleniumTests.java [UITestsExample.test] 2024-10-14 08-13-19.png");

        WebElement uploadedFakePath = driver.findElement(By.id("uploadedFilePath"));
        Assertions.assertTrue(uploadedFakePath.getText().contains("SeleniumTests.java [UITestsExample.test] 2024-10-14 08-13-19.png"));
    }

    @Test
    public void downloadTest(){
        driver.get("http://85.192.34.140:8081/");
        WebElement  elementsCard = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Elements']"));
        elementsCard.click();
        WebElement elementsTextBox = driver.findElement(By.xpath("//span[text()='Upload and Download']"));
        elementsTextBox.click();

        WebElement dowloadBtn = driver.findElement(By.id("downloadButton"));
        dowloadBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(x-> Paths.get(downloadFolder,"sticker.png").toFile().exists());

        File file = new File("build/downloadFiles/sticker.png");
        Assertions.assertTrue(file.length() !=0);
        Assertions.assertNotNull(file);
    }
}
