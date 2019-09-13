package servlet.admin;

import bean.admin.Category;
import bean.admin.Page;
import dao.CategoryDao;
import dao.CategoryDaoImpl;
import service.CategoryService;
import service.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("addCategory".equals(op)){
            addCategory(request,response);
        }else if ("updateCategory".equals(op)) {
            updateCategory(request,response);
        }else if("deleteMulti".equals(op)) {
            deleteMulti(request,response);
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) {
        String cname = request.getParameter("cname");
        String cid = request.getParameter("cid");
        Category category = new Category();
        category.setCname(cname);
        category.setCid(Integer.parseInt(cid));
        categoryService.updateCategory(category);
        response.setHeader("refresh","1;url=" + request.getContextPath() + "/CategoryServlet?op=findPageCategory&num=1");
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cname = request.getParameter("cname");
        Category category = new Category();
        category.setCname(cname);
        int i = categoryService.addCategory(category);
        if(i == -1){
            response.getWriter().println("当前分类已存在，请重新添加一个新的分类");
            response.setHeader("refresh","1;url=" + request.getContextPath() + "/admin/category/addCategory.jsp");
        }else{
            response.getWriter().println("添加成功，即将跳转到所有分类列表");
            response.setHeader("refresh","1;url=" + request.getContextPath() + "/CategoryServlet?op=findPageCategory&num=1");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("findPageCategory".equals(op)) {
            findPageCategory(request,response);
        }else if ("findAllCategory".equals(op)) {
            findAllCategory(request,response);
        }else if ("deleteCategory".equals(op)) {
            deleteCategory(request,response);
        }
    }

    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] cids = request.getParameterValues("cid");
        for (String cid : cids) {
            categoryService.deleteCategory(cid);
        }
        response.getWriter().println("删除成功，即将跳转！");
        response.setHeader("refresh","1;url=" + request.getContextPath() + "/CategoryServlet?op=findPageCategory&num=1");
    }

    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categoryList = categoryService.findAllCategory();
        HttpSession session = request.getSession();
        session.setAttribute("categoryList",categoryList);
        request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request,response);


    }

    private void findPageCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String num = request.getParameter("num");
        int number = 0;
        try {
            number = Integer.parseInt(num);
        }catch (Exception e) {
            response.getWriter().println("输入页数有误！");
            return;
        }
        Page page = categoryService.findPageCategory(number);
        HttpSession session = request.getSession();
        session.setAttribute("pageHelper",page);
        request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request,response);
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        boolean result = categoryService.deleteCategory(cid);
        if (result) {
            request.getRequestDispatcher(request.getContextPath() + "/CategoryServlet?op=findPageCategory&num=1").forward(request,response);
        }
    }
}
