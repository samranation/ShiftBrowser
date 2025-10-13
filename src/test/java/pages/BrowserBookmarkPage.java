package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.TestBase;
import utils.AppiumUtil;
import utils.ClickUtil;

import java.util.List;

public class BrowserBookmarkPage extends TestBase {

    public String saveBookmarkAndGetBookmarkText() {

        ClickUtil clickUtil = new ClickUtil();
        // add bookmark
        clickUtil.robustClickOnElement("//Button[@Name='Add Bookmark']",
                "//Button[@Name='Save']");

        // save
        WebElement saveBookmark = defaultAppiumWait().until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.xpath("//Button[@Name='Save']"))
        );
        saveBookmark.click();

        // quick settings
        clickUtil.robustClickOnElement("//Button[@Name='Quick Settings']",
                "//Button[contains(@Name,'Advanced Settings')]");

        // depending on resolution we may have to scroll down to find
        WebElement bookmarks = null;
        try {
            bookmarks = defaultAppiumWait().until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//Button[@Name='Bookmarks' and @FrameworkId='Chrome']"))
            );
            bookmarks.click();
        } catch (WebDriverException ignore) {
            AppiumUtil.app.findElement(AppiumBy.xpath("//Window")).sendKeys(Keys.PAGE_DOWN);
            defaultAppiumWait().until(ExpectedConditions.elementToBeClickable(bookmarks));
            bookmarks.click();
        }

        List<WebElement> hits = this.retrieveBookmark();

        return hits.get(0).getText();
    }

    public WebElement deleteBookmark() {
        List<WebElement> hits = this.retrieveBookmark();

        // right click
        hits.get(0).sendKeys(Keys.SHIFT, Keys.F10);

        WebElement delete = AppiumUtil.app.findElement(AppiumBy.xpath("//*[@Name='Delete']"));
        delete.click();


        return AppiumUtil.app.findElement(AppiumBy.xpath(
                "//Text[@Name='To bookmark pages, click the star in the address bar']"));
    }

    private List<WebElement> retrieveBookmark() {
        WebElement bookmarkList = AppiumUtil.app.findElement(AppiumBy.accessibilityId("list"));
        List<WebElement> hits = bookmarkList.findElements(By.xpath(
                ".//*[@Name and " +
                        "contains(translate(@Name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'careers')]"
        ));
        return hits;
    }
}
