package brokenlink;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrokenLinks {
	static {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
	}

	public static void main(String[] args) {
		HttpURLConnection huc=null;
		String homePage="https://www.facebook.com/";
		int status=200;
		WebDriver driver=new ChromeDriver();
		driver.get("https://www.facebook.com/");
		// List<WebElement> allLinks=driver.findElements(By.xpath("//a"));
		 List<WebElement> allLinks=driver.findElements(By.tagName("a"));
		 for(WebElement link:allLinks)
		 {
			 String url=link.getAttribute("href");
			 if(url==null || url.isEmpty())
			 {
				 System.out.println(url+"Anchor is not configured");
				 continue;//after continue control directly come to for loop
			 }
			 if(!url.startsWith(homePage))
			 {
				 System.out.println(url+"Third party Links");
				 continue;
			 }
			 else
			 {
				 try {
					URL ur=new URL(url);
					huc=(HttpURLConnection)ur.openConnection();
					huc.setRequestMethod("HEAD");
					huc.connect();
					status=huc.getResponseCode();
					if(status!=200)
					{
						System.out.println(url+"The Link Is Broken");
						
					}
					else
					{
						System.out.println(url+"The Link Is Working Fine");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
		int noOfLinks=allLinks.size();
		System.out.println("No of Links:"+noOfLinks);
		driver.close();

	}

}
