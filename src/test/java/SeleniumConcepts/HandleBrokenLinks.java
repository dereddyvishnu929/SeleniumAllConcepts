package SeleniumConcepts;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HandleBrokenLinks {

	public static void main(String[] args) throws IOException {
		
		WebDriver driver=new ChromeDriver();
		
		driver.get("http://www.deadlinkcity.com/");
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		List<WebElement> alllinks=driver.findElements(By.tagName("a"));
		System.out.println("Totak number of links : " + alllinks.size());
		
		int brokenlinks=0;
		for(WebElement links:alllinks)
		{
			String hrefatt=links.getAttribute("href");
			
			if(hrefatt==null || hrefatt.isEmpty())
			{
				System.out.println("Href attrinbute value is empty");
				continue;
			}
			
			//convert link into URL format
			URL urllink=new URL(hrefatt);
			
			//send a request to open and connect
			HttpURLConnection conn=(HttpURLConnection)urllink.openConnection();
			conn.connect();
			
			if(conn.getResponseCode()>=400)
			{
				System.out.println(hrefatt +"   ==> "+ "Broken links" );
				brokenlinks++;
			}
			else
			{
				System.out.println(hrefatt +"   ==> "+ "Not a Broken links");
			}
		}
		driver.quit();
		

	}

}
