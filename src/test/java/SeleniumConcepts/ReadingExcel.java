package SeleniumConcepts;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadingExcel {

	public static void main(String[] args) throws IOException {
		
		//Open Excel 
		FileInputStream fis=new FileInputStream("C:\\VISHNU\\Eclipse\\SeleniumProject\\Testdata\\testdata.xlsx");
		
		//Open workbook
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		
		//open sheet
		XSSFSheet sh=wb.getSheet("Sheet1");
		
		//count no of row and columns
		int totalrows=sh.getLastRowNum();
		int totalcells=sh.getRow(1).getLastCellNum();
		
	    System.out.println(totalrows);
	    System.out.println(totalcells);
	    
	    for(int r=0;r<=totalrows;r++)
	    {
	    	XSSFRow currentrow=sh.getRow(r);
	    	
	    	for(int c=0;c<totalcells;c++)
	    	{
	    		//XSSFCell cell=currentrow.getCell(c);
	    		//String value=cell.toString();
	    		
	    		String value=currentrow.getCell(c).toString();
	    		
	    		System.out.print(value+ "    ");
	    	}
	    	System.out.println();
	    }
	    
	    //close workbook
	    wb.close();
	    
	    //close file
	    fis.close();
		
		
     
	}

}
