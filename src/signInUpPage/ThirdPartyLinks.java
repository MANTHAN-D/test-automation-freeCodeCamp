/**
 * 
 */
package signInUpPage;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Manthan
 *
 */
public class ThirdPartyLinks {
	private WebDriver driver;
	private String baseUrl;	
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ThirdPartyLinks.class);

    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        baseUrl = "https://www.freecodecamp.com/signin";        
        
    }
    
	@Test
    public void testNavigations(){
        driver.get(baseUrl);
        List<WebElement> linkList = driver.findElements(By.tagName("a"));
        ArrayList<String> linkNames = new ArrayList<String>();
        for(WebElement linkElement : linkList){
        	if(linkElement.getText().length() > 0 && 
        			(linkElement.getAttribute("href").contains("/auth/") || 
        					linkElement.getAttribute("href").contains("/email-")))
        		linkNames.add(linkElement.getText());        	
        }               
        
        for(String linkName : linkNames){
        	log.debug("Link being checked : "+linkName);
        	WebElement currentElement = driver.findElement(By.linkText(linkName));        	
        	JavascriptExecutor js = (JavascriptExecutor) driver;
        	//to scroll screen to appropriate location to avoid overlapping of elements
        	js.executeScript("scroll(0,"+currentElement.getLocation().y+(-350)+")");
        	currentElement.click();
        	String title = driver.getTitle();
        	log.debug("Title of page: "+title);
        	//checking correctness of redirected page
        	try{
        		if(linkName.equals("Sign in with GitHub")){
        			assertTrue(title.equals(PageTitles.GITHUB));
        		}
        		else if(linkName.equals("Sign in with Facebook")){
        			assertTrue(title.equals(PageTitles.FACEBOOK));
        		}
        		else if(linkName.equals("Sign in with Google")){
        			assertTrue(title.equals(PageTitles.GOOGLE));
        		}
        		else if(linkName.equals("Sign in with LinkedIn")){
        			assertTrue(title.equals(PageTitles.LINKEDIN));
        		}
        		else if(linkName.equals("Sign in with Twitter")){
        			assertTrue(title.equals(PageTitles.TWITTER));
        		}
        		else if(linkName.equals("Or sign up using your email address here.")){
        			assertTrue(title.equals(PageTitles.SIGNUP));
        		}
        		else if(linkName.equals("If you originally signed up using your email address, you can sign in here.")){
        			assertTrue(title.equals(PageTitles.SIGNIN));
        		}
        	}catch(AssertionError e){
        		log.error("Navigation failure with : "+ linkName);
        	}
        	driver.navigate().back();
        }
    }  
	
	@After
    public void closureActivities(){
        /*driver.quit();*/
    }
	
	private class PageTitles{
		public static final String GITHUB = "Sign in to GitHub · GitHub";
		public static final String FACEBOOK = "Log into Facebook | Facebook";
		public static final String GOOGLE = "Sign in - Google Accounts";
		public static final String LINKEDIN = "Authorize | LinkedIn";
		public static final String TWITTER = "Twitter / Authorize an application";
		public static final String SIGNUP = "Sign up for Free Code Camp using your Email Address | Free Code Camp";
		public static final String SIGNIN = "Sign in to Free Code Camp using your Email Address | Free Code Camp";
	}

}
