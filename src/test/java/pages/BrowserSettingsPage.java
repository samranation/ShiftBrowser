package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.TestBase;
import utils.AppiumUtil;
import utils.ClickUtil;

public class BrowserSettingsPage extends TestBase {

    public void checkBrowserSettingsPageHasLoaded() {
        By quickSettings = AppiumBy.name("Quick Settings");
        defaultAppiumWait()
                .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(quickSettings)));
    }

    public String checkVersionInAdvancedSettings() {
        ClickUtil clickUtil = new ClickUtil();
        clickUtil.robustClickOnElement("//Button[@Name='Quick Settings']",
                "//Button[contains(@Name,'Advanced Settings')]");
        clickUtil.robustClickOnElement("//Button[contains(@Name,'Advanced Settings')]",
                "//Text[contains(@Name,'Version')]");
        WebElement versionEl = AppiumUtil.app.findElement(
                AppiumBy.xpath("//Text[contains(@Name,'Version')]")
        );
        return versionEl.getAttribute("Name");
    }

}
