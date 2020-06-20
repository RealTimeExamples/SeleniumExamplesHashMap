package org.totalqa.seleniumexample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class  HashMapSeleniumExample 
{
	public static HashMap<String,String> sharePriceExcelValuesfromXLS;
	public static HashMap<String,String> sharePriceExcelValuesfromWEB;

	public static void main(String args[]) throws BiffException, IOException
	{
		 
		sharePriceExcelValuesfromXLS = readXLSFile("SharePriceList.xls");
		sharePriceExcelValuesfromWEB = readDatafromWEB();
		/**
		 * HashMap1 : Storing the values from WebPage
		 * HashMap2 : Storing the value from Excel
		 * Compare the values to validate the data
		 * 
		 * 
		 */
		String expected = sharePriceExcelValuesfromXLS.get("Tata Motors Ltd.");
		String actual   = sharePriceExcelValuesfromWEB.get("Tata Motors Ltd.");

		if(actual.equals(expected))
		{
			System.out.println("TC Pass");
		}
		else
		{
			System.out.println("TC Fails");
		}

		Set<String> keyList = sharePriceExcelValuesfromXLS.keySet();
		for(String key : keyList)
		{
			System.out.println(key);
			if(sharePriceExcelValuesfromXLS.get(key).equals(sharePriceExcelValuesfromWEB.get(key)))
				System.out.println("Pass for the " + key);

			else
				System.out.println("Fail for the " + key);

		}
	}
	public static HashMap<String,String> readDatafromWEB()
	{
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://money.rediff.com/losers/bse/daily/groupa?src=gain_lose");
		List<WebElement> currentPriceList = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr/td[4]"));
		List<WebElement> companyList = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr/td[1]"));
		System.out.println("Size of the List ::" +currentPriceList.size() );
		HashMap<String,String> sharePriceExcelValuesfromWEB = new HashMap<String,String>();
		for(int i = 0 ;i <2;i++)
		{
			System.out.println("Key ::" +companyList.get(i).getText().trim()  +"Value ::"+currentPriceList.get(i).getText().trim() );
			sharePriceExcelValuesfromWEB.put(companyList.get(i).getText().trim(), currentPriceList.get(i).getText().trim());
		}
		return sharePriceExcelValuesfromWEB;

	}
	public static HashMap<String,String> readXLSFile(String fileName) throws IOException, BiffException
	{
		//Reading the data from XLS
		FileInputStream fis = new FileInputStream(new File(fileName));
		Workbook wb = Workbook.getWorkbook(fis);
		Sheet sheet = wb.getSheet(0);
		int rows = sheet.getRows();
		HashMap<String,String> sharePriceExcelValuesfromXLS = new HashMap<String,String>();
		for(int i = 0;i<rows;i++)
		{

			Cell cell = sheet.getCell(0,i);
			String key = cell.getContents();

			cell  = sheet.getCell(1,i);
			String value= cell.getContents();

			System.out.print("Key ::" + key);
			System.out.print("Value::" + value);
			sharePriceExcelValuesfromXLS.put(key,value);
			System.out.println();
		}
		return sharePriceExcelValuesfromXLS;
	}

}

//for(int i = 0 ;i <currentPriceList.size();i++)
//{
//	if(companyList.get(i).getText().trim().equals("Vedanta"))
//	{
//			System.out.println(currentPriceList.get(i).getText());
//			break;
//	}
//}