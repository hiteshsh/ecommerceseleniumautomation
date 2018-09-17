package tests;

import Model.Product;
import org.testng.annotations.Test;
import pages.Cart;
import pages.NavigationMenu;

import java.util.ArrayList;
import java.util.List;

import static Model.Category.WOMEN;

/**
 * Created by hiteshs on 9/16/18.
 */
public class CartTest extends BaseTest{

    @Test
    public void removeProductFromTopCartSection(){

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
                .continueShopping();

        Cart cartDropdownBlock= new Cart(driver);
        cartDropdownBlock.viewCartBlock()
                .productShouldBeInCartBlock("Faded Short Sleeve T-shirts")
                .removeProductFromCartBlock("Faded Short Sleeve T-shirts")
                .productShouldNotBeInCartBlock("Faded Short Sleeve T-shirts");


    }


    @Test
    public void removeProductFromCartPage(){

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
                .removeProductFromCart("Faded Short Sleeve T-shirts")
                .productShouldNotBeAvailableInCart("Faded Short Sleeve T-shirts");
    }
}
