package SeleniumConcepts;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WritingData {

	public static void main(String[] args) throws IOException {
		
		FileOutputStream fos=new FileOutputStream(System.getProperty("user.dir")+"\\Testdata\\myfile.xlsx");
		
		XSSFWorkbook wb=new XSSFWorkbook();
		
		XSSFSheet sh=wb.createSheet();
		
		//Creating rows and cells and writing data without loop
		XSSFRow row1=sh.createRow(0);
		
		row1.createCell(0).setCellValue("Welcome");
		row1.createCell(1).setCellValue("To");
		row1.createCell(2).setCellValue("bangalore");
		
		XSSFRow row2=sh.createRow(1);
		
		row2.createCell(0).setCellValue("Welcome");
		row2.createCell(1).setCellValue("To");
		row2.createCell(2).setCellValue("Chennai");
		
		
		//Creating rows and cells and writing data with loop
		/*Scanner sc=new Scanner(System.in);
		
		for(int r=0;r<=3;r++)
		{
			XSSFRow row=sh.createRow(r);
		}
		for(int c=0;c<2;c++)
		{
			System.out.println("Enter a value");
			String value=sc.next();
			row.creatCell(c).setCellValue(value);
			
		}
		*/
		
		
		wb.write(fos);
		wb.close();
		fos.close();
		
		System.out.println("Writing is done!!!");
		

	}

}
