package utils;

import tests.TestBase;

import java.nio.file.Files;
import java.nio.file.Path;

public class VersionUtil extends TestBase {

    public String getVersion()  {
        try {
            Path exe = Files.list(TestBase.DOWNLOAD_DIR)
                    .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".exe")).findAny().orElse(null);

            return new PowerShellUtil().readExeVersionWithPowerShell(exe);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
