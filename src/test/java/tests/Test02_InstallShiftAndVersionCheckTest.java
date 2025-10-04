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

    private File[] shiftFiles;

    @BeforeClass
    public void setup() {
        this.shiftFiles = new DownloadShiftUtil().downloadShiftBrowserFile();
    }

    @Test(description = "Install Shift Browser ")
    public void Test02_InstallShiftBrowser()  {
        
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
