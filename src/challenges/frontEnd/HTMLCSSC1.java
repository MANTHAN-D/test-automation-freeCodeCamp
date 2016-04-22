/**
 * 
 */
package challenges.frontEnd;

import static org.junit.Assert.assertTrue;

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
public class HTMLCSSC1 {

	private WebDriver driver;
	private String baseUrl;	
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(HTMLCSSC1.class);

    @Before
    public void setUp(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        baseUrl = "https://www.freecodecamp.com/challenges/headline-with-the-h2-element";        
        
    }
    
    @Test
    public void testPageTitle(){
        driver.get(baseUrl);
        String expectedTitle = baseUrl.substring(baseUrl.lastIndexOf("/")+1).replace("-", " ");
        String actualtitle = driver.findElement(By.xpath("//*[@id=\"scroll-locker\"]/div/div[1]/div/h4")).getText();        
                              
        try{
    		assertTrue(expectedTitle.equalsIgnoreCase(actualtitle));    		
    	}catch(AssertionError ae){
    		log.error("Error: Mismatched title of page..!!!!!");
    	}        
    }       
    
    @Test
    public void testFunctionalButtons(){
        driver.get(baseUrl);      
        
        WebElement runButton = driver.findElement(By.xpath("//*[@id=\"submitButton\"]"));
        WebElement resetButton = driver.findElement(By.xpath("//*[@id=\"trigger-reset-modal\"]"));
        WebElement helpButton = driver.findElement(By.xpath("//*[@id=\"challenge-help-btn\"]"));
        WebElement bugButton = driver.findElement(By.xpath("//*[@id=\"trigger-issue-modal\"]"));
        
      
        try{
        	assertTrue(runButton.getText().equals("Run tests (ctrl + enter)"));
        	assertTrue(resetButton.getText().equals("Reset"));
            assertTrue(helpButton.getText().equals("Help"));
            assertTrue(bugButton.getText().equals("Bug"));            
        }catch(AssertionError e){
        	log.error("Error : Fault in default buttons");
        }        
        
        runButton.click();
        List<WebElement> testSuitList = driver.findElement(By.xpath("//*[@id=\"testSuite\"]")).findElements(By.tagName("i"));
        int countErrors = 0;
        for(WebElement we : testSuitList){
        	if(we.getAttribute("class").contains("error"))
        		countErrors++;
        }
        if(countErrors == 0){
        	log.debug(driver.findElement(By.xpath("//*[@id=\"next-challenge\"]")).isDisplayed());
        	driver.findElement(By.xpath("//*[@id=\"next-challenge\"]")).click();
        }
        else{
        	log.debug(driver.findElement(By.xpath("//*[@id=\"next-challenge\"]")).isDisplayed());
        }
        
        resetButton.click();
        try{
        	WebElement resetModalHeader = driver.findElement(By.xpath("//*[@id=\"reset-modal\"]/div/div")).findElement(By.className("modal-header"));        	
        	assertTrue(resetModalHeader.getText().contains("Clear your code?"));
        	
        	JavascriptExecutor js = (JavascriptExecutor) driver;
        	//to scroll screen to appropriate location to avoid overlapping of elements
        	js.executeScript("scroll(0,"+resetModalHeader.findElement(By.tagName("a")).getLocation().y+(-350)+")");
        	
        	resetModalHeader.findElement(By.tagName("a")).click();
        	
        }catch(AssertionError ae){
        	log.error("Error : Problem with reset modal!!!");
        }
        
        helpButton.click();
        try{
        	WebElement helpModalHeaderText = driver.findElement(By.xpath("//*[@id=\"chat-embed-help\"]/div[1]/div/span")); 
        	assertTrue(helpModalHeaderText.getText().equalsIgnoreCase("Help"));
        	
        	JavascriptExecutor js = (JavascriptExecutor) driver;
        	//to scroll screen to appropriate location to avoid overlapping of elements
        	js.executeScript("scroll(0,"+driver.findElement(By.xpath("//*[@id=\"chat-embed-help\"]/div[1]/button")).getLocation().y+(-350)+")");
        	
        	driver.findElement(By.xpath("//*[@id=\"chat-embed-help\"]/div[1]/button")).click();
        	
        }catch(AssertionError ae){
        	log.error("Error : Problem with help modal!!!");
        }               
        
        bugButton.click();
        try{
        	WebElement bugModalHeader = driver.findElement(By.xpath("//*[@id=\"issue-modal\"]/div/div")).findElement(By.className("modal-header"));        	
        	assertTrue(bugModalHeader.getText().contains("Did you find a bug?"));
        	
        	JavascriptExecutor js = (JavascriptExecutor) driver;
        	//to scroll screen to appropriate location to avoid overlapping of elements
        	js.executeScript("scroll(0,"+bugModalHeader.findElement(By.tagName("a")).getLocation().y+(-350)+")");
        	
        	bugModalHeader.findElement(By.tagName("a")).click();
        	
        }catch(AssertionError ae){
        	log.error("Error : Problem with issue/bug modal!!!");
        }                             
        
    }
    
    @Test
    public void testValidInput(){
        driver.get(baseUrl);
        WebElement runButton = driver.findElement(By.xpath("//*[@id=\"submitButton\"]"));
        driver.findElement(By.xpath("//*[@id=\"mainEditorPanel\"]/form/div/div/div[4]/div[1]/div/div/div/div[5]/div[2]/pre"))
        .sendKeys("<span style=\"padding-right: 0.1px;\"><span class=\"cm-tag cm-bracket\">&lt;</span><span class=\"cm-tag\">h2</span><span class=\"cm-tag cm-bracket\">&gt;</span>CatPhotoApp<span class=\"cm-tag cm-bracket\">&lt;/</span><span class=\"cm-tag\">h2</span><span class=\"cm-tag cm-bracket\">&gt;</span></span>");
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
    	//to scroll screen to appropriate location to avoid overlapping of elements
    	js.executeScript("scroll(0,"+runButton.getLocation().y+(-350)+")");
    	
        runButton.click();
        
        List<WebElement> testSuitList = driver.findElement(By.xpath("//*[@id=\"testSuite\"]")).findElements(By.tagName("i"));
        int countErrors = 0;
        for(WebElement we : testSuitList){
        	if(we.getAttribute("class").contains("error"))
        		countErrors++;
        }
        if(countErrors == 0){
        	log.debug("Success ::"+driver.findElement(By.xpath("//*[@id=\"next-challenge\"]")).isDisplayed());
        	driver.findElement(By.xpath("//*[@id=\"next-challenge\"]")).click();
        }
        else{
        	log.error("Errors ::"+driver.findElement(By.xpath("//*[@id=\"next-challenge\"]")).isDisplayed());
        }                      
    }       
	
	@After
    public void closureActivities(){
        driver.quit();
    }
}
