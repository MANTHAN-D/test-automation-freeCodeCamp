package signInUpPage;

import static org.junit.Assert.assertTrue;







import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.pagefactory.ByChained;

/**
 * @author Manthan
 *
 */
public class EmailSignIn {

	private WebDriver driver;
	private String baseUrl;
	private Properties testData;
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(EmailSignIn.class);

    @Before
    public void setUp()throws IOException{
    	testData = new Properties();
        testData.load(new FileInputStream("testdata/signin-credentials.properties"));
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        baseUrl = "https://www.freecodecamp.com/email-signin";        
        
    }
    
    @Test
    public void testValid(){
        
    	int noOfVTests = Integer.parseInt(testData.getProperty("noOfVTests"));
    	while(noOfVTests > 0){
    		driver.get(baseUrl);

    		driver.findElement(By.id("email")).sendKeys(testData.getProperty("eV"+noOfVTests));
			driver.findElement(By.id("password")).sendKeys(testData.getProperty("pV"+noOfVTests));
    		driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/form/button")).click();
    		String titlePage = driver.getTitle();
    		try{
    			assertTrue(titlePage.equals("Learn how Free Code Camp Works | Free Code Camp"));
    		}catch(AssertionError ae){
    			log.error("Error: Valid credentials not accepted in login..!!!!! test-case no "+ noOfVTests);
    		}
    		noOfVTests--;
    	}
    }
    
    @Test
    public void testInValid(){
        
    	int noOfIVTests = Integer.parseInt(testData.getProperty("noOfIVTests"));
    	while(noOfIVTests > 0){
    		driver.get(baseUrl);

    		driver.findElement(By.id("email")).sendKeys(testData.getProperty("eIV"+noOfIVTests));
			driver.findElement(By.id("password")).sendKeys(testData.getProperty("pIV"+noOfIVTests));
    		driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/form/button")).click();    

    		WebElement existingUserAlert = driver.findElement(By.className("alert"));        
    		try{
    			assertTrue(existingUserAlert.getText().contains("Invalid username or password."));
    			driver.findElement(new ByChained(By.className("alert"),By.className("close"))).click();
    		}catch(AssertionError ae){
    			log.error("Error: Invalid credentials accepted in login..!!!!!test-case no "+ noOfIVTests);
    		}
    		noOfIVTests--;
    	}
    }       
       
    @Test
    public void testForgotPasswordLink(){
        driver.get(baseUrl);
        List<WebElement> linkList = driver.findElements(By.tagName("a"));
        
        for(WebElement linkElement : linkList){
        	String linkName = linkElement.getText(); 
        	if(linkName.length() > 0 && linkName.equals("Forgot your password?")){
        		System.out.println("Link being checked : "+linkName);
        		JavascriptExecutor js = (JavascriptExecutor) driver;
            	//to scroll screen to appropriate location to avoid overlapping of elements
            	js.executeScript("scroll(0,"+linkElement.getLocation().y+(-350)+")");
            	linkElement.click();
            	String titlePage = driver.getTitle();
            	log.debug("Title of page: "+titlePage);            
                try{
                	assertTrue(titlePage.equals("Forgot Password | Free Code Camp"));
                }catch(AssertionError ae){
                	log.error("Error : Forgot password link not working!!!!!");
                }
                break;
        	}        		
        }                              
    }
	
	
	@After
    public void closureActivities(){
        driver.quit();
    }
}
