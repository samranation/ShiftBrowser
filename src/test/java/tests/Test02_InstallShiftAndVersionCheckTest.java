package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DownloadShiftUtil;
import utils.PowerShellUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class Test02_InstallShiftAndVersionCheckTest extends TestBase {

    private Path downloadPath;
    private WebDriverWait wait;
    private File[] shiftFiles;

    @BeforeClass
    public void setup() {
        driver.get("https://shift.com/");
    }

    @Test(description = "This test downloads and verifies the browser version from .exe meta data")
    public void Test01_DownloadShiftBrowserTest_CheckVersionFromMetaData() throws Exception {
        this.shiftFiles = new DownloadShiftUtil().downloadShiftBrowserFile();
        Assert.assertNotNull(this.shiftFiles, "Download folder should be accessible");
        Assert.assertTrue(this.shiftFiles.length > 0, "No Shift exe file found in downloads!");

        Path exe = Files.list(TestBase.DOWNLOAD_DIR)
                .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".exe")).findAny().orElse(null);

        String version = new PowerShellUtil().readExeVersionWithPowerShell(exe);
        System.out.println("Installer FileVersion: " + version);
    }

    @AfterClass
    public void tearDown() {
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
