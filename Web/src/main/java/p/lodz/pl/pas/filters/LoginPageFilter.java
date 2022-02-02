package p.lodz.pl.pas.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginPageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        boolean isLoginPage = req.getRequestURI().endsWith("login.xhtml");

        if (isLoggedIn || isLoginPage || session != null){
            chain.doFilter(request, response);
        }
        else{
            res.sendRedirect("/faces/login.xhtml");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
