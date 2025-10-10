package tests;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import pages.IntroPage;
import utils.AppiumUtil;
import utils.DownloadShiftUtil;
import utils.PowerShellUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.logging.Logger;

public class TestBase {

    protected static WebDriver firefoxDriver;
    protected static Path DOWNLOAD_DIR = Paths.get(System.getProperty("user.home"), "Downloads", "browser");
    protected static String SHIFT_BASE_URL = "https://shift.com/";
    protected File[] shiftFiles;

    private Logger logger = Logger.getLogger(TestBase.class.getName());


    @BeforeSuite
    public void beforeSuite() throws IOException {
        if (Files.exists(DOWNLOAD_DIR)) {
            this.deleteInstallationFilesInDownloads(DOWNLOAD_DIR.toFile().listFiles());
        }

        if (firefoxDriver == null) {
            Files.createDirectories(DOWNLOAD_DIR);

            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.download.dir", DOWNLOAD_DIR.toString());
            profile.setPreference("browser.download.useDownloadDir", true);
            FirefoxOptions options = new FirefoxOptions().setProfile(profile);
            options.addArguments("--headless");
            firefoxDriver = new FirefoxDriver(options);
            firefoxDriver.manage().window().maximize();
        }

        // download and install shift
        Path shiftPath = Paths.get(System.getenv("LOCALAPPDATA"), "Shift", "chromium", "shift.exe");
        if (! shiftPath.toFile().exists()) { // if already installed
            firefoxDriver.get(SHIFT_BASE_URL);
            DownloadShiftUtil downloadShiftUtil = new DownloadShiftUtil();
            this.shiftFiles = downloadShiftUtil.downloadShiftBrowserFile();
            downloadShiftUtil.installShift();

            new AppiumUtil().startAppium("//*[@Name='Untitled - Shift Browser']");
            IntroPage introPage = new IntroPage();
            introPage.togglePrivacyPolicyAcceptance();
            introPage.finishInstallationSteps();
        }

        // close browser
        AppiumUtil.app.closeApp();

        // quit firefox
        if (firefoxDriver != null) {
            firefoxDriver.quit();
            firefoxDriver = null;
        }
    }

    protected WebDriverWait defaultAppiumWait() {
        return new WebDriverWait(AppiumUtil.app, Duration.ofSeconds(30));
    }

    protected WebDriverWait customAppiumWait(long seconds) {
        return new WebDriverWait(AppiumUtil.app, Duration.ofSeconds(seconds));
    }

    protected void startBrowserAndMaximizeWindow() {
        new AppiumUtil().startAppium("//*[@ClassName='Chrome_WidgetWin_1' and contains(@Name,'- Shift Browser')]");
        // maximize app
        WebElement maximize = customAppiumWait(10).until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//*[@ClassName='Chrome_RenderWidgetHostHWND']" +
                        "//*[self::Text or @LocalizedControlType='button'][@Name='Maximize']")));
        maximize.click();
    }

    protected void minimizeWindowAndCloseApp() {
        // maximize app
        WebElement minimize = customAppiumWait(10).until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//*[@ClassName='Chrome_RenderWidgetHostHWND']" +
                        "//*[self::Text or @LocalizedControlType='button'][@Name='Minimize']")));
        minimize.click();
        AppiumUtil.app.closeApp();
    }

    @AfterSuite
    public void afterSuite() {
        Path inno = Paths.get(System.getenv("LOCALAPPDATA"), "Shift", "unins000.exe");

        String cmd = "Start-Process -FilePath '" + inno + "' -ArgumentList '/VERYSILENT','/SUPPRESSMSGBOXES','/NORESTART' -Wait";
        String result = new PowerShellUtil().exec(cmd);
        Assert.assertEquals(result, "", "Shift never uninstalled");
        logger.info("Shift uninstalled successfully ");

        this.deleteInstallationFilesInDownloads(this.shiftFiles);
    }

    private void deleteInstallationFilesInDownloads(File[] path) {
        if (path != null) {
            for (File file : path) {
                if (file.exists()) {
                        file.delete();
                }
            }
        }
    }
}
