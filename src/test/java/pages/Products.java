package pages;

import Model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hiteshs on 8/11/18.
 */
public class Products {

    private WebDriver driver;
    private WebDriverWait wait;


    public Products(WebDriver driver){
        this.driver=driver;
        wait= new WebDriverWait(this.driver,10);
    }

    public Cart addProductToCart(String productName, String productId){

        WebElement productList= driver.findElement(By.className("product_list"));
        Util.scrollToElement(productList,driver);
        WebElement product= driver.findElement(By.xpath("//a[contains(@title,'"+productName+"')]/parent::div/parent::div"));
        //wait.until(ExpectedConditions.visibilityOf(product));
        Actions builder= new Actions(driver);
        builder.moveToElement(product).build().perform();
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Add to cart')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-id-product='"+productId+"']"))).click();
        return new Cart(driver);

    }


}
