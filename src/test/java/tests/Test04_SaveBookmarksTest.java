package tests;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BrowserBookmarkPage;
import pages.BrowserNavigationPage;

public class Test04_SaveBookmarksTest extends TestBase {

    @BeforeClass
    public void beforeClass(){
        this.startBrowserAndMaximizeWindow();
        // would be wise to implement a check here to see if there are existing
        // bookmarks. if so, we should delete them before performing the test
    }

    @Test
    public void test04_saveJobPostAsBookmark() {
        BrowserNavigationPage browserNavigationPage = new BrowserNavigationPage();
        browserNavigationPage.checkBrowserNavigationPageHasLoaded();
        browserNavigationPage.navigateToRedBrickCareerWebsite();

        BrowserBookmarkPage browserBookmarkPage = new BrowserBookmarkPage();
        String text = browserBookmarkPage.saveBookmarkAndGetBookmarkText();
        Assert.assertTrue(text.toLowerCase().contains("careers"),
                "Bookmark is not available");
    }

    @AfterClass
    public void afterClass() {
        BrowserBookmarkPage browserBookmarkPage = new BrowserBookmarkPage();
        WebElement webElement = browserBookmarkPage.deleteBookmark();
        Assert.assertTrue(webElement.isDisplayed(),
                "Bookmark did not delete.");

        this.restoreWindowAndCloseApp();
    }
}
