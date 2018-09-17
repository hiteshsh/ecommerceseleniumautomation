package pages;

import Model.PriceBreakup;
import Model.Product;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

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
        String expectedQuantity=driver.findElement(By.id("summary_products_quantity")).getText();
        if(quantity>1)
         Assert.assertEquals(expectedQuantity,quantity+" Products");
        else
         Assert.assertEquals(expectedQuantity,quantity+" Product");

         return this;
    }

    public Cart shouldDisplayPriceBreakUp(List<Product> products){
        PriceBreakup priceBreakup= calculatePrice(products);
        double totalWithShipping= priceBreakup.getShipping()+priceBreakup.getTotal();
        double total=priceBreakup.getTax()+totalWithShipping;
        Assert.assertEquals(driver.findElement(By.id("total_product")).getText().replace("$","")
                ,String.format("%.2f",priceBreakup.getTotal()));
        Assert.assertEquals(driver.findElement(By.id("total_shipping")).getText().replace("$","")
                ,String.format("%.2f",priceBreakup.getShipping()));
        Assert.assertEquals(driver.findElement(By.id("total_price_without_tax")).getText().replace("$","")
                ,String.format("%.2f",totalWithShipping));
        Assert.assertEquals(driver.findElement(By.id("total_tax")).getText().replace("$","")
                ,String.format("%.2f",priceBreakup.getTax()));
        Assert.assertEquals(driver.findElement(By.id("total_price_container")).getText().replace("$","")
                ,String.format("%.2f",total));

        return this;

    }
    private PriceBreakup calculatePrice(List<Product> products){
       DecimalFormat df2 = new DecimalFormat(".##");
        PriceBreakup priceBreakup= new PriceBreakup();
        double totalPrice=0;
        for (Product product: products) {
            totalPrice=totalPrice+(product.getQuantity()*product.getUnitPrice());
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
        //Assert.assertEquals(driver.findElement(By.id("layer_cart_product_quantity")).getText(),String.valueOf(product.getQuantity()));

        return this;
    }

    public Cart addQuantityForProduct(String productName,int quantity) {
        WebElement productRow=null;
        WebElement cartSummary=driver.findElement(By.id("cart_summary")).findElement(By.tagName("tbody"));
        List<WebElement> listOfProductsElement=cartSummary.findElements(By.tagName("tr"));
        for (WebElement element:listOfProductsElement){
            if (element.getText().contains(productName)){
                productRow=element;
                break;
            }
        }

        WebElement quantityEl=productRow.findElement(By.cssSelector("td[class='cart_quantity text-center']>input"));
        Integer currentQuantity= Integer.valueOf(quantityEl.getAttribute("value"));
        WebElement add=productRow.findElement(By.cssSelector("a[title='Add']"));
        wait.until(ExpectedConditions.elementToBeClickable(add));

        for(int i=0;i<quantity;i++){
            add.click();
            wait.until(ExpectedConditions.attributeToBe(quantityEl,"value",String.valueOf(currentQuantity+1)));
            currentQuantity=currentQuantity+1;
        }

        return this;
    }

    public Cart removeProductFromCart(String productName) {
        List<WebElement> productList=driver.findElements(By.cssSelector("#cart_summary tbody tr"));
        WebElement productRow=findProductByName(productName);
        WebElement delete=productRow.findElement(By.className("cart_quantity_delete"));
        wait.until(ExpectedConditions.elementToBeClickable(delete)).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("#cart_summary tbody tr"),productList.size()-1));
        return this;

    }

    private WebElement findProductByName(String productName){
        WebElement productRow=null;
        if (isCartEmpty()){
            return productRow;
        }
        List<WebElement> listOfProductsElement=driver.findElements(By.cssSelector("#cart_summary tbody tr"));
        for (WebElement element:listOfProductsElement){
            if (element.getText().contains(productName)){
                productRow=element;
                break;
            }
        }
        return productRow;

    }

    public void productShouldNotBeAvailableInCart(String productName) {
        Assert.assertNull(findProductByName(productName));
    }

    private boolean isCartEmpty(){
         List<WebElement> cartsize=driver.findElements(By.cssSelector("#center_column table"));
         if (cartsize!=null) {
             return !(cartsize.size() > 0);
         }
         else
             return true;
    }

    public Cart removeProductFromCartBlock(String productName) {

        List<WebElement> productList=driver.findElements(By.cssSelector("dl.products>dt"));
        System.out.println("size: "+productList.size());

        for (WebElement productrow:productList
             ) {
            String prodtitle=productrow.findElement(By.cssSelector(".cart-info a.cart_block_product_name")).getAttribute("title");
            if (prodtitle.equalsIgnoreCase(productName)){
                productrow.findElement(By.cssSelector("span>a")).click();
                break;
            }
        }

        List<WebElement> productList1=driver.findElements(By.cssSelector("dl.products>dt"));
        System.out.println("size: "+productList1.size());
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("dl.products>dt"),productList.size()-1));
        return this;


    }

    public Cart viewCartBlock() {

        WebElement productBlock=driver.findElement(By.xpath("//a[contains(@title,'View my shopping cart')]"));
        Util.scrollToElement(productBlock,driver);
        Actions builder= new Actions(driver);
        builder.moveToElement(productBlock).build().perform();
        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.cssSelector("dl.products>dt"))));
        return this;
    }

    public void productShouldNotBeInCartBlock(String productname) {
        Assert.assertNull(findProductByNameInCartBlock(productname));

    }

    public WebElement findProductByNameInCartBlock(String productName){
        WebElement element=null;
        List<WebElement> productList=driver.findElements(By.cssSelector("dl.products>dt"));
        if (productList!=null&& productList.size()>0) {
            for (WebElement productrow:
                 productList) {
                String prodtitle=productrow.findElement(By.cssSelector(".cart-info a.cart_block_product_name")).getAttribute("title");
                if (prodtitle.equalsIgnoreCase(productName)){
                    element=productrow;
                    break;
                }
            }
        }
        return element;

    }

    public Cart productShouldBeInCartBlock(String productName) {
        Assert.assertNotNull(findProductByNameInCartBlock(productName));
        return this;
    }
}


