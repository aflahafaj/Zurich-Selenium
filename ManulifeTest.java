import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ManulifeRpaApplicationTests {

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver", "D:/[FAJRINA]/[ECLIPSE]/geckodriver-v0.33.0-win64/geckodriver.exe");
		
		FirefoxOptions options = new FirefoxOptions();
		WebDriver driver = new FirefoxDriver(options);
		
		String url = "https://acs3-qa.taspenlife.com/AcsGroup_Uat/";
		driver.get(url);
		
		//username field
		driver.findElement(By.name("username")).sendKeys("test");

        //password field
        driver.findElement(By.name("password")).sendKeys("");
        
        //click login button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        driver.findElement(By.name("login")).click();
        
        //go to POS menu
        WebElement posMenu = driver.findElement(By.linkText("POS"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        posMenu.click();
        driver.findElement(By.linkText("Member Alteration List")).click();
        
        //search member
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        driver.findElement(By.id("S_MemberNames")).sendKeys("amir hamzah");
        driver.findElement(By.id("S_MemberInstID")).sendKeys("06000086800");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        driver.findElement(By.cssSelector(".col-md-4 > .btn-primary")).click();
        
        //view detail member
        WebElement detailMember = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".odd .glyph-icon")));
        detailMember.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		
		System.out.println("Opening the website...");
		
		driver.quit();
	}

}
