package SeleniumConcepts;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CaptureSceenShots {

	public static void main(String[] args) throws IOException {
		

		WebDriver driver=new ChromeDriver();
		
		driver.get("https://www.opencart.com/index.php?route=cms/demo");
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//Capture screen shot
		TakesScreenshot ts=(TakesScreenshot)driver;
		
		File src=ts.getScreenshotAs(OutputType.FILE);
		File target=new File("C:\\VISHNU\\Eclipse\\SeleniumProject\\ScreenShots\\fullpage.png");
		FileUtils.copyFile(src, target);
		
		//Capturing screenshot for particular webwlwment
		WebElement logo=driver.findElement(By.xpath("//img[@title='OpenCart - Demo']"));
		File src1=logo.getScreenshotAs(OutputType.FILE);
		File target1=new File("C:\\VISHNU\\Eclipse\\SeleniumProject\\ScreenShots\\logo.png");
		FileUtils.copyFile(src1, target1);
		

	}

}
