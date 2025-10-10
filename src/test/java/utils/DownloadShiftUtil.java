package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.TestBase;

import java.io.File;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.awaitility.Awaitility.await;

public class DownloadShiftUtil extends TestBase {

    private File[] shiftFiles;
    private Logger logger = Logger.getLogger(DownloadShiftUtil.class.getName());

    public File[] downloadShiftBrowserFile() {
        WebDriverWait wait = new WebDriverWait(firefoxDriver, Duration.ofSeconds(10));
        WebElement downloadButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".link-button.link-small.link-primary")
                )
        );

        JavascriptExecutor js = (JavascriptExecutor) firefoxDriver;
        js.executeScript("arguments[0].scrollIntoView(true);", downloadButton);

        // robust click
        try {
            new Actions(firefoxDriver).doubleClick(downloadButton).perform();
            try {
                wait.withTimeout(Duration.ofSeconds(3))
                        .until(ExpectedConditions.stalenessOf(downloadButton));
            } catch (TimeoutException ignore) {
                downloadButton.click();
            }
        } catch (Exception ignore) {}

        Path browserDir = TestBase.DOWNLOAD_DIR;

        await().atMost(60, TimeUnit.SECONDS).pollInterval(2, TimeUnit.SECONDS).until(() -> {
            File folder = browserDir.toFile();
            shiftFiles = folder.listFiles((dir, name) ->
                    name.toLowerCase().contains("shift") && name.endsWith(".exe"));
            return shiftFiles != null && shiftFiles.length > 0 && shiftFiles[0].length() > 0;
        });

        logger.info("Shift installation files downloaded successfully!");

        return shiftFiles;
    }

    public void installShift() {
        String installerPath = Arrays.stream(this.shiftFiles).findAny().get().toString();
        String cmd = "Start-Process -FilePath \"" + installerPath.replace("\\","\\\\") + "\"; Start-Sleep -Seconds 10";
        new PowerShellUtil().exec(cmd);
        logger.info("Shift installation has started successfully!");
    }
}
