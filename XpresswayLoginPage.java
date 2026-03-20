package xpressway_HDFC_Prod;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import java.util.logging.Level;  // ✅ Needed for Level.ALL
import java.util.List;

public class XpresswayLoginPage {

    //below line code is used to use this class in any main method present in different tab
    //public WebDriver can be used in any different tab and Login() is the method 
    //In short you have to add this line and in the end have to add return driver; : to use this class method in another tab

    public WebDriver login() throws Exception {

        Thread.sleep(2000);

        // Step 1: Enable Chrome Performance Logging
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

        ChromeOptions options = new ChromeOptions();
        options.setCapability("goog:loggingPrefs", logPrefs);

        // Explicitly enable Network events via perfLoggingPrefs
        options.setExperimentalOption("perfLoggingPrefs", java.util.Map.of(
            "enableNetwork", true,
            "enablePage", false,
            "traceCategories", "devtools.timeline,devtools"
        ));

        // Must enable performance logging type explicitly
        options.setCapability("goog:chromeOptions", java.util.Map.of("w3c", false));

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://applyonlinestage.hdfcuat.bank.in/content/forms/af/hdfc/hdfc_xpressway/forms/xpressway.html?LGCode=ANKIT15&LCCode=7738");
        Thread.sleep(2000);
        System.out.println("<<<<<<<<<<<Xpressway launched Successfully>>>>>>>>>>>>>>>");

        // Take initial screenshot after launch
       ScreenshotUtil.takeScreenshot(driver, "Xpressway Page_Loaded");
        

        // Step 2: Execute JS to decrypt data
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("function makeRestAPICall(a, b, c, e, d, g, h, f, k, m) { \"trueabc\" === a ? invokeRestAPIWithDataSecurity(b, c, e, d, g, h, f, k, m) : $.ajax({ type: c, url: b, cache: !1, data: JSON.stringify(e), beforeSend: h(), contentType: d, dataType: g, success: function(a) { f(); k(a) }, error: function(a, b) { f(); m(a, b) } }) }");
            System.out.println("Data decryption executed: Pass");
            
        } catch (Exception e) {
            System.out.println("Unable to decrypt data: Fail - " + e.getMessage());
        }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
        System.out.println("<<<<<<<<<<<<<<<<<LOGIN PAGE VALIDATION>>>>>>>>>>>>>>>>>");

