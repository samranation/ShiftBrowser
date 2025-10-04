package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.TestBase;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class DownloadShiftUtil extends TestBase {

    private File[] shiftFiles;

    public File[] downloadShiftBrowserFile() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement downloadButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".link-button.link-small.link-primary")
                )
        );

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", downloadButton);

        downloadButton.click();

        Path browserDir = TestBase.DOWNLOAD_DIR;

        await().atMost(60, TimeUnit.SECONDS).pollInterval(2, TimeUnit.SECONDS).until(() -> {
            File folder = browserDir.toFile();
            shiftFiles = folder.listFiles((dir, name) ->
                    name.toLowerCase().contains("shift") && name.endsWith(".exe"));
            return shiftFiles != null && shiftFiles.length > 0 && shiftFiles[0].length() > 0;
        });

        return shiftFiles;
    }
}
