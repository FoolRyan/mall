package servlet.admin;

import bean.admin.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Ajax")
public class AjaxServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if ("isUsernameExist".equals(op)) {
            isUsernameExist(request,response);
        }else if ("isEmailExist".equals(op)) {
            isEmailExist(request,response);
        }
    }

    private void isEmailExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        boolean matches = email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        User result = userService.isEmailExist(email);
        if (result == null && matches) {
            response.getWriter().print(true);
        }else {
            response.getWriter().print(false);
        }
    }

    private void isUsernameExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        User result = userService.isUsernameExist(username);
        boolean matches = username.matches("^.{6,18}$");
        if (result == null && matches) {
            response.getWriter().print(true);
        }else {
            response.getWriter().print(false);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
