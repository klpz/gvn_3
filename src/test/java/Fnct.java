import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.NoSuchElementException;

import static java.lang.Thread.sleep;
import static org.junit.Assert.fail;

public class Fnct {
    public boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void waitForVisible(WebDriver driver, String xpath) throws Exception {
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (driver.findElement(By.xpath(xpath)).isDisplayed()) break; } catch (Exception e) {}
            sleep(1000);
        }
    }

    public void login(WebDriver driver, String username, String password) throws Exception {
        driver.findElement(By.xpath("//a[contains(@href, '/user/login/')]")).click();
        String parentHandle = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[@onclick='hideWindow(); return doYandexLogin();']")).click();

        Thread.sleep(1000);
        for(String childHandle : driver.getWindowHandles()){
            if (!childHandle.equals(parentHandle)){
                driver.switchTo().window(childHandle);
                break;
            }
        }
        waitForVisible(driver,"//input[@name='login']");
        driver.findElement(By.xpath("//input[@name='login']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@name='passwd']")).sendKeys(password);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@class='passport-Button']")).click();
        sleep(1000);

        try{
            driver.findElement(By.xpath("//button[@class='button2 button2_size_l button2_theme_normal button2_width_max']")).click();
        } catch (Exception e) {

        }

        /*if(driver.findElement(By.xpath("//button[@class='button2 button2_size_l button2_theme_normal button2_width_max']")) != null){
            driver.findElement(By.xpath("//button[@class='button2 button2_size_l button2_theme_normal button2_width_max']")).click();
        }*/
        sleep(1000);
        /*if(!isElementPresent(driver, By.className("//div[@class='passport-Domik-Form-Error passport-Domik-Form-Error_active']"))){
            driver.switchTo().window(parentHandle);
        }*/
        try {
            driver.findElement(By.xpath("//div[@class='passport-Domik-Form-Error passport-Domik-Form-Error_active']"));
        }
        catch (Exception e) {
            driver.switchTo().window(parentHandle);
        }
        //driver.close();
    }

    public void logout(WebDriver driver) throws Exception {
        //waitForVisible(driver, "//a[@href='/user/logout/']");
        Actions action = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//*[@id='header-avatar']"));

        action.moveToElement(element).build().perform();
        //sleep(1000);
        //driver.findElement(By.xpath("(//a[@class='spr icon icon__avatar'])")).click();
        //Select select = new Select(driver.findElement(By.xpath("//a[@class='spr icon icon__avatar']")));
        sleep(1000);
        driver.findElement(By.linkText("Выход")).click();
        //sleep(1000);
        //select.deselectAll();
        driver.navigate().refresh();
        sleep(1000);
        //driver.close();
    }
}
