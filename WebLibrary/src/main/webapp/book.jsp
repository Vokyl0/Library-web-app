<%@ page import="com.myname.entity.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Book book = (Book)request.getAttribute("book");
%>
<h1><%= book.getTitle() %></h1>
<br/>
<a href="http://localhost:8080/my-app/static/add.html">Go do the adding page</a>
<br/>
<a href="http://localhost:8080/my-app/static/search.html">Go read some books</a>
<p><%= book.getContent() %></p>
</body>
</html>
