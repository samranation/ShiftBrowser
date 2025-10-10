package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.TestBase;
import utils.AppiumUtil;

public class BrowserNavigationPage extends TestBase {
    private final By navigation = AppiumBy.name("Search the Web or Enter Site Address");

    public void checkBrowserNavigationPageHasLoaded() {
        defaultAppiumWait()
                .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(navigation)));
    }

    public String navigateToRedBrickCareerWebsite() {
        WebElement navigationElement = AppiumUtil.app.findElement(navigation);
        navigationElement.click();
        navigationElement.sendKeys("https://www.rdbrck.com/");
        navigationElement.sendKeys(Keys.ENTER);

        // click on Careers link
        WebElement careersUrl = defaultAppiumWait().until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath( "//*[@ClassName='Chrome_RenderWidgetHostHWND']" +
                        "//*[self::Hyperlink or @LocalizedControlType='link'][@Name='Careers']")));
        careersUrl.click();

        // see openings link
        WebElement seeOpeningsUrl = defaultAppiumWait().until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath( "//*[@ClassName='Chrome_RenderWidgetHostHWND']" +
                        "//*[self::Hyperlink or @LocalizedControlType='link'][@Name='See openings']")));
        seeOpeningsUrl.click();

        // click Technology filter
        WebElement technology = defaultAppiumWait().until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath(  "//*[@ClassName='Chrome_RenderWidgetHostHWND']" +
                        "//*[self::Text or @LocalizedControlType='text'][@Name='TECHNOLOGY']")));
        technology.click();

        By docBy = AppiumBy.xpath(
                "//*[@ClassName='Chrome_RenderWidgetHostHWND' and " +
                        " @LocalizedControlType='document' and @Name='Shift']"
        );

        WebElement doc = customAppiumWait(15)
                .until(ExpectedConditions.presenceOfElementLocated(docBy));
        return doc.getText();
    }
}
