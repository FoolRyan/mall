package servlet.frontend;

import bean.admin.Page;
import bean.admin.Product;
import service.ProductService;
import service.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/frontEndProductServlet")
public class frontEndProductServlet extends HttpServlet {

    private ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("findProductByCid".equals(op)) {
            findProductByCid(request,response);
        }else if ("findProductById".equals(op)) {
            findProductById(request,response);
        }else if ("findProByName".equals(op)) {
            findProByName(request,response);
        }
    }

    private void findProByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Product> list = null;
        for (int i = 0; i < 4; i++) {
            list = productService.findProByName(keyword,i);
            if (list.size() != 0) {
                break;
            }
        }
        request.setAttribute("products",list);
        request.getRequestDispatcher("/products.jsp").forward(request,response);
    }

    private void findProductById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        Product products = productService.findProductById(pid);
        HttpSession session = request.getSession();
        session.setAttribute("product",products);
        request.getRequestDispatcher("/productdetail.jsp").forward(request,response);
    }

    private void findProductByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        List<Product> products = productService.findProductByCid(cid);
        HttpSession session = request.getSession();
        session.setAttribute("products",products);
        request.getRequestDispatcher("/products.jsp").forward(request,response);
    }
}
