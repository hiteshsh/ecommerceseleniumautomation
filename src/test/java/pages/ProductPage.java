package pages;

import Model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import utils.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hiteshs on 8/11/18.
 */
public class ProductPage {

    public WebDriver driver;

    public ProductPage(WebDriver driver){
        this.driver=driver;
    }

    public void addProductToCart(String productName){
        WebElement productList= driver.findElement(By.className("product_list"));
        Util.scrollToElement(productList,driver);
        WebElement product= driver.findElement(By.xpath("//a[contains(text(),'"+productName+"')]"));
        Actions builder= new Actions(driver);
        builder.moveToElement(product).build().perform();
        driver.findElement(By.xpath("//span[contains(text(),'Add to cart')]")).click();

    }
}
