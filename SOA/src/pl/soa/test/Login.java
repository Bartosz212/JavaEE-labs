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
import java.util.Vector;

@WebServlet(name = "Login", urlPatterns = "/login")
public class Login extends HttpServlet {
        private class PersonalData {
            private String login;
            private String password;
            private String name;
            private String surname;

            public PersonalData(String login, String password, String name, String surname){
                this.login = login;
                this.password = password;
                this.name = name;
                this.surname = surname;
            }

            public String getLogin() {
                return login;
            }

            public String getPassword() {
                return password;
            }
        }

        public static List<String> listOfLoggedUsers = new ArrayList<>();
        Vector<PersonalData> personalDataVector;
        String htmlTemplate = "<html><head><title>Logowanie</title></head><body>";
        String htmlLoginFormTemplate = "<form action=\"login\" method=\"post\">Login: <input type=\"text\" name=\"login\" /><br><br>Hasło: <input type=\"password\" name=\"haslo\" /><br><br><input type=\"submit\" value=\"Wyślij\" /></form></body></html>";

        private Boolean isLoginDataCorrect(String login, String password){
            for (PersonalData element : personalDataVector){
                if (element.getLogin().equals(login) && element.getPassword().equals(password))
                    return true;
            }

            return false;
        }

        private void printResponse(HttpServletResponse response, int status) throws IOException {
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();

            if (status == 0){
                out.println(htmlTemplate + htmlLoginFormTemplate);
            }
            else if (status == -1){
                out.println(htmlTemplate);
                out.println("Nie podałeś loginu<br><br>");
                out.println(htmlLoginFormTemplate);
            }
            else if (status == -2){
                out.println(htmlTemplate);
                out.println("Nie podałeś hasła<br><br>");
                out.println(htmlLoginFormTemplate);
            }
            else if (status == -3){
                out.println(htmlTemplate);
                out.println("Podaj login i hasło!<br><br>");
                out.println(htmlLoginFormTemplate);
            }
            else if (status == -4){
                out.println(htmlTemplate);
                out.println("Zły login lub hasło<br><br>");
                out.println(htmlLoginFormTemplate);
            }

            out.close();
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String login = request.getParameter("login");
            String password = request.getParameter("haslo");

            if (login.equals("") && password.equals(""))
                printResponse(response, -3);
            else if (login.equals(""))
                printResponse(response, -1);
            else if (password.equals(""))
                printResponse(response, -2);
            else if (!isLoginDataCorrect(login, password))
                printResponse(response, -4);
            else if (isLoginDataCorrect(login, password)){
                String cookieValue = login + "_" + System.currentTimeMillis();
                Cookie cookie = new Cookie("sessionID", cookieValue);
                cookie.setMaxAge(120);
                response.addCookie(cookie);
                listOfLoggedUsers.add(cookieValue);
                response.sendRedirect("guestsBook");
            }
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            personalDataVector = new Vector<>();
            personalDataVector.add(new PersonalData("janK", "kowalski", "Jan", "Kowalski"));
            personalDataVector.add(new PersonalData("pawelN", "nowak", "Paweł", "Nowak"));
            personalDataVector.add(new PersonalData("bartoszS", "sliwa", "Bartosz", "Śliwa"));

            printResponse(response, 0);
        }
    }
