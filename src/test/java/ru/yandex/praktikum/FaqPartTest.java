package ru.yandex.praktikum;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.praktikum.pageObjects.FaqPart;
import ru.yandex.praktikum.pageObjects.HomePage;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FaqPartTest {

    @Rule
    public DriverFactory factory = new DriverFactory();

    private final String questionSubstring;
    private final String expectedAnswer;

    public FaqPartTest(String questionSubstring, String expectedAnswer) {
        this.questionSubstring = questionSubstring;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters
    public static Object[][] getQuestionAnswer() {
        try {
            return CsvDataProvider.loadFromResource("/faq-data.csv");
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить тестовые данные", e);
        }
    }

    @Test
    public void verifyFaqAnswer() {
        WebDriver driver = factory.getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.openHomePage();

        FaqPart faq = new FaqPart(driver);
        WebElement questionButton = faq.findQuestionBySubstring(questionSubstring);
        faq.expandAnswer(questionButton);
        String actualAnswer = faq.getAnswerTextFromElement(questionButton);

        assertEquals(
                "Ответ не совпадает для вопроса (подстрока):" + questionSubstring,
                normalize(expectedAnswer),
                normalize(actualAnswer)
        );
    }

    private static String normalize(String s) {
        return s == null ? "" : s.trim().replaceAll("\\s+", " ");
    }

}
