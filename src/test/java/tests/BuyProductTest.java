package tests;

import Model.Product;
import org.testng.annotations.Test;
import pages.Cart;
import pages.NavigationMenu;
import pages.Products;

import java.util.ArrayList;
import java.util.List;

import static Model.Category.WOMEN;

/**
 * Created by hiteshs on 3/8/18.
 */
public class BuyProductTest extends BaseTest {

    //@Test
    public void buyAProductFromWomenCategory()  {
        List<Product> productList= new ArrayList<Product>();
        Product product= new Product();
        product.setProductDesc("Faded Short Sleeve T-shirts");
        product.setUnitPrice(16.51);
        product.setQuantity(1);
        productList.add(product);

        NavigationMenu menu= new NavigationMenu(driver);

        menu.navigateToCategory(WOMEN)
                .addProductToCart("Faded Short Sleeve T-shirts","1")
                .shouldDisplayProductSuccesfullyAddedMessage()
                .shouldDisplayAddedProductDetails(product)
                .checkoutProducts()
                .shouldDisplayTotalProduct(1)
                .shouldDisplayAllProduct(productList)
                .shouldDisplayPriceBreakUp(productList)
                .confirmCheckout();

    }

    //@Test
    public void buyMultipleProductFromWomenCategory(){
        List<Product> productList= new ArrayList<Product>();
        Product product= new Product();
        product.setProductDesc("Faded Short Sleeve T-shirts");
        product.setUnitPrice(16.51);
        product.setQuantity(1);
        productList.add(product);

        Product product1= new Product();
        product1.setProductDesc("Blouse");
        product1.setUnitPrice(27.00);
        product1.setQuantity(1);
        productList.add(product1);

        NavigationMenu menu= new NavigationMenu(driver);

        menu.navigateToCategory(WOMEN)
                .addProductToCart("Faded Short Sleeve T-shirts","1")
                .shouldDisplayProductSuccesfullyAddedMessage()
                .continueShopping()
                .addProductToCart("Blouse","2")
                .shouldDisplayProductSuccesfullyAddedMessage()
                .checkoutProducts()
                .shouldDisplayTotalProduct(2)
                .shouldDisplayAllProduct(productList)
                .shouldDisplayPriceBreakUp(productList)
                .confirmCheckout();

    }

    @Test
    public void buyAProductWithMoreQuantity(){

        List<Product> productList= new ArrayList<Product>();
        Product product= new Product();
        product.setProductDesc("Faded Short Sleeve T-shirts");
        product.setUnitPrice(16.51);
        product.setQuantity(3);
        productList.add(product);

        NavigationMenu menu= new NavigationMenu(driver);

        menu.navigateToCategory(WOMEN)
                .addProductToCart("Faded Short Sleeve T-shirts","1")
                .shouldDisplayProductSuccesfullyAddedMessage()
                .shouldDisplayAddedProductDetails(product)
                .checkoutProducts()
                .addQuantity(2,"Faded Short Sleeve T-shirts")
                .shouldDisplayTotalProduct(3)
                .shouldDisplayAllProduct(productList)
                .shouldDisplayPriceBreakUp(productList)
                .confirmCheckout();

    }

}
