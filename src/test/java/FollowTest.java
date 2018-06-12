import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

public class FollowTest {
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
    public void testFollowChrome() throws Exception {
        follow(new ChromeDriver(optionsChrome));
    }

    @Test
    public void testFollowFirefox() throws Exception {
        follow(new FirefoxDriver(optionsFirefox));
    }

    private void follow(WebDriver driver) throws Exception {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        fnct.login(driver, "prokopiy.nikolaev@yandex.ru", "Qwerty");
        sleep(1000);

        driver.findElement(By.xpath("//li[2]/a")).click();
        driver.findElement(By.xpath("//form/a[2]")).click();
        driver.findElement(By.xpath("//div[2]/div/div/div[4]/a")).click();
        driver.findElement(By.xpath("//form/a[3]")).click();
        driver.findElement(By.xpath("//div/div/div[4]/a[2]")).click();


        Actions action = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//*[@id='header-avatar']"));
        action.moveToElement(element).build().perform();

        driver.findElement(By.xpath("(//a[contains(text(),'Сообщества')])[3]")).click();
        driver.findElement(By.xpath("//div[@id='main-content']/div[2]/h3")).click();

        assertEquals(true, driver.findElement(By.xpath("//div[@class='user-list clearfix']")).isDisplayed());

        fnct.logout(driver);
        driver.quit();
    }
}
