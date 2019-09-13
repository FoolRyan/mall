package servlet.frontend;

import bean.admin.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.UserServiceImpl;
import util.MailUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/frontEndUserServlet")
public class frontEndUserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");

        if ("regist".equals(op)) {
            regist(request,response);
        }else if ("login".equals(op)) {
            login(request,response);
        }else if ("updateUserInfo".equals(op)) {
            updateUserInfo(request,response);
        }
    }

    private void updateUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        boolean result = userService.updateUserInfo(user);
        if (result) {
            response.getWriter().println("修改成功");
            response.setHeader("refresh","1;url=" + request.getContextPath() + "/user/personal.jsp");
        }else {
            response.getWriter().println("修改失败");
            response.setHeader("refresh","1;url=" + request.getContextPath() + "/user/personal.jsp");
        }
    }

    private void regist(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
            response.getWriter().println("注册失败，请重新注册");
            request.getSession().setAttribute("msg",user);
            request.getRequestDispatcher("/user/regist.jsp").forward(request,response);
        }else {
            String email = request.getParameter("email");
            String requestURI = request.getRequestURI();
            String emailMsg = "<a href='" + requestURI + "?op=activatedUser&uid=" + user.getUid() + "'>点我激活</a>";
            MailUtils.sendMail(email,emailMsg);
            response.getWriter().println("注册成功，请尽快登录邮箱激活账号");
            response.setHeader("refresh","1;url=" + request.getContextPath() + "/index.jsp");
        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String verifyCode = request.getParameter("verifyCode");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user = userService.isExist(user);
        if (user != null) {
            String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
            if (checkcode_session.equals(verifyCode)){
                request.getSession().setAttribute("user",user);
                request.getRequestDispatcher("/index.jsp").forward(request,response);
            }else {
                response.getWriter().println("验证码错误，请重新输入");
                response.setHeader("refresh","2;url=" + request.getContextPath() + "/user/login.jsp");
            }

        }else {
            response.getWriter().println("用户名或者密码错误，请重新输入");
            response.setHeader("refresh","2;url=" + request.getContextPath() + "/user/login.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("logout".equals(op)) {
            logout(request,response);
        }else if ("activatedUser".equals(op)) {
            activatedUser(request,response);
        }
    }

    private void activatedUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uid = request.getParameter("uid");
        boolean result = userService.activatedUser(uid);
        if(result){
            response.getWriter().println("激活成功,即将跳转至首页！");
        }else{
            response.getWriter().println("激活失败");
        }
        response.setHeader("refresh","2;url=" + request.getContextPath() + "/index.jsp");
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        response.getWriter().println("退出成功！");
        response.setHeader("refresh","1;url=" + request.getContextPath() + "/index.jsp");
    }
}
