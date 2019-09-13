package dao;

import bean.admin.Page;
import bean.frontEnd.Order;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OderDao {

    private QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());

    @Override
    public int queryTotalCount() {
        Long count = 0L;
        try {
            count = runner.query("select count(uid) from `order`", new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count.intValue();
    }

    @Override
    public List<Order> queryPageOrder(int number) {
        List<Order> orders = null;
        try {
            orders = runner.query("select * from `order` limit ? offset ?",
                    new BeanListHandler<Order>(Order.class),
                    Page.PAGE_SIZE,(number - 1) * Page.PAGE_SIZE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean placeOrder(Order order) {

        try {
            runner.update("insert into `order` values(?,?,?,?,?,?,?,?,?)",
                    order.getOid(),
                    order.getMoney(),
                    order.getRecipient(),
                    order.getTel(),
                    order.getAddress(),
                    order.getState(),
                    order.getOrdertime(),
                    order.getUsername(),
                    order.getUid());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Order> findAllOrders(String uid) {
        List<Order> query = null;
        try {
            query = runner.query("select * from `order` where uid = ?",new BeanListHandler<Order>(Order.class),uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (query != null) {
            return query;
        }
        return null;
    }

    @Override
    public void deleteOrder(String oid) {
        try {
            runner.update("delete from `order` where oid = ?",oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
