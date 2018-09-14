package pages;

import Model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Util;

/**
 * Created by hiteshs on 9/13/18.
 */
public class Shipping {

    private WebDriver driver;
    private WebDriverWait wait;

    public Shipping(WebDriver driver){
        this.driver=driver;
        wait= new WebDriverWait(this.driver,10);
    }

    public Shipping shouldDisplayCarrierCharges(){
        String shippingCost=driver.findElement(By.className("delivery_option_price")).getText();
        Assert.assertEquals(shippingCost,"$2.00");
        return this;
    }

    public Shipping shouldDisplayShippingAddress(User user){
        WebElement deliveryAddress= driver.findElement(By.id("address_delivery"));
        Assert.assertEquals(deliveryAddress.findElement(By.className("address_title")).getText(),"YOUR DELIVERY ADDRESS");
        Assert.assertEquals(deliveryAddress.findElement(By.className("address_firstname")).getText(),user.getUsername());
        Assert.assertEquals(deliveryAddress.findElement(By.className("address_address1")).getText(),user.getAddress());
        Assert.assertEquals(deliveryAddress.findElement(By.className("address_city")).getText(),user.getAddressState());
        Assert.assertEquals(deliveryAddress.findElement(By.className("address_country_name")).getText(),user.getCountry());
        Assert.assertEquals(deliveryAddress.findElement(By.className("address_phone_mobile")).getText(),user.getPhoneNumber());
        return this;
    }

    public Shipping addComments(){
        driver.findElement(By.tagName("textarea")).sendKeys("Please deliver on coming weekend");
        return this;
    }
    public Payment proceedToPayment(){
        WebElement checkoutLink= driver.findElement(By.cssSelector("button.button-medium"));
        Util.scrollToElement(checkoutLink,driver);
        checkoutLink.click();
        return new Payment(driver);
    }


    public Shipping agreeTermsAndCondtion(){
        driver.findElement(By.cssSelector("input[type='checkbox']")).click();
        return this;
    }

    public Shipping proceedToShipping() {
        WebElement checkoutLink= driver.findElement(By.cssSelector("button.button-medium"));
        Util.scrollToElement(checkoutLink,driver);
        checkoutLink.click();
        return this;
    }
}
