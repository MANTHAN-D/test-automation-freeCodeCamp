package signInUpPage;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.pagefactory.ByChained;


/**
 * @author Manthan
 *
 */
public class EmailSignUp {

	private WebDriver driver;
	private String baseUrl;	
	private Properties testData;
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(EmailSignUp.class);

    @Before
    public void setUp() throws IOException{
    	testData = new Properties();
        testData.load(new FileInputStream("testdata/signup-credentials.properties"));
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        baseUrl = "https://www.freecodecamp.com/email-signup";        
        
    }
    
	@Test
    public void testValid(){
        
		int noOfVTests = Integer.parseInt(testData.getProperty("noOfVTests"));
		while(noOfVTests > 0){
			driver.get(baseUrl);

			driver.findElement(By.id("email")).sendKeys(testData.getProperty("eV"+noOfVTests));
			driver.findElement(By.id("password")).sendKeys(testData.getProperty("pV"+noOfVTests));
			driver.findElement(By.xpath("/html/body/div[4]/form/div/div/div[3]/button")).click();
			log.debug(driver.getTitle());        
			try{
				WebElement errorElement  = driver.findElement(By.cssSelector("input:invalid"));        
				assertTrue(errorElement.isDisplayed());
			}catch(NoSuchElementException e){
				WebElement welcomeAlert = driver.findElement(By.className("alert"));
				try{
					assertTrue(welcomeAlert.getText().contains("Welcome to Free Code Camp! We've created your account."));
					driver.findElement(new ByChained(By.className("alert"),By.className("close"))).click();
				}catch(AssertionError ae){
					log.error("Error: Something went wrong with account creation..!! test-case no:"+noOfVTests);
				}
			}
			catch(AssertionError e){
				log.error("Error: Valid credentials not accepted on signup page..!!!!!test-case no:"+noOfVTests);
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
			driver.findElement(By.xpath("/html/body/div[4]/form/div/div/div[3]/button")).click();			

			WebElement errorElement  = driver.findElement(By.cssSelector("input:invalid"));       
			try{
				assertTrue(errorElement.isDisplayed());
			}catch(AssertionError e){
				log.error("Error:Invalid credentials accepted on signup page..!!!!! testcase no: " + noOfIVTests);
			}
			noOfIVTests--;
		}
                
    }	
		
	@After
    public void closureActivities(){
        driver.quit();
    }
}
