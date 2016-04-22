/**
 * 
 */
package map;

import static org.junit.Assert.assertTrue;
import homePage.HomePageLinks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.pagefactory.ByChained;

/**
 * @author Manthan
 *
 */
public class ChallengesOfMap {

	private WebDriver driver;
	private String baseUrl;	
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ChallengesOfMap.class);

    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        baseUrl = "https://www.freecodecamp.com/";        
        
    }
    
    @Test
    public void testFunctionalButtonsOnEachPage(){
        driver.get(baseUrl);        
        driver.findElement(By.xpath("//*[@id=\"nav-map-btn\"]")).click();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"nested-collapseJointheFreeCodeCampCommunity\"]/p[1]")));
        System.out.println(driver.findElement(new ByChained(By.className("map-aside"),By.id("map-aside-frame"),
        		By.xpath("/html/body/div[3]"))).findElements(By.tagName("div")).size());
        
        
      //System.out.println(driver.findElement(By.xpath("//*[@id=\"accordion\"]/h2[1]/a")));
      //*[@id="nested-collapseJointheFreeCodeCampCommunity"]/p[1]
      //*[@id="nested-collapseJointheFreeCodeCampCommunity"]/p[6]
      //*[@id="nested-collapseHTML5andCSS"]/p[1]   
      //*[@id="accordion"]        
      //System.out.println("OOOO-----"+driver.findElement(By.xpath("//*[@id=\"collapseGetting-Started\"]")));	
        //System.out.println(driver.findElement(By.className("map-aside")).findElement(By.id("map-aside-frame")));
        
        List<WebElement> linkList = driver.findElements(By.tagName("a"));
        ArrayList<String> linkNames = new ArrayList<String>();
        for(WebElement linkElement : linkList){
        	//if(linkElement.getText().length() > 0)
        		//linkNames.add(linkElement.getText());
        	System.out.println(linkElement.getText());
        }
        
        /*driver.findElement(By.id("email")).sendKeys("almamatter@google.com");        
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/div[2]/button")).click();
        WebElement existingUserAlert = driver.findElement(By.className("alert"));        
        try{
    		assertTrue(existingUserAlert.getText().contains("e-mail has been sent to "));
    		driver.findElement(new ByChained(By.className("alert"),By.className("close"))).click();
    	}catch(AssertionError ae){
    		System.out.println("Error: Valid email not being accepted in Forgot password..!!!!!");
    	}*/
        
    }                 
	
	@After
    public void closureActivities(){
        /*driver.quit();*/
    }
}
