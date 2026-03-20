package xpressway_HDFC_Prod;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {

    // Change this if you want a different folder (can be relative like "screenshots")
    private static final String SCREENSHOT_DIR = "C:\\Screenshots";

    private static void ensureDirExists() {
        File dir = new File(SCREENSHOT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Takes a screenshot and returns absolute path of saved file or null on failure.
     * namePrefix will be sanitized and timestamp appended.
     */
    public static String takeScreenshot(WebDriver driver, String namePrefix) {
        if (driver == null) return null;
        try {
            ensureDirExists();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String safeName = (namePrefix == null || namePrefix.trim().isEmpty())
                    ? "screenshot"
                    : namePrefix.replaceAll("[^a-zA-Z0-9_\\-]", "_");
            String fileName = safeName + "_" + timestamp + ".png";
            File dest = new File(SCREENSHOT_DIR + File.separator + fileName);

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, dest);

            return dest.getAbsolutePath();
        } catch (IOException ioe) {
            System.err.println("IOException saving screenshot: " + ioe.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error taking screenshot: " + e.getMessage());
            return null;
        }
    }
}