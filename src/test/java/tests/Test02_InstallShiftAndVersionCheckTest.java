package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DownloadShiftUtil;
import utils.PowerShellUtil;


import java.io.File;
import java.util.Arrays;

public class Test02_InstallShiftAndVersionCheckTest extends TestBase {

    private File[] shiftFiles;

    @BeforeClass
    public void setup() {
        driver.get("https://shift.com/");
        this.shiftFiles = new DownloadShiftUtil().downloadShiftBrowserFile();
    }

    @Test(description = "Install Shift Browser ")
    public void Test02_InstallShiftBrowser()  {
        String installerPath = Arrays.stream(this.shiftFiles).findAny().get().toString();
        String cmd = "Start-Process -FilePath \"" + installerPath.replace("\\","\\\\") + "\" -ArgumentList '/S' -Wait";
        new PowerShellUtil().exec(cmd);
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
