<%-- 
    Document   : upload
    Created on : Mar 19, 2014, 7:33:17 AM
    Author     : xumakgt6 (Allan Revolorio)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add to Jack's Rabbit Repository</title>
    </head>
    <body>
    <center><h1>ContentViewer!</h1>
        <table>
            <tr>
                <td><a href="Viewer">Viewer</a></td>
                <td><a href="upload.jsp">Upload</a></td>
            </tr>
        </table></center>
        <h3>File loader:</h3>
        Please select a file: <br />
        <form action="LoadServlet" method="POST" enctype="multipart/form-data">
            File : <input type="file" name="file">
                   <input type="submit" value="upload" />
        </form>
    
    </body>
</html>