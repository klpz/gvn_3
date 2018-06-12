import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class FavoriteTest {
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
    public void testFavoriteChrome() throws Exception {
        favorite(new ChromeDriver(optionsChrome));
    }

    @Test
    public void testFavoriteFirefox() throws Exception {
        favorite(new FirefoxDriver(optionsFirefox));
    }

    @Test
    public void testWithoutLoginChrome() throws Exception{
        favoriteWithoutLogin(new ChromeDriver(optionsChrome));
    }

    @Test
    public void testWithoutLoginFirefox() throws Exception{
        favoriteWithoutLogin(new FirefoxDriver(optionsFirefox));
    }

    private void favorite(WebDriver driver) throws Exception {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        fnct.login(driver, "prokopiy.nikolaev@yandex.ru", "Qwerty");
        sleep(1000);
        JavascriptExecutor jse = (JavascriptExecutor)driver;

        driver.findElement(By.xpath("//div[2]/div/div/div/a/span")).click();
        //jse.executeScript("scroll(0, 1000);");
        sleep(1000);
        driver.findElement(By.xpath("//*[@class='icon__60__text counter-fav']")).click();


        jse.executeScript("scroll(0, -250);");

        Actions action = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//*[@id='header-avatar']"));

        action.moveToElement(element).build().perform();
        driver.findElement(By.xpath("//*[@href='/profile/favorites/1569840']")).click();

        //fnct.waitForVisible(driver, "//div[@id='gallery_object_5632321']/div[2]/div/div/div/div/div[2]/a/div");
        ///html/body/div[8]/div[1]/div[7]/div[1]/div[1]/div[1]/div[1]/div/div[2]/div/div/div/div/div[2]/a[1]/div
        driver.findElement(By.xpath("//*[@class='popup-meta__text counter-fav']")).click();

        fnct.logout(driver);
        driver.quit();
    }

    private void favoriteWithoutLogin(WebDriver driver)throws Exception{
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        
    }
}
