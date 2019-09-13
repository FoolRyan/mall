package servlet.admin;

import bean.admin.Page;
import bean.admin.Product;
import bean.admin.Category;
import org.apache.commons.beanutils.BeanUtils;
import service.CategoryService;
import service.CategoryServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;
import util.FileUploadUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    private ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String> resultMap =  FileUploadUtils.parseRequest(request);
        Product product = new Product();
        String op = resultMap.get("op");
        if ("addProduct".equals(op)) {
            try {
                BeanUtils.populate(product,resultMap);
                productService.addProduct(product);
                response.getWriter().println("添加成功，即将跳转");
                response.setHeader("refresh","1;url=" + request.getContextPath() + "/ProductServlet?op=findPageProduct&num=1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("updateProduct".equals(op)) {
            updateProduct(request,response,resultMap);
        } else if ("deleteMulti".equals(op)) {
            deleteMulti(request,response);
        }
    }

    private void findProductByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        Product productByPid = productService.findProductByPid(Integer.parseInt(pid));
        HttpSession session = request.getSession();
        session.setAttribute("product",productByPid);
        request.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(request,response);
    }

    private void updateProduct(HttpServletRequest request,HttpServletResponse response,Map<String,String> resultMap) {
        Product product = new Product();
        try {
            BeanUtils.populate(product,resultMap);
            productService.updateProduct(product);
            response.getWriter().println("修改成功，即将跳转");
            response.setHeader("refresh","1;url=" + request.getContextPath() + "/ProductServlet?op=findPageProduct&num=1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] pids = request.getParameterValues("pid");
        for (String pid : pids) {
            productService.deleteProduct(pid);
        }
        response.getWriter().println("删除成功，即将跳转！");
        response.setHeader("refresh","1;url=" + request.getContextPath() + "/ProductServlet?op=findPageProduct&num=1");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        String pid = request.getParameter("pid");
        if ("loadCategories".equals(op)) {
            loadCategories(request,response);
        }else if ("findPageProduct".equals(op)) {
            findPageProduct(request,response);
        }else if ("deleteProduct".equals(op)) {
            productService.deleteProduct(pid);
            request.getRequestDispatcher("/ProductServlet?op=findPageProduct&num=1").forward(request,response);
        }else if ("findProductByPid".equals(op)){
            findProductByPid(request,response);
        }else if ("multiconditionSearch".equals(op)) {
            multiconditionSearch(request,response);
        }
    }

    private void multiconditionSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        String cid = request.getParameter("cid");
        String pname = request.getParameter("pname");
        String maxprice = request.getParameter("maxprice");
        String minprice = request.getParameter("minprice");
        // 对前端数据进行校验
        List<Product> products = productService.conditionSearch(pid, cid, pname, maxprice, minprice);
        // productPage.setList(products);
        request.setAttribute("searchProduct",products);
        request.getRequestDispatcher("/admin/product/searchProductList.jsp").forward(request,response);
    }

    private void findPageProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String num = request.getParameter("num");
        int number = 0;
        try {
            number = Integer.parseInt(num);
        }catch (Exception e) {
            response.getWriter().println("输入页数有误！");
            return;
        }
        Page page = productService.findPageProduct(number);
        HttpSession session = request.getSession();
        session.setAttribute("pageHelper",page);
        request.getRequestDispatcher("/admin/product/productList.jsp").forward(request,response);
    }

    private void loadCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 查询category数据，然后写入域，转发到addProduct.jsp
        List<Category> categoryList = categoryService.findAllCategory();
        request.setAttribute("categories",categoryList);
        request.getRequestDispatcher("/admin/product/addProduct.jsp").forward(request,response);
    }
}
