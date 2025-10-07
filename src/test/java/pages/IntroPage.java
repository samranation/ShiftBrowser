package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.AppiumUtil;
import utils.ClickUtil;

import java.time.Duration;

public class IntroPage {

    public void togglePrivacyPolicyAcceptance() {
        WebDriverWait wait = new WebDriverWait(AppiumUtil.app, Duration.ofSeconds(30));
        WebElement eulaCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.xpath("//CheckBox[@Name='EULA and Privacy Policy Checkbox']"))
        );
        wait.until(ExpectedConditions.elementToBeClickable(eulaCheckbox));

        // checkbox
        String state = eulaCheckbox.getAttribute("Toggle.ToggleState");
        if ("0".equals(state)) {
            eulaCheckbox.click();
            wait.until(d -> "1".equals(eulaCheckbox.getAttribute("Toggle.ToggleState")));
        }
    }

    public void finishInstallationSteps() throws InterruptedException {
        new ClickUtil().robustClickOnElement("Go to next step", AppiumUtil.app);

        // Thread sleep is not ideal as we would want to use implicit waits
        // if time permitted, I would implement the following check instead of a thread sleep:
//        String text =
//        AppiumUtil.app.findElement(AppiumBy.xpath("//Text[contains(@Name,'Create Spaces')]")).getText();
//        Assert.assertTrue(text.contains("Create Spaces"));
        // not sure if this works as I wasn't able to test in time.

        Thread.sleep(3000);

        new ClickUtil().robustClickOnElement("Go to next step", AppiumUtil.app);

        new ClickUtil().robustClickOnElement("Skip without adding apps", AppiumUtil.app);

        new ClickUtil().robustClickOnElement("Continue", AppiumUtil.app);

        new ClickUtil().robustClickOnElement("Select this template and continue", AppiumUtil.app);

        AppiumUtil.shiftWindow.click();

        new ClickUtil().robustClickOnElement("Open Shift", AppiumUtil.app);
    }

}
