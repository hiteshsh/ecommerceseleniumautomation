package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by hiteshs on 3/8/18.
 */
public class DriverFactory {

    public WebDriver getDriver(String browser){
        if(browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver","./src/test/resources/webdrivers/chromedriver");
            return new ChromeDriver();
        }
        else if (browser.equalsIgnoreCase("chrome"))
            return new FirefoxDriver();
        else
            return null;
    }
}
