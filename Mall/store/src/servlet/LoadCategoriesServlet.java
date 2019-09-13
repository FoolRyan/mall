package servlet;

import bean.admin.Category;
import bean.admin.Product;
import service.CategoryService;
import service.CategoryServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoadCategoriesServlet",value = "/LoadCategoriesServlet",loadOnStartup = 3)
public class LoadCategoriesServlet extends HttpServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    private ProductService productService = new ProductServiceImpl();

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 执行查询数据库的Categories,然后放到Context域
        ServletContext servletContext = config.getServletContext();
        List<Category> categories = categoryService.findAllCategory();
        List<Product> productTop = productService.productTop();
        List<Product> hotProducts = productService.hotProducts();
        servletContext.setAttribute("categories",categories);
        servletContext.setAttribute("productTop",productTop);
        servletContext.setAttribute("hotProducts",hotProducts);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
