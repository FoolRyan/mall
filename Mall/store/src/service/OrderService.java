package service;

import bean.admin.Page;
import bean.frontEnd.Order;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

public interface OrderService {

    Page findPageOrder(int number);

    boolean placeOrder(Order order);

    List<Order> findAllOrders(String uid);

    void deleteOrder(String oid);
}
