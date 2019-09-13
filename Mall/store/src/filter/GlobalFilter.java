package filter;

import bean.admin.Admin;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "GlobalFilter",urlPatterns = "/*")
public class GlobalFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestURI = request.getRequestURI();
        request.setCharacterEncoding("utf-8");
        if (isStaticResource(requestURI)){
            response.setContentType("text/html;charset=utf-8");
            chain.doFilter(request,response);
            return;
        }
        if (requestURI.contains("/admin")) {
            Admin admin = (Admin) request.getSession().getAttribute("admin");
            if (admin == null) {
                request.getRequestDispatcher("/admin/index.jsp").forward(request,response);
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    private boolean isStaticResource(String requestURI) {
        if (requestURI.contains(".jpg") || requestURI.contains(".png")
                || requestURI.contains(".js") || requestURI.contains(".css")) {
            return false;
        }
        return true;
    }

    public void init(FilterConfig config) throws ServletException {

    }




}
