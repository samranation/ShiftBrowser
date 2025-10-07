package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ClickUtil {

    public void robustClickOnElement(String elementByName, WindowsDriver app) {
        WebDriverWait wait = new WebDriverWait(app, Duration.ofSeconds(30));

        By by = AppiumBy.name(elementByName);
        try {
            WebElement btn = new WebDriverWait(app, Duration.ofSeconds(30))
                    .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(by)));

            new WebDriverWait(app, Duration.ofSeconds(5)).until(d ->
                    "True".equals(btn.getAttribute("IsEnabled")) && !"True".equals(btn.getAttribute("IsOffscreen")));

            new Actions(app).doubleClick(btn).perform();

            // check element has become stale
            try {
                wait.withTimeout(Duration.ofSeconds(3))
                        .until(ExpectedConditions.stalenessOf(btn));
            } catch (TimeoutException ignore) {
                app.findElement(by).click();
            }

        } catch (WebDriverException ignore) {
            app.findElement(by).sendKeys(Keys.ENTER);
        }
    }

}
