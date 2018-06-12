import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ResearchTest {
    private String baseUrl;
    private Fnct fnct;
    private ChromeOptions optionsChrome;
    private FirefoxProfile optionsFirefox;

    @Before
    public void setUp() throws Exception {
        fnct = new Fnct();
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        baseUrl = "https://fishki.net";
        optionsChrome = new ChromeOptions();
        optionsChrome.addArguments("--disable-notifications");
        optionsFirefox = new FirefoxProfile();
        optionsFirefox.setPreference("dom.webnotifications.enabled", false);
    }

    @Test
    public void testSearchSuccessChrome() throws Exception {
        searchSuccess(new ChromeDriver(optionsChrome));
    }

    @Test
    public void testSearchFailureChrome() throws Exception {
        searchFailure(new ChromeDriver(optionsChrome));
    }

    @Test
    public void testSearchSuccessFirefox() throws Exception {
        searchSuccess(new FirefoxDriver(optionsFirefox));
    }

    @Test
    public void testSearchFailureFirefox() throws Exception {
        searchFailure(new FirefoxDriver(optionsFirefox));
    }

    private void searchSuccess(WebDriver driver) throws Exception {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        driver.findElement(By.xpath("html/body/header/div/nav/ul/li/a[@class='spr header-settings__link header-settings__link_to search']")).click();
        driver.findElement(By.xpath("//input[@id='qry']")).clear();
        driver.findElement(By.xpath("//input[@id='qry']")).sendKeys("секс");
        driver.findElement(By.xpath("//button[@class='btn-green btn-green--bdtl']")).click();

        driver.findElement(By.xpath("/html/body/div[6]/div[1]/a")).click();
        driver.quit();
    }

    private void searchFailure(WebDriver driver) throws Exception {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        fnct.waitForVisible(driver, "//a[@class='spr header-settings__link header-settings__link_to search']");
        driver.findElement(By.xpath("//a[@class='spr header-settings__link header-settings__link_to search']")).click();
        driver.findElement(By.xpath("//button[@class='btn-green btn-green--bdtl']")).click();
        assertEquals(true, driver.findElement(By.xpath("//div[@class='jGrowl-message']")).isDisplayed());
        driver.quit();
    }
}
