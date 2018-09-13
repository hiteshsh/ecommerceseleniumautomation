package pages;

import Model.PriceBreakup;
import Model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hiteshs on 4/10/18.
 */
public class Cart {

    private WebDriver driver;
    private WebDriverWait wait;

    public Cart(WebDriver driver){
        wait= new WebDriverWait(driver,20);
        this.driver= driver;
    }

    public List<Product> getAllProductsDetailsFromCartSummary(){
        WebElement cartSummary=driver.findElement(By.id("cart_summary")).findElement(By.tagName("tbody"));
        List<WebElement> listOfProductsElement=cartSummary.findElements(By.tagName("tr"));
        List<Product> productList= new ArrayList<Product>();
        for (WebElement productInCart: listOfProductsElement) {
            Product product= new Product();
            product.setProductDesc(productInCart.findElement(By.className("cart_description")).findElement(By.tagName("a")).getText());
            if(productInCart.findElement(By.className("cart_avail")).getText().equalsIgnoreCase("In stock"))
                product.setInStock(true);
            else
                product.setInStock(false);
            product.setUnitPrice(Double.valueOf(productInCart.findElement(By.className("cart_unit"))
                    .findElement(By.tagName("span")).getText().replace("$","")));
            product.setQuantity(Integer.valueOf(productInCart.findElement(By.className("cart_quantity"))
                    .findElement(By.cssSelector("input[type='hidden']")).getAttribute("value")));

            productList.add(product);
        }

        return productList;
    }

    public Cart shouldDisplayTotalProduct(int quantity){
         Assert.assertEquals(quantity+" Product",driver.findElement(By.id("summary_products_quantity")).getText());
         return this;
    }

    public Cart shouldDisplayPriceBreakUp(List<Product> products){
        PriceBreakup priceBreakup= calculatePrice(products);
        double totalWithShipping= priceBreakup.getShipping()+priceBreakup.getTotal();
        double total=priceBreakup.getTax()+totalWithShipping;
        Assert.assertEquals(Double.valueOf(driver.findElement(By.id("total_product")).getText().replace("$",""))
                ,priceBreakup.getTotal());
        Assert.assertEquals(Double.valueOf(driver.findElement(By.id("total_shipping")).getText().replace("$",""))
                ,priceBreakup.getShipping());
        Assert.assertEquals(Double.valueOf(driver.findElement(By.id("total_price_without_tax")).getText().replace("$",""))
                ,totalWithShipping);
        Assert.assertEquals(Double.valueOf(driver.findElement(By.id("total_tax")).getText().replace("$",""))
                ,priceBreakup.getTax());
        Assert.assertEquals(Double.valueOf(driver.findElement(By.id("total_price_container")).getText().replace("$",""))
                ,total);

        return this;

    }
    private PriceBreakup calculatePrice(List<Product> products){
        PriceBreakup priceBreakup= new PriceBreakup();
        double totalPrice=0;
        for (Product product: products) {
            totalPrice=totalPrice+product.getQuantity()*product.getUnitPrice();
        }
        priceBreakup.setTotal(totalPrice);
        priceBreakup.setShipping(2);
        priceBreakup.setTax(0);
        return priceBreakup;
    }


    public Cart shouldDisplayAllProduct(List<Product> expectedProductList){
        List<Product> actualProductList=getAllProductsDetailsFromCartSummary();
        Iterator<Product> iteratorActual=actualProductList.iterator();
        Iterator<Product> iteratorExpected=expectedProductList.iterator();
        while(iteratorActual.hasNext() && iteratorExpected.hasNext()) {
            Product actual=iteratorActual.next();
            Product expected=iteratorExpected.next();
            Assert.assertEquals(actual.getProductDesc(),expected.getProductDesc());
            Assert.assertEquals(actual.getUnitPrice(),expected.getUnitPrice());
        }
        return this;
    }

    public Cart checkoutProducts(){

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@title,'Proceed to checkout')]"))).click();
        return this;
    }

    public void confirmCheckout(){

        WebElement checkoutLink= driver.findElement(By.cssSelector("a.button.standard-checkout"));
        Util.scrollToElement(checkoutLink,driver);
        checkoutLink.click();
    }

    public Products continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@title,'Continue shopping')]"))).click();
        return new Products(driver);
    }

    public Cart shouldDisplayProductSuccesfullyAddedMessage(){
        String message=wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.className("layer_cart_product>h2")))).getText();
        Assert.assertEquals(message,"Product successfully added to your shopping cart");
        return this;
    }

    public Cart shouldDisplayAddedProductDetails(Product product) {
        Assert.assertEquals(driver.findElement(By.id("layer_cart_product_title")).getText(),product.getProductDesc());
        Assert.assertEquals(driver.findElement(By.id("layer_cart_product_quantity")).getText(),String.valueOf(product.getQuantity()));

        return this;
    }
}
