package tests;

import Model.Product;
import org.testng.annotations.Test;
import pages.Cart;
import pages.NavigationMenu;
import pages.ProductPage;

import java.util.ArrayList;
import java.util.List;

import static Model.Category.WOMEN;

/**
 * Created by hiteshs on 3/8/18.
 */
public class BuyProductTest extends BaseTest {

    @Test
    public void buyAProductFromWomenCategory()  {
        List<Product> productList= new ArrayList<Product>();
        Product product= new Product();
        product.setProductDesc("Faded Short Sleeve T-shirts");
        product.setUnitPrice(16.51);
        product.setQuantity(1);
        productList.add(product);

        NavigationMenu menu= new NavigationMenu(driver);
        menu.navigateToCategory(WOMEN);

        ProductPage productpage= new ProductPage(driver);
        productpage.addProductToCart("Faded Short Sleeve T-shirts");

        Cart cart= new Cart(driver);
        cart.checkoutProducts();
        cart.shouldDisplayTotalProduct(1);
        cart.shouldDisplayAllProduct(productList);
        cart.shouldDisplayPriceBreakUp(productList);
        cart.confirmCheckout();

    }

}
