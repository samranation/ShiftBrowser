package tests;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.IntroPage;
import utils.AppiumUtil;
import utils.ClickUtil;
import utils.DownloadShiftUtil;
import utils.PowerShellUtil;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.logging.Logger;

public class BrowserVersionCheckTest extends TestBase {

    private File[] shiftFiles;
    private String version;
    private Logger logger = Logger.getLogger(BrowserVersionCheckTest.class.getName());

    @BeforeClass
    public void setup() throws  InterruptedException {
//         download and install shift
        driver.get(SHIFT_BASE_URL);
        DownloadShiftUtil downloadShiftUtil = new DownloadShiftUtil();
        this.shiftFiles = downloadShiftUtil.downloadShiftBrowserFile();
        downloadShiftUtil.installShift();

        // start appium
        new AppiumUtil().startAppium();

        // page objects for intro page
        IntroPage introPage = new IntroPage();
        introPage.togglePrivacyPolicyAcceptance();

        // finish installation
        introPage.finishInstallationSteps();
    }

    @Test(description = "This test downloads and verifies the browser version from .exe meta data",
    priority = 1)
    public void Test01_DownloadShiftBrowserTest_CheckVersionFromMetaData() throws Exception {
        Assert.assertNotNull(this.shiftFiles, "Download folder should be accessible");
        Assert.assertTrue(this.shiftFiles.length > 0, "No Shift exe file found in downloads!");

        Path exe = Files.list(TestBase.DOWNLOAD_DIR)
                .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".exe")).findAny().orElse(null);

        this.version = new PowerShellUtil().readExeVersionWithPowerShell(exe);
        logger.info("Installer FileVersion: " + version);
    }

    @Test(description = "Check Version of installed shift browser ", priority = 2)
    public void Test02_CheckVersionOfInstalledBrowser() throws InterruptedException {
        // wait until browser has fully loaded after installation:
        By quickSettings = AppiumBy.name("Quick Settings");
        new WebDriverWait(AppiumUtil.app, Duration.ofSeconds(30))
                .until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(quickSettings)));

        // start test
        new ClickUtil().robustClickOnElement("Quick Settings", AppiumUtil.app);
        new ClickUtil().robustClickOnElement("Advanced Settings", AppiumUtil.app);
        WebElement versionEl = AppiumUtil.app.findElement(
                AppiumBy.xpath("//Text[contains(@Name,'Version')]")
        );
        String versionText = versionEl.getAttribute("Name");
        Assert.assertTrue(versionText.contains(version), "Versions did not match!");
    }

    @AfterClass
    public void tearDown() {
        Path inno = Paths.get(System.getenv("LOCALAPPDATA"), "Shift", "unins000.exe");

        String cmd = "Start-Process -FilePath '" + inno + "' -ArgumentList '/VERYSILENT','/SUPPRESSMSGBOXES','/NORESTART' -Wait";
        String result = new PowerShellUtil().exec(cmd);
        Assert.assertEquals(result, "", "Shift never uninstalled");
        logger.info("Shift uninstalled successfully ");

        // deletes the .exe file in downloads
        if (shiftFiles != null) {
            for (File file : shiftFiles) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }
}
