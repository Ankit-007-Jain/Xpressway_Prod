package xpressway_HDFC_Prod;
import org.openqa.selenium.WebDriver;

public class MainMethod {

	public static void main(String[] args) throws Exception {
		 DigitalPlatformPage01 digit=new DigitalPlatformPage01();
		WebDriver driver=digit.digital();
		Thread.sleep(3000);
		driver.close();
	}
}
