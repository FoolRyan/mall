/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/7/9
 * Time: 10:28
 */
package bean.frontEnd;

import bean.admin.Product;

public class CartItem {

    private Product product;

    private int num;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", num=" + num +
                '}';
    }
}
