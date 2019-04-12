package pl.soa.test;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "AgeFilter", urlPatterns = "/agecheck")
public class AgeFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("FILTER");
        HttpServletRequest req = (HttpServletRequest) request;
        int age = Integer.parseInt(request.getParameter("age"));

        if(age<18){
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Halo, to nie dla Ciebie</title></head><body>");
            out.println("Hej, nie masz 18 lat." );
            out.println("</body></html>");
            out.close();
        }else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
