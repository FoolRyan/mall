package dao;

import bean.admin.Product;
import bean.frontEnd.Cart;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;

import java.sql.SQLException;
import java.util.List;

public class CartDaoImpl implements CartDao {

    private QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());

    @Override
    public int queryCartNumberByUser(int pid, int uid) {
        Long number = 0L;
        try {
            number = (Long) runner.query("select count(id) from shoppingcart where uid = ? and pid = ?",
                    new ScalarHandler(),
                    uid,pid);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return number.intValue();
    }

    @Override
    public boolean addCart(String pid, String num, int uid) {
        int update = 0;
        try {
            update = runner.update("insert into shoppingcart values (?,?,?)", uid, pid, num);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if (update == 0){
            return false;
        }
        return true;
    }

    @Override
    public void updateCart(int pid, int num, int uid) {
        try {
            runner.update("update shoppingcart set num = num + ? where uid = ? and pid = ?", num, uid, pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cart> queryCartByUser(int uid) {
        List<Cart> cartList = null;
        try {
            cartList = runner.query("SELECT * from shoppingcart where uid = ?",
                    new BeanListHandler<Cart>(Cart.class), uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartList;
    }

    @Override
    public boolean deleteItem(String uid,String pid) {
        boolean result = false;
        try {
            int update = runner.update("delete from shoppingcart where uid = ? and pid = ?", uid, pid);
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public List<Cart> showShoppingCartByUid(String uid) {
        List<Cart> query = null;
        try {
            query = runner.query("select * from shoppingcart where uid = ?",
                    new BeanListHandler<Cart>(Cart.class),uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    @Override
    public int queryProductNum(String uid, String pid) {
        List<Product> productList = null;
        try {
            productList = runner.query("select * from shoppingcart where uid = ? and pid = ?",
                    new BeanListHandler<Product>(Product.class),uid,pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int i = 0;
        if (productList.size() != 0) {
            String sql = "select num from shoppingcart where uid = " + uid + " and pid = " + pid;
            try {
                i = runner.query(sql,new ScalarHandler<>());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            i = 0;
        }
        return i;
    }

    @Override
    public boolean modifyNum(String pid, String uid, int num) {
        try {
            runner.update("update shoppingcart set num = ? where uid = ? and pid = ?",num,uid,pid);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
