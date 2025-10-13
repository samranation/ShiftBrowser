package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.BrowserSettingsPage;
import utils.VersionUtil;

public class Test02_CheckVersionOfInstalledBrowserTest extends TestBase {

    @BeforeClass
    public void beforeClass(){
        this.startBrowserAndMaximizeWindow();
    }

    @Test(description = "Check Version of installed shift browser ")
    public void test02_CheckVersionOfInstalledBrowserTest()  {
        BrowserSettingsPage browserSettingsPage = new BrowserSettingsPage();
        browserSettingsPage.checkBrowserSettingsPageHasLoaded();
        String versionText = browserSettingsPage.checkVersionInAdvancedSettings();
        String version = new VersionUtil().getVersion();
        Assert.assertTrue(versionText.contains(version), "Versions did not match!");
    }

    @AfterClass
    public void afterClass() {
        this.restoreWindowAndCloseApp();
    }
}
