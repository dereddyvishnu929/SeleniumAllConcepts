package SeleniumConcepts;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class Uploadingfile_using_AutoIt {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		Thread.sleep(3000);
		
		//Launching browser
		driver.get("https://www.ilovepdf.com/word_to_pdf");
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		//Clicking on Select Word file
		WebElement fileuploadelement = driver.findElement(By.xpath("//span[contains(text(),'Select WORD files')]//ancestor::a"));
		fileuploadelement.click();
		
		//Open the upload file / open file dailog
		String filepath = "C:\\VISHNU\\Vishnu.docx";
		ProcessBuilder pb = new ProcessBuilder("C:\\VISHNU\\OneDrive\\Desktop\\AutoIt\\Uploadfile.exe",filepath);
		try {
			pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WebElement element2 = driver.findElement(By.xpath("//span[contains(text(),'Convert to PDF')]//ancestor::button"));
		element2.click();
		Thread.sleep(10000);
		WebElement element3 = driver.findElement(By.xpath("//h1[contains(text(),'WORD file has been converted to PDF')]"));
		boolean isWordfilebeenconvertedtopdf = element3.isDisplayed();
		Assert.assertTrue(isWordfilebeenconvertedtopdf, "Word file been converted to not pdf");

	}

}
