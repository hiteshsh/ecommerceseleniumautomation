package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by hiteshs on 4/9/18.
 */
public class NavigationMenu {

    @FindBy(xpath = "//a[contains(@title,'Women')]")
    private WebElement categoryWomen;
    @FindBy(xpath = "//a[contains(@title,'Dresses')]")
    private WebElement categoryDress;
    @FindBy(xpath = "//a[contains(@title,'T-shirts')]")
    private WebElement categorytShirt;

    public NavigationMenu(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void navigateToCategory(Enum Category){
        if (Category.name().equalsIgnoreCase("WOMEN")) {
            categoryWomen.click();
        }
        else if(Category.name().equalsIgnoreCase("DRESSES")){
            categoryDress.click();
        }
        else{
            categorytShirt.click();
        }

    }
}
