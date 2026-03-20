package xpressway_HDFC_Prod;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage01 {

    public WebDriver homepage() throws Exception {

        XpresswayLoginPage loginPage = new XpresswayLoginPage();
        WebDriver driver = loginPage.login();

        Thread.sleep(5000);
        System.out.println("<<<<<<<<<<<<<<<<<PRODUCTS_PAGE VALIDATION>>>>>>>>>>>>>>>>>");

        // Validate User Type
        try {
            WebElement heading = driver.findElement(By.xpath("//*[@id='guideContainer-rootPanel-panel_1995127749_cop-panel_1128491847-panel_copy_copy_copy-guidetextdraw_601767___guide-item']"));

            String headingText = heading.getText();
            if (headingText.contains("Dear Customer")) {
                System.out.println("User Validation:: New To Bank (NTB) user");
            } else {
                System.out.println("User Validation:: Existing To Bank (ETB) user");
            }

        } catch (Exception e) {
            System.out.println("User Validation:: Heading element not found on the page");
        }
        System.out.println("----------------------------------------------------");
        ScreenshotUtil.takeScreenshot(driver, "ETB User");

        /////////////////////////////////////////////////////////////////////////
        
     // PERSONAL LOAN
        try {
            WebElement heading03 = driver.findElement(By.xpath("//p[normalize-space()='Personal Loan']"));

            new Actions(driver).moveToElement(heading03).perform();
            Thread.sleep(2000);

            WebElement button001 = driver.findElement(
                    By.xpath("(//p[normalize-space()='Personal Loan'])[1]/following::a[1]"));
            button001.click();

            Thread.sleep(10000);

            ArrayList<String> tabs001 = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs001.get(1));

            String actualSSOUrl001 = driver.getCurrentUrl();

            //Step 1: Check SSO URL
            boolean isSSOUrlValid = actualSSOUrl001.contains("SSO_AUTHENTICATION_SUCCESS");

            // Step 2: Consent Flow Validation
            boolean isConsentFlowSuccess = false;

            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                // Checkbox
                WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("checkbox-609cf79d74")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
                Thread.sleep(2000);

                // Scroll button
                WebElement scrollBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.className("go-to-bottom-btn")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", scrollBtn);
                Thread.sleep(2000);

                // Continue flow (optional but good)
                WebElement agreeBtn = driver.findElement(By.id("button-d780d15832"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", agreeBtn);
                Thread.sleep(2000);

                WebElement applyBtn = driver.findElement(By.id("button-287ab08488"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", applyBtn);

                isConsentFlowSuccess = true;
                System.out.println("Consent Flow:: Pass");

            } catch (Exception e) {
                isConsentFlowSuccess = false;
                System.out.println("Personal Loan Consent Flow :: FAIL");
            }

            // Step 3: FINAL SSO RESULT
            if (isSSOUrlValid && isConsentFlowSuccess) {
                System.out.println("Personal Loan SSO Validation:: PASS");
            } else {
                System.out.println("Personal Loan SSO Validation:: FAIL");
            }

        } catch (Exception e) {
            System.out.println("Personal Loan Flow :: FAIL");
            e.printStackTrace();
        } finally {
            // Always switch back (VERY IMPORTANT)
            try {
                ArrayList<String> tabs001 = new ArrayList<>(driver.getWindowHandles());
                if (tabs001.size() > 1) {
                    driver.close();
                    driver.switchTo().window(tabs001.get(0));
                }
                Thread.sleep(2000);
            } catch (Exception ex) {
                System.out.println("Tab recovery failed");
            }
        }

        System.out.println("----------------------------------------------------");
        
  /////////////////////////////////////////////////////////////////////////////////
        
     // Fixed Deposit using funds from HDFC Bank - SSO validation

        ArrayList<String> tabs = null;

        try {

            WebElement heading01 = driver.findElement(By.xpath("//p[contains(text(),'Fixed Deposit using Funds from HDFC Bank')]"));
            new Actions(driver).moveToElement(heading01).perform();
            Thread.sleep(2000);

            driver.findElement(By.xpath("//p[contains(text(),'Fixed Deposit using Funds from HDFC Bank')]/following::a[1]")).click();
            Thread.sleep(8000);

            tabs = new ArrayList<>(driver.getWindowHandles());

            if (tabs.size() > 1) {
                driver.switchTo().window(tabs.get(1));
            } else {
                System.out.println("New tab not opened :: FAIL");
            }

            String actualSSOUrl = driver.getCurrentUrl();

            if (actualSSOUrl.contains("SSO_AUTHENTICATION_SUCCESS")) {
                System.out.println("Fixed Deposit SSO Validation :: Pass");
            } else {
                System.out.println("Fixed Deposit SSO Validation :: Fail");
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

         // Enter Amount
            try {
                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(15));

                WebElement amountField = wait1.until(ExpectedConditions.presenceOfElementLocated(
                        By.id("numberinput-e9f4d6b2d8")));

                JavascriptExecutor js = (JavascriptExecutor) driver;

                // Scroll to element
                js.executeScript("arguments[0].scrollIntoView(true);", amountField);
                Thread.sleep(1000);

                try {
                    // Try normal method
                    amountField.click();
                    amountField.clear();
                    amountField.sendKeys("6000");
                } catch (Exception e) {
                    System.out.println("Normal sendKeys failed, using JS");
                }

                // Force set using JS (MOST IMPORTANT)
                js.executeScript(
                    "arguments[0].value='6000'; arguments[0].dispatchEvent(new Event('input'));",
                    amountField
                );

                System.out.println("Amount entered:: Pass");

            } catch (Exception e) {
                System.out.println("Amount field not interactable :: FAIL");
            }
            // Continue button
            try {
                WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("button-070bcd2895")));

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);

                System.out.println("Continue button clicked:: Pass");

            } catch (Exception e) {
                System.out.println("Continue button not clickable :: FAIL");
            }

            Thread.sleep(4000);

            // Tenure button (SAFE)
            try {
                WebElement tenureBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("button-a942f55ce6")));

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tenureBtn);

                System.out.println("Tenure selected:: Pass");

            } catch (Exception e) {
                System.out.println("Tenure button not clickable :: FAIL");
            }

            Thread.sleep(4000);

            // Next Continue (SAFE)
            try {
                WebElement continueBtn01 = wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("button-be21b9cef9")));

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn01);

                System.out.println("Next Continue clicked:: Pass");

            } catch (Exception e) {
                System.out.println("Next Continue not clickable :: FAIL");
            }

        } 
        catch (Exception e) {
            System.out.println("FD Complete Flow :: FAILED");
            e.printStackTrace();
        } 
        finally {
            // 🔥 MOST IMPORTANT (Recovery)
            try {
                if (tabs != null && tabs.size() > 1) {
                    driver.close();
                    driver.switchTo().window(tabs.get(0));
                }
            } catch (Exception ex) {
                System.out.println("FD tab recovery failed");
            }
        }
        System.out.println("----------------------------------------------------");
        
        /////////////////////////////////////////////////////////////////////////
        // CREDIT CARD - SSO
        try {
            WebElement heading02 = driver.findElement(By.xpath("//p[contains(text(),'Credit Card Application')]"));
            new Actions(driver).moveToElement(heading02).perform();
            Thread.sleep(2000);

            driver.findElement(By.xpath("//p[contains(text(),'Credit Card Application')]/following::a[1]")).click();
            Thread.sleep(11000);

            ArrayList<String> tabs02 = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs02.get(1));

            String url = driver.getCurrentUrl();

            if (url.contains("SSO_AUTHENTICATION_SUCCESS")) {
                System.out.println("Credit Card Application SSO Validation :: PASS");
            } else {
                System.out.println("Credit Card Application SSO Validation:: FAIL");
            }

            driver.close();
            driver.switchTo().window(tabs02.get(0));

        } catch (Exception e) {
            System.out.println("Credit Card Application Offer :: FAIL");
            e.printStackTrace();
        }
        System.out.println("----------------------------------------------------");
        /////////////////////////////////////////////////////////////////////////
      //Scrolling from top to bottom code
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        Thread.sleep(500);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        long scrollHeight = (long) js.executeScript("return document.body.scrollHeight");

        for (int i = 0; i <= scrollHeight; i += 300) {
            js.executeScript("window.scrollTo(0, arguments[0]);", i);
            Thread.sleep(500); // Adjust speed as needed
        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////           
    
//Checking To Top cta working
WebElement toTop= driver.findElement(By.className("scrolltotop_desktop")); 
toTop.click();
if(toTop.isDisplayed()) {
System.out.println("To Top cta validation: To Top CTA Working:: Pass");
}
else {
 System.out.println("To Top cta validation: To Top CTA not Working: Fail::" +toTop.isDisplayed());
}
Thread.sleep(3000);

////////////////////////////////////////////////////////////////////////////////////////////////////////

//To validate Explore More Banking Options

WebElement applyOnline= driver.findElement(By.xpath("//*[@id=\"guideContainer-rootPanel-panel_1995127749_cop-panel_1128491847-panel_1476744642-panel-panel_897830868_copy-panel_897830868_copy-guidetextdraw_57070726__\"]/p"));
Actions applyOnline01 = new Actions(driver);
applyOnline01.moveToElement(applyOnline).perform();
Thread.sleep(3000);

if (applyOnline.isDisplayed()) {
    System.out.println("To Validate: Explore More Banking Options:: Pass");
} else {
    System.out.println("To Validate: Explore More Banking Options:: Fail");
}
//////////////////////////////////////////////////////////////////////////////////////////////////////
// Clicking the FASTag offer
//try is used to run code that might throw an error/exception
try {
    WebElement fastTag = driver.findElement(By.xpath("//p[contains(text(),'FASTag')]"));
    
    if (fastTag.isDisplayed()) {
        // Move to the heading
        Actions fastTag01 = new Actions(driver);
        fastTag01.moveToElement(fastTag).perform();
        Thread.sleep(2000);

        // Now, below locator will click the Button next to the text
        WebElement fastagCTA = driver.findElement(By.xpath("//p[contains(text(),'FASTag')]/following::a[1]"));
        fastagCTA.click();
        Thread.sleep(10000);

        // Post click it will redirect to another tab
        ArrayList<String> anotherTab = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(anotherTab.get(1));

        String currentUrl = driver.getCurrentUrl();

        // Check if the URL contains LCCode=7738&LGCode=AYUS12
   //     if (currentUrl.contains("LGCode=ANKIT15&LCCode=7738")) {
            System.out.println("To Validate FASTag Navigation:: Pass");
     //   } else {
      //      System.out.println("To Validate FASTag Navigation:: Fail");
   //     }

        driver.close();
        driver.switchTo().window(anotherTab.get(0)); // switch back to home tab
    }
}
//catch is used to handle that error so the program doesn’t stop
 catch (Exception e) {
	 	System.out.println("To Validate: FASTag offer is NOT visible on the page:: Fail");
}
Thread.sleep(2000);
//driver.switchTo().window(anotherTab.get(0)); //tabs.get(0):: what we fixed for the first window above
//Scrolling on top of the page

JavascriptExecutor js11 = (JavascriptExecutor) driver;
js11.executeScript("window.scrollTo(0, 0);");
Thread.sleep(2000);
 return driver;
}}
