
package xpressway_HDFC_Prod;

import java.awt.Desktop.Action;
import java.awt.Window;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ServicesPage01 {

	public WebDriver services() throws Exception {
		ProductsPage01 homey=new ProductsPage01(); //calling previous tab class
		WebDriver driver=homey.homepage(); //calling method from created object
		Thread.sleep(3000);
		
		System.out.println("<<<<<<<<<<<<<<<<<SERVICES_PAGE VALIDATION>>>>>>>>>>>>>>>>>");
		WebElement serviceCTA= driver.findElement(By.xpath("//span[text()='Services']"));
		serviceCTA.click();
		Thread.sleep(9000);
		System.out.println("To Validate Services tab navigation:: Able to Navigate Service Tab:: Pass");
		ScreenshotUtil.takeScreenshot(driver, "Services_Tab");
		ArrayList<String> tabs01=new ArrayList<>(driver.getWindowHandles()); //Creating ArrayList<string> and storing all tabs in that
		driver.switchTo().window(tabs01.get(1));
		driver.close();
        driver.switchTo().window(tabs01.get(0));
        Thread.sleep(3000);
			return driver;		
		 }
	}
