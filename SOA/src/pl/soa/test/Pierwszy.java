package pl.soa.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "Pierwszy", urlPatterns = "/pierwszy")
public class Pierwszy extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("imie");
        int age = Integer.parseInt(request.getParameter("wiek"));

        out.println("<html><head><title>Pierwszy servlet</title></head><body>");
        out.println("Hej " + name + ", masz " + age + " lat.");
        out.println("</body></html>");
        out.close();
    }
}
