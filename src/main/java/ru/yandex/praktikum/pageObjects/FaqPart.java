package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FaqPart {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public FaqPart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public WebElement findQuestionBySubstring(String questionSubstring) {
        String escaped = escapeSubstring(questionSubstring);
        String locator = generateXpathLocatorByEscaped(escaped);
        return waitForPresenceOfQuestion(locator);
    }

    private String escapeSubstring(String questionSubstring) {
        return questionSubstring.trim().replace("'", "\\'");
    }

    private String generateXpathLocatorByEscaped(String escaped) {
        return String.format(
                "//div[@data-accordion-component='AccordionItemButton' and contains(normalize-space(), '%s')]",
                escaped
        );
    }

    private WebElement waitForPresenceOfQuestion(String locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    public void expandAnswer(WebElement questionButton) {
        scrollToQuestion(questionButton);
        clickQuestionButton(questionButton);
        waitForAnswerExpand(questionButton);
    }

    private void scrollToQuestion(WebElement questionButton) {
        ((JavascriptExecutor)driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                questionButton
        );
    }

    private void clickQuestionButton(WebElement questionButton) {
        wait.until(ExpectedConditions.elementToBeClickable(questionButton));
        questionButton.click();
    }

    private void waitForAnswerExpand(WebElement questionButton) {
        wait.until(d -> "true".equals(questionButton.getAttribute("aria-expanded")));
    }


    public String getAnswerTextFromElement(WebElement questionButton) {
        WebElement accordionItem = findAccordionItemByQuestionButton(questionButton);
        WebElement answerPanel = findAnswerPanel(accordionItem);
        waitForAnswerTextNotEmpty(answerPanel);
        return extractAndTrimAnswerText(answerPanel);
    }

    private WebElement findAccordionItemByQuestionButton(WebElement questionButton) {
        return questionButton.findElement(
                By.xpath("./ancestor::div[@data-accordion-component='AccordionItem'][1]")
        );
    }

    private WebElement findAnswerPanel(WebElement accordionItem) {
        return accordionItem.findElement(
                By.cssSelector("div[data-accordion-component='AccordionItemPanel']")
        );
    }

    private void waitForAnswerTextNotEmpty(WebElement answerPanel) {
        wait.until(d -> {
            String text = answerPanel.getText();
            return text != null && !text.trim().isEmpty();
        });
    }

    private String extractAndTrimAnswerText(WebElement answerPanel) {
        return answerPanel.getText().trim();
    }

}
