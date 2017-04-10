package com.test.integrationtesting;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ItemViewTest {

	private static URI siteBase;
	private static WebDriver drv;
	 
	@BeforeClass 
	public static void setUp() throws Exception {
	
	URL url = ItemViewTest.class.getClassLoader().getResource("selenium/chrome/chromedriver.exe");	
	System.setProperty("webdriver.chrome.driver", url.getPath());
	String driverProps = System.getProperty("webdriver.chrome.driver");
	siteBase = new URI("http://localhost:8080/testapp/itemView.html");
	drv = new ChromeDriver();

	}
	
	@Test
	public void testItemView() {
		drv.get(siteBase.toString());
		// Assert.assertTrue(drv.getPageSource().contains("Grocery List"));
		//drv.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		//WebElement item = drv.findElement(By.xpath("//*[@id='newItem']"));
		WebElement item = drv.findElement(By.id("newItem"));
		String hindu = "The Hindu";
		item.sendKeys(hindu);
		
		WebElement addBtn = drv.findElement(By.id("add_btn"));
		addBtn.click();
		drv.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		String indian = "Indian Express";
		item.sendKeys(indian);
		addBtn.click();
		
		List<WebElement> list = drv.findElements(By.tagName("li"));
		ArrayList<String> strList = new ArrayList<String>();
		
		for(WebElement li : list){
			strList.add(li.getText());
		}
		if(!strList.contains(hindu)){
			Assert.assertTrue(false);
		}
		if(!strList.contains(indian)){
			Assert.assertTrue(false);
		}
	 }
	
	
}
