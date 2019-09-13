package service;

import bean.admin.Category;
import bean.admin.Page;
import bean.frontEnd.Order;
import dao.OderDao;
import dao.OrderDaoImpl;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OderDao orderDao = new OrderDaoImpl();

    @Override
    public Page findPageOrder(int number) {
        int totalCount = orderDao.queryTotalCount();
        List<Order> orders = orderDao.queryPageOrder(number);
        Page page = new Page();
        page.setCurrentPage(number);
        page.setNextPage(number + 1);
        page.setPreviousPage(number - 1);
        page.setTotalCount(totalCount);
        page.setList(orders);
        int totalPageCount = (int) Math.ceil(totalCount * 1.0 / Page.PAGE_SIZE);
        page.setTotalPageCount(totalPageCount);
        return page;
    }

    @Override
    public boolean placeOrder(Order order) {
        return orderDao.placeOrder(order);
    }

    @Override
    public List<Order> findAllOrders(String uid) {
        return orderDao.findAllOrders(uid);
    }

    @Override
    public void deleteOrder(String oid) {
        orderDao.deleteOrder(oid);
    }
}
