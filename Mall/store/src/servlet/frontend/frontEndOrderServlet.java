package servlet.frontend;

import bean.admin.User;
import bean.frontEnd.Order;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.commons.beanutils.BeanUtils;
import service.OrderService;
import service.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/frontEndOrderServlet")
public class frontEndOrderServlet extends HttpServlet {

    private OrderService orderService = new OrderServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("placeOrder".equals(op)) {
            placeOrder(request,response);
        }
    }

    private void placeOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Order order = new Order();
        try {
            BeanUtils.populate(order,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        String address = request.getParameter("address");
        String recipient = request.getParameter("recipient");
        String tel = request.getParameter("tel");
        String username = request.getParameter("username");
//        String state = request.getParameter("state");
//        String money = request.getParameter("money");
//        order.setMoney(money);
//        order.setState(state);
//        order.setAddress(address);
        order.setRecipient(recipient);
        order.setTel(tel);
        order.setUsername(username);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        order.setOrdertime(time);
        String oid = time + "_" + (int)Math.random() * 10;
        order.setOid(oid);
        boolean result = orderService.placeOrder(order);
        if (result){
            findAllOrders(request,response);
        }else {
            response.getWriter().println("下单失败！");
            response.setHeader("refresh", "1;url=" + request.getContextPath() + "/placeOrder.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("myoid".equals(op)) {
            findAllOrders(request,response);
        }else if ("cancelOrder".equals(op)) {
            cancelOrder(request,response);
        }
    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) {

    }

    private void findAllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        List<Order> orders = orderService.findAllOrders(uid);
        request.getSession().setAttribute("orders",orders);
        request.getRequestDispatcher("/myOrders.jsp").forward(request,response);

    }
}
