package servlet.admin;

import bean.admin.Page;
import bean.admin.User;
import bean.frontEnd.Cart;
import bean.frontEnd.Order;
import service.OrderService;
import service.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    private OrderService orderService = new OrderServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("findPageOrder".equals(op)) {
            findPageOrder(request,response);
        }else if ("deleteOrder".equals(op)) {
            deleteOrder(request,response);
        }
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oid = request.getParameter("oid");
        orderService.deleteOrder(oid);
        request.getRequestDispatcher("/OrderServlet?op=findPageOrder&num=1").forward(request,response);
    }

    private void findPageOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String num = request.getParameter("num");
        int number = 0;
        try {
            number = Integer.parseInt(num);
        }catch (Exception e) {
            response.getWriter().println("输入页数有误！");
            return;
        }
        Page page = orderService.findPageOrder(number);
        HttpSession session = request.getSession();
        session.setAttribute("pageHelper",page);
        request.getRequestDispatcher("/admin/order/orderList.jsp").forward(request,response);

    }

}
