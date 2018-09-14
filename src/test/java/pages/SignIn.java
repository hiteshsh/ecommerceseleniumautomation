package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by hiteshs on 9/13/18.
 */
public class SignIn {

    private WebDriver driver;
    private WebDriverWait wait;

    public SignIn(WebDriver driver){
        this.driver=driver;
        wait= new WebDriverWait(this.driver,10);
    }

    public void SignIn(String email,String password){
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("email")))).sendKeys(email);
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.id("SubmitLogin")).click();
    }
}
