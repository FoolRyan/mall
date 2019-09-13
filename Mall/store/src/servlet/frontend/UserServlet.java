package servlet.frontend;

import bean.admin.Page;
import bean.admin.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("addUser".equals(op)) {
            addUser(request,response);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        int i = userService.addUser(user);
        if (i == -1) {
            response.getWriter().println("当前用户已存在，请重新添加");
            response.setHeader("refresh","1;url=" + request.getContextPath() + "/admin/user/addUser.jsp");
        }else {
            response.getWriter().println("添加成功，即将跳转到所有注册用户列表");
            response.setHeader("refresh","1;url=" + request.getContextPath() + "/UserServlet?op=findPageUser&num=1");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("findPageUser".equals(op)) {
            findPageUser(request,response);
        }else if ("deleteUser".equals(op)) {
            deleteUser(request,response);
        }
    }

    private void findPageUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String num = request.getParameter("num");
        int number = 0;
        try {
            number = Integer.parseInt(num);
        }catch (Exception e) {
            response.getWriter().println("输入页数有误！");
            return;
        }
        Page page = userService.findPageUser(number);
        HttpSession session = request.getSession();
        session.setAttribute("pageHelper",page);
        request.getRequestDispatcher("/admin/user/userList.jsp").forward(request,response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        boolean result = userService.deleteUser(uid);
        if (result) {
            request.getRequestDispatcher(request.getContextPath() + "/UserServlet?op=findPageUser&num=1").forward(request,response);
        }
    }
}
