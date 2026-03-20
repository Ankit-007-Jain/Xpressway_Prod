package xpressway_HDFC_Prod;

import java.time.Duration;
import java.util.ArrayList;

import javax.swing.text.TabableView;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DigitalPlatformPage01 {

	public WebDriver digital() throws Exception
	{
		ServicesPage01 service=new ServicesPage01(); //calling previous tab class
		WebDriver driver=service.services(); //calling previous tab method from created object
		
		WebElement digitalPlatform= driver.findElement(By.xpath("//span[text()='Digital Platforms']"));
		digitalPlatform.click();
		System.out.println("<<<<<<<<<<<<<<<<<DigitalPlatforms_PAGE VALIDATION>>>>>>>>>>>>>>>>>");
		Thread.sleep(3000);
///////////////////////////////////////////////////////////////////////////////////////////////////
//Validating Smart Wealth navigation
	try {
			
	WebElement hdfcSkyCTA=driver.findElement(By.xpath("//div[@class='digitalservice_heading' and normalize-space()='HDFC Sky']"));
	Actions actions = new Actions(driver);
	actions.moveToElement(hdfcSkyCTA).perform();
	Thread.sleep(4000);

//To navigate to Smartwealth and clicking
	hdfcSkyCTA.click();
	System.out.println("To Validate:: HDFC SKY service navigation:: Pass");
	//((JavascriptExecutor) driver).executeScript("arguments[0].click();", hdfcSkyCTA);
	Thread.sleep(8000);
	ArrayList<String> tab= new ArrayList<String>(driver.getWindowHandles());
	driver.switchTo().window(tab.get(1));	
	driver.close();
	driver.switchTo().window(tab.get(0));
		}
		catch(Exception e)
		{
			System.out.println("To Validate:: HDFC SKY service is not visible:: Fail");
			e.printStackTrace();
		}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
//CLICKING OFFERS
	Thread.sleep(3000);
//	
	JavascriptExecutor js1 = (JavascriptExecutor) driver;
	js1.executeScript("window.scrollTo(0, 0);");
	Thread.sleep(3000);	
	System.out.println("<<<<<<<<<<<<<<<<<Offers_PAGE VALIDATION>>>>>>>>>>>>>>>>>");
WebElement offers=driver.findElement(By.xpath("//span[text()='Card Offers']"));
if(offers.isDisplayed())
{
	offers.click();
	System.out.println("To Validate:: Card Offers tab is redirecting: Pass");
	Thread.sleep(8000);
}
else {
	System.out.println("To Validate:: Card Offers tab is not redirecting: Fail");
}
ArrayList<String> offersTab=new ArrayList<String>(driver.getWindowHandles());
driver.switchTo().window(offersTab.get(1));
driver.close();
driver.switchTo().window(offersTab.get(0));
Thread.sleep(4000);
driver.close();
//write all digital platform functionalities
//lets consider this final tab/class and next lets create main method and call DigitalPlatform class	
		return driver;		
	}

}