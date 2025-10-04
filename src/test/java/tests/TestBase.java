package tests;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

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
    public void setUp(){
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            // allows the files to be downloaded
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("safebrowsing.enabled", false);
            prefs.put("download.default_directory", DOWNLOAD_DIR.toFile().getAbsolutePath());
            prefs.put("download.prompt_for_download", false);
            prefs.put("download.directory_upgrade", true);
            options.setExperimentalOption("prefs", prefs);

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // windows driver
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("app", "");
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
