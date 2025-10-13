package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.BrowserNavigationPage;
import utils.AppiumUtil;

public class Test03_BrowserNavigationTest extends TestBase {

    @BeforeClass
    public void beforeClass(){
        this.startBrowserAndMaximizeWindow();
    }

    @Test(description = "Shift browser navigation test", priority = 3)
    public void test03_navigationTest() {
        new AppiumUtil().startAppium("//*[@ClassName='Chrome_WidgetWin_1' and contains(@Name,'- Shift Browser')]");

        BrowserNavigationPage browserNavigationPage = new BrowserNavigationPage();
        browserNavigationPage.checkBrowserNavigationPageHasLoaded();
        String shitRoleText = browserNavigationPage.navigateToRedBrickCareerWebsite();
        Assert.assertTrue(shitRoleText.toLowerCase().contains("software development manager in test"),
                "Role is not available");
    }

    @AfterClass
    public void afterClass() {
        this.restoreWindowAndCloseApp();
    }
}
