import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

public class LoginTest {
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
    public void testLoginSuccessChrome() throws Exception {
        loginSuccess(new ChromeDriver(optionsChrome));
    }

    @Test
    public void testLoginFailureChrome() throws Exception {
        loginFailure(new ChromeDriver(optionsChrome));
    }

    @Test
    public void testLoginSuccessFirefox() throws Exception {
        loginSuccess(new FirefoxDriver(optionsFirefox));
    }

    @Test
    public void testLoginFailureFirefox() throws Exception {
        loginFailure(new FirefoxDriver(optionsFirefox));
    }

    private void loginSuccess(WebDriver driver) throws Exception {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        fnct.login(driver, "prokopiy.nikolaev@yandex.ru", "Qwerty");
        sleep(1000);
        //driver.navigate().refresh();
        //sleep(2000);
        fnct.logout(driver);
        driver.quit();
    }

    private void loginFailure(WebDriver driver) throws Exception {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        fnct.login(driver, "prokopiy.nikolaev@yandex.ru", "kekkek");
        sleep(2000);
        assertEquals(true, driver.findElement(By.xpath("//div[@class='passport-Domik-Form-Error passport-Domik-Form-Error_active']")).isDisplayed());
        driver.quit();
    }
}
