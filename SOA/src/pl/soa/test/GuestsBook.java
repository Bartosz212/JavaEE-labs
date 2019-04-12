package pl.soa.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GuestsBook", urlPatterns = "/guestsBook")
public class GuestsBook extends HttpServlet {
    List<String> listOfEntries = new ArrayList<>();

    String htmlHeadTemplate = "<html><head><title>Księga gości</title></head><body>";
    String htmlFeedbackFormTemplate = "<form action=\"guestsBook\" method=\"post\"><b>Please send your feedback:</b><br>Your name: <input type=\"text\" name=\"name\" />" +
            "<br>Your email: <input type=\"text\" name=\"email\" />" +
            "<br>Comment: <input type=\"text\" name=\"comment\" />" +
            "<br><input type=\"submit\" value=\"Send feedback\" /></form><br><hr><br>";
    String htmlEnd = "</body></html>";


    private Boolean hasValidSession(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie element : cookies) {
                if (element.getName().equals("sessionID")) {
                    if (Login.listOfLoggedUsers.contains(element.getValue())) {
                        Cookie cookie = new Cookie("sessionID", element.getValue());
                        cookie.setMaxAge(120);
                        response.addCookie(cookie);

                        return true;
                    } else
                        return false;
                }
            }
        }

        return false;
    }

    private void printResponse(HttpServletResponse response, int status) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(htmlHeadTemplate + htmlFeedbackFormTemplate);

        for (String element : listOfEntries)
            out.println(element);

        out.println(htmlEnd);
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (hasValidSession(request, response)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String comment = request.getParameter("comment");

            if (!name.equals("") && !email.equals("") && !comment.equals(""))
                listOfEntries.add("<b>" + name + "</b> (" + email + ") says<br><code>" + comment + "</code><br><br>");

            printResponse(response, 0);
        }
        else
            response.sendRedirect("login");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (hasValidSession(request, response))
            printResponse(response, 0);
        else
            response.sendRedirect("/login");
    }
}
