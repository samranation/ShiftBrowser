package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class PowerShellUtil {

    public String readExeVersionWithPowerShell(Path exePath) throws Exception {
        String ps = "(Get-Item -LiteralPath '" + exePath.toString().replace("'", "''") + "').VersionInfo.FileVersion";
        ProcessBuilder pb = new ProcessBuilder("powershell", "-NoProfile", "-Command", ps);
        pb.redirectErrorStream(true);
        Process proc = pb.start();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            String version = br.readLine();
            int exit = proc.waitFor();
            if (exit != 0 || version == null || version.isBlank()) {
                throw new RuntimeException("Failed to get FileVersion via PowerShell. Exit=" + exit);
            }
            return version.trim();
        }
    }

}
