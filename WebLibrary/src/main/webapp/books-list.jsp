<%@ page import="com.myname.entity.Book" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Books list</title>
</head>
<body>
<h1>Books list</h1>
<%
    List<Book> books = (List)request.getAttribute("books");
%>
<% for (Book book : books) {%>
<a href="http://localhost:8080/my-app/book.jsp.jsp/?id=<%= book.getId() %>"> <%= book.getTitle() %> - <%= book.getAuthor() %></a>
<br/>
<% } %>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<a href="http://localhost:8080/my-app/static/add.html">Go do the adding page</a>
<br/>
<a href="http://localhost:8080/my-app/static/search.html">Go read some books</a>
</body>
</html>