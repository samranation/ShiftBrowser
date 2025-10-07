package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.options.WindowsOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class AppiumUtil {

    public static WebElement shiftWindow;
    public static WindowsDriver app;

    public void startAppium() {
        Path shiftExe = Paths.get(System.getenv("LOCALAPPDATA"), "Shift", "chromium", "shift.exe");
        await()
                .pollInterval(2, SECONDS)
                .atMost(3, MINUTES)
                .ignoreExceptions()
                .until(() -> Files.exists(shiftExe) && Files.isRegularFile(shiftExe));

        await().pollDelay(10, SECONDS)
                .pollInterval(5, SECONDS)
                .atMost(1, MINUTES).until(() -> {

                    WindowsOptions root = new WindowsOptions()
                            .setPlatformName("Windows")
                            .setAutomationName("Windows")
                            .setApp("Root");
                    WindowsDriver desktop = new WindowsDriver(new URL("http://127.0.0.1:4725"), root);

                    By shiftWin = AppiumBy.xpath(
                            "//*[@Name='Untitled - Shift Browser']"
                    );
                    this.shiftWindow = new WebDriverWait(desktop, Duration.ofSeconds(45))
                            .until(ExpectedConditions.presenceOfElementLocated(shiftWin));

                    this.shiftWindow.click();

                    String handleHex = Integer.toHexString(
                            Integer.parseInt(this.shiftWindow.getAttribute("NativeWindowHandle"))
                    );

                    WindowsOptions attach = new WindowsOptions()
                            .setAppTopLevelWindow(handleHex)
                            .amend("appium:newCommandTimeout", 900);

                    app = new WindowsDriver(new URL("http://127.0.0.1:4725"), attach);

                    WebDriverWait wait = new WebDriverWait(app, Duration.ofSeconds(5));
                    boolean eulaCheckboxDisplayed = false;
                    try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(
                                AppiumBy.xpath("//CheckBox[@Name='EULA and Privacy Policy Checkbox']"))
                        );
                        eulaCheckboxDisplayed = true;
                    } catch (WebDriverException ignore) {}

                    return eulaCheckboxDisplayed;
                });
    }

}
