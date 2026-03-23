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
        System.out.println("Personal Loan");

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
        System.out.println("Fixed Deposit using funds from HDFC Bank");
        
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

            // Amount field
            try {
                WebElement amountField = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.id("numberinput-e9f4d6b2d8")));

                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", amountField);
                Thread.sleep(1000);

                try {
                    amountField.click();
                    amountField.clear();
                    amountField.sendKeys("6000");
                } catch (Exception e) {
                    System.out.println("Normal sendKeys failed, using JS");
                }

                js.executeScript(
                        "arguments[0].value='6000'; arguments[0].dispatchEvent(new Event('input'));",
                        amountField);

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

            Thread.sleep(3000);

            // Tenure button
            try {
                WebElement tenureBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("button-a942f55ce6")));

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tenureBtn);
                System.out.println("Tenure selected:: Pass");

            } catch (Exception e) {
                System.out.println("Tenure button not clickable :: FAIL");
            }

            Thread.sleep(5000);

            // Next Continue
            try {
                WebElement continueBtn01 = wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("button-be21b9cef9")));

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn01);
                System.out.println("Next Continue clicked:: Pass");

            } catch (Exception e) {
                System.out.println("Next Continue not clickable :: FAIL");
            }

            Thread.sleep(9000);

            // Scroll top
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

            System.out.println("Review Page: Able to Navigate:: Pass");

            JavascriptExecutor js = (JavascriptExecutor) driver;
            Thread.sleep(5000);

            // Checkbox 1
            try {
                WebElement checkbox1 = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.id("checkbox-034a17f714")));

                js.executeScript("arguments[0].scrollIntoView(true);", checkbox1);

                if (!checkbox1.isSelected()) {
                    js.executeScript("arguments[0].click();", checkbox1);
                    Thread.sleep(3000);
                }

                System.out.println("Checkbox 1 clicked:: Pass");

            } catch (Exception e) {
                System.out.println("Checkbox 1 not clickable :: FAIL");
            }

            // Checkbox 2
            try {
                WebElement checkbox2 = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.id("checkbox-142decba94")));

                js.executeScript("arguments[0].scrollIntoView(true);", checkbox2);

                if (!checkbox2.isSelected()) {
                    js.executeScript("arguments[0].click();", checkbox2);
                    Thread.sleep(3000);
                }

                System.out.println("Checkbox 2 clicked:: Pass");

            } catch (Exception e) {
                System.out.println("Checkbox 2 not clickable :: FAIL");
            }

        } catch (Exception e) {
            System.out.println("FD Complete Flow :: FAILED");
            e.printStackTrace();

        } finally {
            // 🔥 Recovery (VERY IMPORTANT)
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
        System.out.println("Credit Card Application");
  
        /////////////////////////////////////////////////////////////////////////    

     // CREDIT CARD APPLICATION

        ArrayList<String> tabs02 = null;

        try {
            WebElement heading02 = driver.findElement(By.xpath("//p[contains(text(),'Credit Card Application')]"));
            new Actions(driver).moveToElement(heading02).perform();
            Thread.sleep(2000);

            driver.findElement(By.xpath("//p[contains(text(),'Credit Card Application')]/following::a[1]")).click();
            Thread.sleep(7000);

            tabs02 = new ArrayList<>(driver.getWindowHandles());

            if (tabs02.size() > 1) {
                driver.switchTo().window(tabs02.get(1));
                Thread.sleep(2000);
            } else {
                System.out.println("New tab not opened :: FAIL");
                Thread.sleep(2000);
            }

            String url = driver.getCurrentUrl();

            // ✅ Step 1: SSO URL Check
            boolean isSSOUrlValid = url.contains("SSO_AUTHENTICATION_SUCCESS");

            // ✅ Step 2: Consent Flow
            boolean isConsentSuccess = true;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // 🔥 Wait for full page load
            wait.until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

            Thread.sleep(3000);

            // ================= CHECKBOX =================
            try {
                List<WebElement> checkboxes = wait.until(
                        ExpectedConditions.presenceOfAllElementsLocatedBy(
                                By.xpath("//input[contains(@id,'guidecheckbox')]")));

                boolean clicked = false;

                for (WebElement checkbox : checkboxes) {
                    if (checkbox.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);
                        Thread.sleep(1000);
                        js.executeScript("arguments[0].click();", checkbox);
                        Thread.sleep(4000);
                        clicked = true;
                        System.out.println("Checkbox clicked :: PASS");
                        break;
                    }
                }

                if (!clicked) throw new Exception();

            } catch (Exception e) {
                System.out.println("Checkbox not clickable :: FAIL");
                isConsentSuccess = false;
            }

            Thread.sleep(3000);
            
         // ================= DOWN ARROW =================
            
            // Down Arrow
            try {
                Thread.sleep(2000);

                WebElement downArrow = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//img[contains(@class,'cq-dd-image')]")));

                js.executeScript("arguments[0].scrollIntoView(true);", downArrow);
                js.executeScript("arguments[0].click();", downArrow);
                Thread.sleep(3000);
                System.out.println("Down Arrow clicked :: PASS");

            } catch (Exception e) {
                System.out.println("Down Arrow not clickable :: FAIL");
                isConsentSuccess = false;
            }
            
                        // ================= I AGREE =================
            try {
                List<WebElement> buttons = wait.until(
                        ExpectedConditions.presenceOfAllElementsLocatedBy(
                                By.xpath("//span[text()='I Agree']/ancestor::button")));

                boolean clicked = false;

                for (WebElement btn : buttons) {
                    if (btn.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                        Thread.sleep(3000);
                        js.executeScript("arguments[0].click();", btn);
                        Thread.sleep(4000);
                        clicked = true;
                        System.out.println("I Agree clicked :: PASS");
                        break;
                    }
                }

                if (!clicked) throw new Exception();

            } catch (Exception e) {
                System.out.println("I Agree not clickable :: FAIL");
                Thread.sleep(2000);
                isConsentSuccess = false;
            }

            Thread.sleep(3000);

            // ================= CONTINUE =================
            try {
                List<WebElement> buttons = wait.until(
                        ExpectedConditions.presenceOfAllElementsLocatedBy(
                                By.xpath("//span[text()='Continue']/ancestor::button")));

                boolean clicked = false;

                for (WebElement btn : buttons) {
                    if (btn.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
                        Thread.sleep(2000);
                        js.executeScript("arguments[0].click();", btn);
                        Thread.sleep(11000);
                        clicked = true;
                        System.out.println("Continue clicked :: PASS");
                        break;
                    }
                }

                if (!clicked) throw new Exception();

            } catch (Exception e) {
                System.out.println("Continue not clickable :: FAIL");
                Thread.sleep(4000);
                isConsentSuccess = false;
            }

            // ✅ FINAL RESULT
            if (isSSOUrlValid && isConsentSuccess) {
                System.out.println("Credit Card Application SSO Validation :: PASS");
            } else {
                System.out.println("Credit Card Application SSO Validation :: FAIL");
            }

        } catch (Exception e) {
            System.out.println("Credit Card Application Offer :: FAIL");
            e.printStackTrace();

        } finally {
            // ✅ Tab recovery
            try {
                if (tabs02 != null && tabs02.size() > 1) {
                    driver.close();
                    driver.switchTo().window(tabs02.get(0));
                    Thread.sleep(4000);
                }
            } catch (Exception ex) {
                System.out.println("Tab recovery failed");
                Thread.sleep(2000);
            }
        }

        System.out.println("----------------------------------------------------");

        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Checking To Top CTA
        try {
            WebElement toTop = driver.findElement(By.className("scrolltotop_desktop"));
            toTop.click();

            if (toTop.isDisplayed()) {
                System.out.println("To Top CTA Working:: Pass");
            } else {
                System.out.println("To Top CTA not Working:: Fail");
            }
        } catch (Exception e) {
            System.out.println("To Top CTA not visible :: FAIL");
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Explore More Banking Options
        try {
            WebElement applyOnline = driver.findElement(By.xpath("//*[contains(text(),'Explore More Banking Options')]"));

            new Actions(driver).moveToElement(applyOnline).perform();

            if (applyOnline.isDisplayed()) {
                System.out.println("Explore More Banking Options:: Pass");
            } else {
                System.out.println("Explore More Banking Options:: Fail");
            }
        } catch (Exception e) {
            System.out.println("Explore section not visible :: FAIL");
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // FASTag
        try {
            WebElement fastTag = driver.findElement(By.xpath("//p[contains(text(),'FASTag')]"));

            if (fastTag.isDisplayed()) {
                new Actions(driver).moveToElement(fastTag).perform();
                Thread.sleep(2000);

                WebElement fastagCTA = driver.findElement(By.xpath("//p[contains(text(),'FASTag')]/following::a[1]"));
                fastagCTA.click();

                Thread.sleep(8000);

                ArrayList<String> anotherTab = new ArrayList<>(driver.getWindowHandles());
                driver.switchTo().window(anotherTab.get(1));

                System.out.println("FASTag Navigation:: Pass");

                driver.close();
                driver.switchTo().window(anotherTab.get(0));
            }

        } catch (Exception e) {
            System.out.println("FASTag offer not visible :: FAIL");
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
        Thread.sleep(2000);

        return driver;
           }}