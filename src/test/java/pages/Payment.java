package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Created by hiteshs on 9/13/18.
 */
public class Payment {
    private WebDriver driver;
    private WebDriverWait wait;

    public Payment(WebDriver driver){
        this.driver=driver;
        wait= new WebDriverWait(this.driver,10);
    }

    public Payment payByCheck(){
        driver.findElement(By.className("cheque")).click();
        return this;
    }

    public Payment confirmOrder(){
        driver.findElement(By.cssSelector("button.button-medium")).click();
        return this;
    }

    public void shouldDisplayOrderSuccessMessage() {
        Assert.assertEquals(driver.findElement(By.className("alert-success")).getText(),"Your order on My Store is complete.");
    }
}
