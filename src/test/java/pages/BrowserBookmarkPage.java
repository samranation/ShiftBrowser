package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import tests.TestBase;
import utils.AppiumUtil;

import java.util.List;

public class BrowserBookmarkPage extends TestBase {

    public String saveBookmarkAndGetBookmarkText() {
        WebElement addBookmarkSimple = AppiumUtil.app.findElement(AppiumBy.xpath("//Button[@Name='Add Bookmark']"));
        addBookmarkSimple.click();

        WebElement saveBookmark = AppiumUtil.app.findElement(AppiumBy.xpath("//Button[@Name='Save']"));
        saveBookmark.click();

        WebElement quickSettings = AppiumUtil.app.findElement(AppiumBy.xpath("//Button[@Name='Quick Settings']"));
        quickSettings.click();

        WebElement bookmarks = AppiumUtil.app.findElement(AppiumBy.xpath("//Button[@Name='Bookmarks' and @FrameworkId='Chrome']"));
        bookmarks.click();

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
