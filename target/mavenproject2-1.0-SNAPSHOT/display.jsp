<%-- 
    Document   : display
    Created on : Mar 17, 2014, 11:40:50 AM
    Author     : xumakgt6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Image Display</title>
    </head>
    <body>
        <h1>loud from Jack-Rabbit's Repository</h1>
        <% String nombre = request.getParameter("id"); %>
        <p>nombre de archivo:<%= nombre %></p>
        <form action="ImageLouderServlet">
            <input type="hidden" name="f" id="f" value="<%= nombre %>" >
            <input type="submit" value="Submit" />
        </form>
        <img src= "ImageLouderServlet" width = "600" height = "400"/>
    </body>
</html>
