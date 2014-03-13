<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Load Image</title>
    </head>
    <body>
        <h1>ContentViewer!</h1>
        <h3>File loader:</h3>
        write a message: <br />
        <form action="LoadServlet" method="POST" enctype="multipart/form-data">
            Message Name: <input type="text" name="msg">
            File : <input type="file" name="file">
                   <input type="submit" value="Submit" />
        </form>
    </body>
</html>