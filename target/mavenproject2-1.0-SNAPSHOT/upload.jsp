<%-- 
    Document   : upload
    Created on : Mar 19, 2014, 7:33:17 AM
    Author     : xumakgt6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add to Jack's Rabbit Repository</title>
    </head>
    <body>
        <h1>ContentViewer!</h1>
        <table>
            <tr>
                <td><a href="Viewer">Viewer</a></td>
                <td><a href="upload.jsp">Upload</a></td>
            </tr>
        </table>
        <h3>File loader:</h3>
        write a message: <br />
        <form action="LoadServlet" method="POST" enctype="multipart/form-data">
            Message Name: <input type="text" name="msg">
            File : <input type="file" name="file">
                   <input type="submit" value="upload" />
        </form>
    </body>
</html>