/**
 * 
 */
package homePage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.pagefactory.ByChained;

/**
 * @author Manthan
 *
 */
public class HomePageLinks {

	private WebDriver driver;
	private String baseUrl;	
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(HomePageLinks.class);

    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        baseUrl = "https://www.freecodecamp.com/";        
        
    }

    @Test
    public void testLinks(){
        driver.get(baseUrl);
        List<WebElement> linkList = driver.findElements(By.tagName("a"));
        ArrayList<String> linkNames = new ArrayList<String>();
        for(WebElement linkElement : linkList){
        	if(linkElement.getText().length() > 0)
        		linkNames.add(linkElement.getText());
        }               
        
        for(String linkName : linkNames){
        	log.debug("Link being checked : "+linkName);
        	WebElement currentElement = driver.findElement(By.linkText(linkName));        	
        	JavascriptExecutor js = (JavascriptExecutor) driver;
        	//to scroll screen to appropriate location to avoid overlapping of elements
        	js.executeScript("scroll(0,"+currentElement.getLocation().y+(-350)+")");
        	currentElement.click();
        	log.debug("Title of page: "+driver.getTitle());
        	
        	try{// for cases having aside frames
        		if(!linkName.toLowerCase().equalsIgnoreCase("chat") && driver.findElement(new ByChained(By.className(linkName.toLowerCase()+"-aside"))) != null){
        			driver.findElement(new ByChained(By.className(linkName.toLowerCase()+"-aside"),By.tagName("div"),By.tagName("button"))).click();        		
        		}
        		else if(driver.findElement(new ByChained(By.className("gitter-"+linkName.toLowerCase()+"-embed"))) != null){ // for chat window
        			driver.findElement(new ByChained(By.className("gitter-"+linkName.toLowerCase()+"-embed"),By.tagName("div"),By.tagName("button"))).click();
        		}
        	}catch(NoSuchElementException e){
    			//normal 
        		driver.navigate().back();
        	}
        }
    }  
    
    @After
    public void closureActivities(){
        /*driver.quit();*/
    }
}