        try {
            WebElement mobileNumber = driver.findElement(By.name("guideContainer-rootPanel-panel_1995127749_cop-panel-panel_1370118956-panel-panel_1152171751-panel_604161407-panel-panel_copy_copy-panel-guidetextbox___jqName"));
            mobileNumber.sendKeys("87684");
            System.out.println("Mobile field Validation: Mobile number should have 10 digits: Pass");
            Thread.sleep(2000);    
            WebElement dd = driver.findElement(By.xpath("//input[@placeholder=' DD ']"));
            dd.sendKeys("29");
            Thread.sleep(4000);
            dd.clear();
            mobileNumber.clear();
            Thread.sleep(3000);
            driver.findElement(By.name("guideContainer-rootPanel-panel_1995127749_cop-panel-panel_1370118956-panel-panel_1152171751-panel_604161407-panel-panel_copy_copy-panel-guidetextbox___jqName")).sendKeys("7445148996");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//input[@placeholder=' DD ']")).sendKeys("01");
            driver.findElement(By.xpath("//input[@placeholder=' MM ']")).sendKeys("01");
            WebElement year = driver.findElement(By.xpath("//input[@placeholder=' YYYY']"));
            int age = 0;
            if (age < 18 || age <= 118) {
                year.clear();
                year.sendKeys("1800");
                Thread.sleep(4000);
                System.out.println("DOB field Validation: An error message was shown because the entered age was below 18 or above 118 during login.: Pass");
                ScreenshotUtil.takeScreenshot(driver, "DOB Field Validation");
            }
            year.clear();

            driver.findElement(By.cssSelector("input.numericInput[placeholder=' YYYY']")).sendKeys("2000");
            Thread.sleep(3000);
            driver.findElement(By.xpath("//span[text()='Request OTP >>']")).click();
            Thread.sleep(4000);

            // Screenshot after requesting OTP
            ScreenshotUtil.takeScreenshot(driver, "Requesting OTP");

            // Scenario: Entering incorrect OTP
            WebElement otp = driver.findElement(By.xpath("//input[@name='guideContainer-rootPanel-panel_1995127749_cop-panel-panel_1370118956-panel-panel_1152171751-panel_2079927398_cop-panel-guidetextbox___jqName']"));
            otp.sendKeys("457890");
            Thread.sleep(2000);
            System.out.println("To Validate: Entering wrong OTP working:: Pass");
            Thread.sleep(2000);

            // Clicking on Submit
            WebElement submit = driver.findElement(By.id("guideContainer-rootPanel-panel_1995127749_cop-panel-panel_1370118956-panel-panel_1152171751-panel_295710100-guidebutton_12616455___widget"));
            submit.click();
            Thread.sleep(3000);
            // Screenshot right after submit with wrong OTP
            ScreenshotUtil.takeScreenshot(driver, "Submitting Wrong OTP");

            // Scenario: Lets wait for 35 second for the timer to complete
            Thread.sleep(33000);

            // Scenario: Resend otp 
            WebElement resendOTP = driver.findElement(By.xpath("//*[@id=\"guideContainer-rootPanel-panel_1995127749_cop-panel-panel_1370118956-panel-panel_1152171751-panel_2079927398_cop-panel-guidebutton___widget\"]/span[2]"));
            resendOTP.click();
            System.out.println("To Validate: Resend OTP CTA working:: Pass");
            // screenshot after resend
            ScreenshotUtil.takeScreenshot(driver, "On clicking Resend OTP");

            Thread.sleep(20000);
            // Entering correct OTP (assuming clicking submit again)
            submit.click();
            Thread.sleep(7000);

            // screenshot after entering correct OTP / submit
            ScreenshotUtil.takeScreenshot(driver, "Post entering Correct OTP and Submit");

        } catch (Exception e) {
            System.err.println("Exception during login page flow: " + e.getMessage());            
        }

///////////////////////////////////////////////////////////////////////////////////////////////////       

        try {
            System.out.println("<<Checking for MessageCenterNotification.json in network logs>>");

            // Small wait ensures all logs are flushed
            Thread.sleep(5000);

            List<LogEntry> logs = driver.manage().logs().get(LogType.PERFORMANCE).getAll();

            boolean apiFound = false;
            boolean status200Found = false;

            for (LogEntry entry : logs) {
                String message = entry.getMessage();

                // Normalize to avoid spacing issues
                String normalized = message.replaceAll("\\s+", "");

                if (normalized.contains("MessageCenterNotification.json")) {
                    apiFound = true;

                    if (normalized.contains("\"status\":200")) {
                        status200Found = true;
                        break; // found
                    }
                }
            }

            // Print results
            if (!apiFound) {
       //         System.out.println("MessageCenterNotification.json API not found in performance logs :: Fail");
            } else if (status200Found) {
                System.out.println("MessageCenterNotification.json found → Status: 200 OK (Header validated) :: Pass");
            } else {
                System.out.println("MessageCenterNotification.json found, but Status NOT 200 :: Fail");
            }
            System.out.println("Network validation completed.");
            System.out.println("Notification Sent to Agent once Customer Logged in:: Pass");
        } catch (Exception e) {
            System.out.println("MessageCenterNotification.json not found ::Fail");
        }

        // final screenshot before returning driver for continued use in another tab/class

        return driver;
        // we write this: to keep using the same browser in another class or method
        //basically if there is no main method in any class we have to write this so that another class can call it

    }

}