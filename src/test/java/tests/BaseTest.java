package tests;

import factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by hiteshs on 3/8/18.
 */
public class BaseTest {

    protected WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setup(String browser){
        System.out.println("*****"+browser);
        driver=new DriverFactory().getDriver(browser);
        System.out.println("**driver**"+driver.toString());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("http://automationpractice.com/");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(){
        System.out.println("*****Closing browser*****");
        driver.manage().deleteAllCookies();
        driver.quit();

    }
}
