package week4.day2.project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



public class ZoomCar {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		
		driver.get("https://www.zoomcar.com/chennai");
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		
		driver.findElementByLinkText("Start your wonderful journey").click();
		
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		
		driver.findElementByXPath("//div[@class='component-popular-locations']//div[contains(text(),'Thuraipakkam')]").click();
		
		driver.findElementByXPath("//div[@class='actions']//button[text()='Next']").click();
		
		//To get tomorrows date:
		
		Date date = new Date(); // Get the current date
		DateFormat sdf = new SimpleDateFormat("dd"); //Get only the date (and not month, year, time etc)
		String today = sdf.format(date); // Get today's date
		int tomorrow = Integer.parseInt(today)+1; // Convert to integer and add 1 to it
		System.out.println(tomorrow);
		
		//div[text()='days']
		
		List<WebElement> lst_Dates = driver.findElementsByXPath("//div[@class='days']/div[starts-with(@class,'day')]");
		
		for(int i=0;i<lst_Dates.size();i++)
		{
			System.out.println(lst_Dates.get(i).getText());
			if(lst_Dates.get(i).getText().contains(tomorrow+""))
			{
				lst_Dates.get(i).click();
				break;
			}
		}
		
		
		driver.findElementByXPath("//button[@class='proceed']").click();
		
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		String strFinalilizedDate = driver.findElementByXPath("//div[@class='day picked low-price']").getText();
		
		
		driver.findElementByXPath("//button[text()='Done']").click();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		List<WebElement> lst_All_Results = driver.findElementsByXPath("//div[@class='car-list-layout']//div[@class='car-listing']");
		int intNoOfResults = lst_All_Results.size();
		
		
		Map<String,Integer> mapObj = new LinkedHashMap();
		
		
		
		for(int i=0;i<intNoOfResults-1;i++)
		{
			String eachCarName = driver.findElementByXPath("(//div[@class='car-item']//div[@class='details']/h3)["+(i+1)+"]").getText();
			//div[@class='car-list-layout']//div[@class='car-listing']//div[@class='price'])[1]
			String eachCarPrice_WithoutComma = driver.findElementByXPath("(//div[@class='car-list-layout']//div[@class='car-listing']//div[@class='price'])["+(i+1)+"]").getText();
			int eachCarPrice = Integer.parseInt(eachCarPrice_WithoutComma.replaceAll("\\D", ""));
			
			mapObj.put(eachCarName, eachCarPrice);
			
		}
		
		System.out.println(mapObj);
		
		Set<Integer> sortedSet = new TreeSet<Integer>(mapObj.values());
		List<Integer> lstSorted = new ArrayList(sortedSet);
		
		
		System.out.println(sortedSet);
		
		int intHighestPrice = lstSorted.get(lstSorted.size()-1);
		String strHighestCarName = "";
		
		for(Entry<String, Integer> each: mapObj.entrySet())
		{
			if(each.getValue()==intHighestPrice)
			{
				strHighestCarName = each.getKey();
			}
			
		}
		
		System.out.println("car name : "+strHighestCarName +" and Price : "+intHighestPrice);
		
		Thread.sleep(4000);
		
		driver.findElementByXPath("//div[@class='details']/h3[text()='"+strHighestCarName+"']/parent::div/parent::div[@class='car-item']//button[text()='BOOK NOW']").click();
		
	}

}
