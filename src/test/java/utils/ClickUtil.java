package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.TestBase;

import java.time.Duration;
import java.util.logging.Logger;

import static org.awaitility.Awaitility.await;

public class ClickUtil extends TestBase {

    private final WindowsDriver app = AppiumUtil.app;

    public void robustClickOnElement(String element, String nextPageTitleElement) {
        await()
                .atMost(Duration.ofSeconds(45))
                .pollInterval(Duration.ofMillis(400))
                .ignoreExceptions()
                .until(() -> {
                    By btnBy = AppiumBy.xpath(element);
                    WebElement btn;

                    // for some reason the open shift button is difficult to click
                    if (element.contains("Open Shift")) {
                        btn = app.findElement(btnBy);
                        btn.sendKeys(Keys.TAB);
                        btn.sendKeys(Keys.ENTER);
                    } else {
                        btn = defaultAppiumWait()
                                .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(btnBy)));
                        try {
                            btn.click();
                        } catch (WebDriverException e) {
                            try {
                                app.findElement(btnBy).sendKeys(Keys.ENTER);
                            } catch (WebDriverException e1) {
                                app.findElement(btnBy).sendKeys(Keys.SPACE);
                            }
                        }
                    }

                    By nextBy = robustNextBy(nextPageTitleElement);

                    try {
                        new WebDriverWait(app, Duration.ofSeconds(6))
                                .until(ExpectedConditions.or(
                                        ExpectedConditions.visibilityOfElementLocated(nextBy),
                                        ExpectedConditions.stalenessOf(btn),
                                        ExpectedConditions.invisibilityOfElementLocated(btnBy),
                                        ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(btnBy))
                                ));
                        return true;
                    } catch (TimeoutException te) {
                        return false;
                    }
                });
    }

    private By robustNextBy(String originalNextXpath) {
        if (originalNextXpath != null && !originalNextXpath.isBlank()) {
            return AppiumBy.xpath(
                    "("+ originalNextXpath + ") | " +
                            // Fallbacks: any element with that text fragment
                            "//*[contains(@Name,'Create Spaces') or contains(@Name,'Create spaces') or contains(@Name,'Create Space') or contains(@Name,'Spaces') or contains(@Name,'Create')]"
            );
        }
        return AppiumBy.xpath(
                "//*[contains(@Name,'Create Spaces') or contains(@Name,'Create spaces') or contains(@Name,'Create')]"
        );
    }
}
