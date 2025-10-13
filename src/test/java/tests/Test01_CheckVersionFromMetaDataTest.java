package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.VersionUtil;

import java.util.logging.Logger;

public class Test01_CheckVersionFromMetaDataTest extends TestBase {

    private Logger logger = Logger.getLogger(Test01_CheckVersionFromMetaDataTest.class.getName());

    @Test(description = "This test downloads and verifies the browser version from .exe meta data")
    public void test01_DownloadShiftBrowserTest_CheckVersionFromMetaData() {
        Assert.assertNotNull(this.shiftFiles, "Download folder should be accessible");
        Assert.assertTrue(this.shiftFiles.length > 0, "No Shift exe file found in downloads!");

        String version = new VersionUtil().getVersion();
        Assert.assertNotNull(version, "Version should not be null");
        logger.info("Installer FileVersion: " + version);
    }
}
