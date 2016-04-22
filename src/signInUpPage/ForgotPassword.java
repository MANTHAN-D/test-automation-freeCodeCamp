/**
 * 
 */
package signInUpPage;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
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
public class ForgotPassword {

	private WebDriver driver;
	private String baseUrl;	
	private Properties testData;
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ForgotPassword.class);

    @Before
    public void setUp() throws IOException{
        testData = new Properties();
        testData.load(new FileInputStream("testdata/forgot-password.properties"));        
    	driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        baseUrl = "https://www.freecodecamp.com/forgot";        
        
    }
    
    @Test
    public void testValidEmail(){
        
    	int noOfVTests = Integer.parseInt(testData.getProperty("noOfVTests"));
    	while(noOfVTests > 0){
    		driver.get(baseUrl);

    		driver.findElement(By.id("email")).sendKeys(testData.getProperty("eV"+noOfVTests));        
    		driver.findElement(By.xpath("/html/body/div[4]/div/form/div[2]/button")).click();
    		WebElement existingUserAlert = driver.findElement(By.className("alert"));        
    		try{
    			assertTrue(existingUserAlert.getText().contains("e-mail has been sent to "));
    			driver.findElement(new ByChained(By.className("alert"),By.className("close"))).click();
    		}catch(AssertionError ae){
    			log.error("Error: Valid email not being accepted in Forgot password..!!!!! test-case no:" + noOfVTests);
    		}
    		noOfVTests--;
    	}
    }
    
    @Test
    public void testInValidEmail(){
        
    	int noOfIVTests = Integer.parseInt(testData.getProperty("noOfIVTests"));
    	while(noOfIVTests > 0){
    		driver.get(baseUrl);

    		driver.findElement(By.id("email")).sendKeys(testData.getProperty("eIV"+noOfIVTests));
    		driver.findElement(By.xpath("/html/body/div[4]/div/form/div[2]/button")).click();                 
    		WebElement existingUserAlert = driver.findElement(By.className("alert"));        
    		try{
    			assertTrue(existingUserAlert.getText().contains("Email not found"));
    			driver.findElement(new ByChained(By.className("alert"),By.className("close"))).click();
    		}catch(AssertionError ae){
    			log.error("Error: Invalid email accepted in Forgot password..!!!!!test-case no:" + noOfIVTests);
    		}
    		noOfIVTests--;
    	}
    }          	
	
	@After
    public void closureActivities(){
        driver.quit();
    }
}
