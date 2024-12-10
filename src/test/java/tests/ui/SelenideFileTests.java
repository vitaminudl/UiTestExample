package tests.ui;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import listeners.RetryListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$x;

@ExtendWith(RetryListener.class)
public class SelenideFileTests {
    @Test
    public void readPdfTest() throws IOException {
        File pdf = new File("src/test/resources/terms-and-conditions.pdf");
        PDF pdfReader = new PDF(pdf);
        String pdfText = pdfReader.text;
        Assertions.assertTrue(pdfText.contains("The regulations of Section 44a of"));
    }

    @Test
    public void readPdfFile() throws IOException {
        Selenide.open("https://www.pdf995.com/samples/");
        File pdf = $x("//td[@data-sort='pdf.pdf']/a").download();
        PDF pdfReader = new PDF(pdf);
        Assertions.assertEquals("Software 995", pdfReader.author);
    }
}
