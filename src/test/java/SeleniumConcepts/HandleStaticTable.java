package SeleniumConcepts;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HandleStaticTable {

	public static void main(String[] args) {
		
		 WebDriver driver =new ChromeDriver();
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
			driver.get("https://testautomationpractice.blogspot.com/");
			driver.manage().window().maximize();
			
			//1)Find no of rows
			int rows=driver.findElements(By.xpath("//table[@name='BookTable']//tr")).size();
			System.out.println("Number of Rows : " + rows);
			
			//2)Find no of coiumns
			int cols=driver.findElements(By.xpath("//table[@name='BookTable']//th")).size();
			System.out.println("Number of Rows : " + cols);
			
			//3)Find Specific row and cloumn data
			String text=driver.findElement(By.xpath("//table[@name='BookTable']//tr[5]//td[1]")).getText();
			System.out.println(text);
			
			//4)Read all the data from the table
			System.out.println("BookName " + " Author " + " Subject " + " Price");
			for(int r=2;r<=rows;r++)
			{
				for(int c=1;c<=cols;c++)
				{
					String text1=driver.findElement(By.xpath("//table[@name='BookTable']//tr["+r+"]//td["+c+"]")).getText();
					System.out.print(text1);
				}
				System.out.println();
			}
			
			//5)Print book names whose author is Amit
			for(int r=2;r<=rows;r++)
			{
			    String author=driver.findElement(By.xpath("//table[@name='BookTable']//tr["+r+"]/td[2]")).getText();
			    if(author.equals("Amit"))
			    {
			    	String bookname=driver.findElement(By.xpath("//table[@name='BookTable']//tr["+r+"]/td[1]")).getText();
			    	System.out.println(author +"   " + bookname);
			    }
			}
			
			//Find sum of all the book prices
			int sum=0;
			for(int r=2;r<=rows;r++)
			{
			     String price=driver.findElement(By.xpath("//table[@name='BookTable']//tr["+r+"]//td[4]")).getText();
			     sum=sum+Integer.parseInt(price);
			     
			}
			System.out.println("Total price of books " + sum);
			
			driver.quit();

	}

}
