package dao;

import bean.frontEnd.Order;

import java.util.List;

public interface OderDao {

    int queryTotalCount();

    List<Order> queryPageOrder(int number);

    boolean placeOrder(Order order);

    List<Order> findAllOrders(String uid);

    void deleteOrder(String oid);

}
