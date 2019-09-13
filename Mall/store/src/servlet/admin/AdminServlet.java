package servlet.admin;

import bean.admin.Admin;
import bean.admin.Page;
import service.AdminService;
import service.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    private AdminService adminService = new AdminServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("addAdmin".equals(op)) {
            addAdmin(request,response);
        }else if("updateAdmin".equals(op)) {
            updateAdmin(request,response);
        }else if ("deleteMulti".equals(op)) {
            deleteMulti(request,response);
        }else if ("login".equals(op)) {
            login(request,response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String adminName = request.getParameter("adminName");
        String password = request.getParameter("password");
        Admin admin = new Admin();
        admin.setAdminName(adminName);
        admin.setPassword(password);
        boolean result = adminService.isExist(admin);
        if (result) {
            HttpSession session = request.getSession();
            session.setAttribute("admin",admin);
            response.getWriter().println("登录成功！");
            response.setHeader("refresh","1;url=" + request.getContextPath() + "/admin/main.jsp");
            // request.getRequestDispatcher("/admin/main.jsp").forward(request,response);
        }else {
            response.getWriter().println("用户名或者密码错误，请重新输入");
            response.setHeader("refresh","2;url=" + request.getContextPath() + "/admin/index.jsp");
        }
    }

    private void deleteMulti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] adminIds = request.getParameterValues("adminId");
        for (String adminId : adminIds) {
            adminService.deleteAdmin(adminId);
        }
        response.getWriter().println("删除成功，即将跳转！");
        response.setHeader("refresh","1;url=" + request.getContextPath() + "/AdminServlet?op=findPageAdmin&num=1");
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String adminName = request.getParameter("adminName");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        if (password.equals(repeatPassword)) {
            Admin admin = new Admin();
            admin.setAdminName(adminName);
            admin.setPassword(password);
            adminService.updateAdmin(admin);
            response.setHeader("refresh","1;url=" + request.getContextPath() + "/AdminServlet?op=findPageAdmin&num=1");
        }else{
            response.getWriter().println("两次密码输入不一致，请重新输入!");
        }

    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String adminName = request.getParameter("adminName");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        if (password.equals(repeatPassword)) {
            Admin admin = new Admin();
            admin.setAdminName(adminName);
            admin.setPassword(password);
            int i = adminService.addAdmin(admin);
            if (i == -1) {
                response.getWriter().println("当前系统用户已存在，请重新添加");
                response.setHeader("refresh","1;url=" + request.getContextPath() + "/admin/admin/addAdmin.jsp");
            }else {
                response.getWriter().println("添加成功，即将跳转到所有系统用户列表");
                response.setHeader("refresh","1;url=" + request.getContextPath() + "/AdminServlet?op=findPageAdmin&num=1");
            }
        }else{
            response.getWriter().println("两次密码输入不一致，请重新输入");
            response.setHeader("refresh","1;url=" + request.getContextPath() + "/admin/admin/addAdmin.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("findPageAdmin".equals(op)) {
            findPageAdmin(request,response);
        }else if ("deleteAdmin".equals(op)) {
            deleteAdmin(request,response);
        }else if ("logout".equals(op)) {
            logout(request,response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("admin");
        response.getWriter().println("退出成功！");
        response.setHeader("refresh","1;url=" + request.getContextPath() + "/admin/index.jsp");
    }

    private void deleteAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminId = request.getParameter("adminId");
        adminService.deleteAdmin(adminId);
        request.getRequestDispatcher(request.getContextPath() + "/AdminServlet?op=findPageAdmin&num=1").forward(request,response);
    }

    private void findPageAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String num = request.getParameter("num");
        int number = 0;
        try {
            number = Integer.parseInt(num);
        }catch (Exception e) {
            response.getWriter().println("输入页数有误！");
            return;
        }
        Page page = adminService.findPageAdmin(number);
        HttpSession session = request.getSession();
        session.setAttribute("pageHelper",page);
        request.getRequestDispatcher("/admin/admin/adminList.jsp").forward(request,response);
    }
}
