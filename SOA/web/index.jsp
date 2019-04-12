<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: bartoszsliwa
  Date: 2019-03-11
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Start</title>
  </head>
  <body>
  <h1>First test app in JavaEE</h1>
  <p>Strona w jsp</p>

  <%
    Date tmp = new Date();
    out.print("<h2>" + tmp.toString() + "</h2>");
  %>


  <a href="ageform.html">Piwo</a><br>
  <a href="piec.html">Srednia</a><br>
  <a href="postform.html">Sort</a><br>
  <a href="login">Zaloguj się</a>

  </body>
</html>
