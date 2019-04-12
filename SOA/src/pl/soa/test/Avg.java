package pl.soa.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "Avg", urlPatterns = "/avg")
public class Avg extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Enumeration en = request.getParameterNames();

        List<Float> list = new ArrayList<>();
        int counter = 0;

        out.println("<html><head><title>Wynik średniej</title></head><body>");

        while(en.hasMoreElements())
        {
            Object objOri=en.nextElement();
            String param=(String)objOri;
            String value=request.getParameter(param);
            try {
                float number = Float.parseFloat(value);
                list.add(number);
                counter++;
            }catch (NumberFormatException e){
                if(!value.isEmpty()) {
                    out.println("<br> " + value + " nie jest liczbą");
                }
            }
        }
        Collections.sort(list);
        out.println("<br>Lista: <br>");
        for (Float l: list) {
            out.println(l + "<br>");
        }
        out.println("<br>");
        out.println("</body></html>");
        out.close();



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        float l1 = Float.parseFloat(request.getParameter("liczba1"));
        float l2 = Float.parseFloat(request.getParameter("liczba2"));
        float l3 = Float.parseFloat(request.getParameter("liczba3"));
        float l4 = Float.parseFloat(request.getParameter("liczba4"));
        float l5 = Float.parseFloat(request.getParameter("liczba5"));

        float avg = (l1+l2+l3+l4+l5)/5;

        out.println("<html><head><title>Wynik średniej</title></head><body>");
        out.println("Średnia liczb: "+l1+", "+l2+", "+l3+", "+l4+", "+l5+" wynosi: "+avg);
        out.println("</body></html>");
        out.close();
    }
}
