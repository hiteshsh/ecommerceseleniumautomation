package Model;

/**
 * Created by hiteshs on 8/11/18.
 */
public class PriceBreakup {

    private double total;
    private double shipping;
    private double tax;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
}
