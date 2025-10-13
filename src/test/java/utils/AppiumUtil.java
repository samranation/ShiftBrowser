package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.options.WindowsOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import tests.TestBase;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class AppiumUtil extends TestBase {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AppiumUtil.class);
    public static WindowsDriver app;
    private Logger logger = Logger.getLogger(AppiumUtil.class.getName());
    public static Path shiftExe;
    public static WebElement browserWindow;

    public void startAppium(String element) {
        shiftExe = Paths.get(System.getenv("LOCALAPPDATA"), "Shift", "chromium", "shift.exe");
        await().pollInterval(2, SECONDS)
                .atMost(3, MINUTES)
                .ignoreExceptions()
                .until(() -> Files.isRegularFile(shiftExe));

        await().atMost(Duration.ofMinutes(2))
                .ignoreExceptions()
                .until(() -> {
                    WindowsOptions launch = new WindowsOptions()
                            .setApp(shiftExe.toString())
                            .amend("appium:newCommandTimeout", 900);
                    app = new WindowsDriver(this.appiumServerUrl(), launch);
                    String title = app.getTitle();
                    return title != null && !title.isEmpty();
                });
        logger.info("Shift launched; waiting for its top-level window…");

        WindowsOptions root = new WindowsOptions()
                .setPlatformName("Windows")
                .setAutomationName("Windows")
                .setApp("Root");
        WindowsDriver desktop = new WindowsDriver(this.appiumServerUrl(), root);

        if (element.equals("//*[@Name='Untitled - Shift Browser']")) {
            customAppiumWait(60).until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//CheckBox[@Name='EULA and Privacy Policy Checkbox']"))
            );
        }

        By shiftWin = AppiumBy.xpath(element);
        browserWindow = new WebDriverWait(desktop, Duration.ofSeconds(60))
                .until(ExpectedConditions.presenceOfElementLocated(shiftWin));

        String handleHex = Integer.toHexString(
                Integer.parseInt(browserWindow.getAttribute("NativeWindowHandle"))
        ).toUpperCase();

        WindowsOptions attach = new WindowsOptions()
                .setAppTopLevelWindow(handleHex)
                .amend("appium:newCommandTimeout", 900);
        app = new WindowsDriver(this.appiumServerUrl(), attach);
        logger.info("Ready to start testing!");

        // Bring to foreground
        browserWindow.click();
        logger.info("Shift window found and focused.");
    }

    private URL appiumServerUrl() {
        try {
            return new URL("http://127.0.0.1:4725");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
