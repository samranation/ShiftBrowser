package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.TestBase;
import utils.ClickUtil;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

public class IntroPage extends TestBase {

    public void togglePrivacyPolicyAcceptance() {
        await().atMost(Duration.ofSeconds(45)).until(() -> {
            WebElement eulaCheckbox = defaultAppiumWait().until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//CheckBox[@Name='EULA and Privacy Policy Checkbox']"))
            );
            defaultAppiumWait().until(ExpectedConditions.elementToBeClickable(eulaCheckbox));

            // toggle checkbox
            String state = eulaCheckbox.getAttribute("Toggle.ToggleState");
            if ("0".equals(state)) {
                eulaCheckbox.click();
                defaultAppiumWait().until(d -> "1".equals(eulaCheckbox.getAttribute("Toggle.ToggleState")));
            }
            return eulaCheckbox.getAttribute("Toggle.ToggleState").equals("1");
        });

    }

    public void finishInstallationSteps() {
        ClickUtil clickUtil = new ClickUtil();
        // welcome to shift
        clickUtil.robustClickOnElement("//Button[contains(@Name,'Go to next step')]",
                "//Text[contains(@Name,'Create Spaces')]");

        // create spaces
        clickUtil.robustClickOnElement("//Button[contains(@Name,'Go to next step')]",
                "//Text[contains(@Name,'Edit, add, or remove')]");

        // Add apps to your spaces
        clickUtil.robustClickOnElement("//Text[contains(@Name,'Skip without adding apps')]",
                "//Text[contains(@Name, 'Shift measures and offsets')]");

        // you've switched...
        clickUtil.robustClickOnElement("//Button[contains(@Name,'Continue')]",
                "//Text[contains(@Name,'The Original Shiftie')]");

        // choose your template
        clickUtil.robustClickOnElement("//Button[contains(@Name,'Select this template and continue')]",
                "//Button[contains(@Name,'Open Shift')]");

        clickUtil.robustClickOnElement("(//Group[@Name='Controls']//Button[@Name='Open Shift']" +
                        " | //Button[contains(@Name,'Open Shift')])",
                "//Button[contains(@Name,'New Tab')]");
    }

}
