package servlet.frontend;


import bean.frontEnd.CartItem;
import service.CartService;
import service.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CartServlet",urlPatterns = "/CartServlet")
public class CartServlet extends HttpServlet {

    private CartService cartService = new CartServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void findCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uid = request.getParameter("uid");
        Map<Object,Object> cart = cartService.showShoppingCartByUid(uid);
        if(((List<CartItem>)cart.get("cartItemList")).size() == 0){
            response.getWriter().println("购物车为空");
            String referer = request.getHeader("referer");
            response.setHeader("refresh","2;url=" + referer);
        }else{
            request.getSession().setAttribute("cart",cart);
            request.getRequestDispatcher("/shoppingcart.jsp").forward(request,response);
        }
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");
        String uid = request.getParameter("uid");
        String referer = request.getHeader("referer");
        int number = cartService.queryProductNum(uid,pid);
        int num = number + 1;
        boolean result;
        if (number != 0) {
            result = cartService.modifyNum(pid,uid,num);
        }else {
            result = cartService.addToCart(pid,uid,num);
        }
        if(result){
            response.getWriter().println("商品已加入购物车");
        }else{
            response.getWriter().println("商品添加失败");
        }
        response.setHeader("refresh","1;url=" + referer);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("addCart".equals(op)) {
            addToCart(request,response);
        }else if ("findCart".equals(op)) {
            findCart(request,response);
        }else if ("deleteItem".equals(op)) {
            deleteItem(request,response);
        }
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uid = request.getParameter("uid");
        String pid = request.getParameter("itemid");
        boolean result = cartService.deleteItem(uid, pid);
        if(result){
            response.getWriter().println("商品移除成功");
        }else{
            response.getWriter().println("商品移除失败");
        }
        response.setHeader("refresh","1;url="+request.getContextPath()+"/CartServlet?op=findCart&uid=" + uid);
    }
}
