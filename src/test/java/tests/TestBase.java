package tests;

import io.appium.java_client.windows.WindowsDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TestBase {

    protected static WebDriver driver;
    protected static Path DOWNLOAD_DIR = Paths.get(System.getProperty("user.home"), "Downloads", "browser");
    protected static WebDriverWait wait;
    protected static WindowsDriver driverWindows;

    @BeforeClass
    public void setUp() throws IOException {
        if (driver == null) {
            Files.createDirectories(DOWNLOAD_DIR);

            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.download.dir", DOWNLOAD_DIR.toString());
            profile.setPreference("browser.download.useDownloadDir", true);
            FirefoxOptions options = new FirefoxOptions().setProfile(profile);
            driver = new FirefoxDriver(options);
            driver.manage().window().maximize();
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
