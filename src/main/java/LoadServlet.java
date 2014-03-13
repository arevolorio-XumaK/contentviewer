/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author xumakgt6
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jcr.*;
import javax.naming.NamingException;
import javax.jcr.Repository;
import javax.servlet.http.Part;
import org.apache.jackrabbit.commons.JcrUtils;

public class LoadServlet extends HttpServlet {

    /**
* Processes requests for both HTTP <code>GET</code> and <code>POST</code>
* methods.
*
* @param request
* @param response
* @throws ServletException if a -specific error occurs
* @throws IOException if an I/O error occurs
* @throws javax.jcr.RepositoryException
* @throws javax.naming.NamingException
*/
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RepositoryException, NamingException {
        
        Repository repository;
        repository = JcrUtils.getRepository("http://localhost:8080/rmi");
        SimpleCredentials creds = new SimpleCredentials("admin",
            "admin".toCharArray());
        Session jcrSession = repository.login(creds, "default");
        System.out.println("Login successful, workspace: " + jcrSession.getWorkspace());
        String message = request.getParameter("msg");
        String fromRepo = "CDP";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        InputStream is = null;
        OutputStream os = null;
        Part filePart = request.getPart("img");
        //String fileName = getFileName(filePart);
      try{
          Node root = jcrSession.getRootNode();
          Node node = root.getNode("Message");
          node.setProperty("message", message);//
          Property nodeProp = node.getProperty("message");
          fromRepo =nodeProp.getString();
          jcrSession.save();
          
      }finally{
          jcrSession.logout();
          
      }
      
String title = "Uploading to JackRabbit Repo";
      String docType =
      "<!doctype html public \"-//w3c//dtd html 4.0 " +
      "transitional//en\">\n";
      out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                " <li><b>Message</b>: "
                + request.getParameter("msg") + "\n" +
                " <li><b>Message from the repo</b>:" + fromRepo
                + "</li></ul>\n" +
                "</body></html>");
      out.close();
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
* Handles the HTTP <code>GET</code> method.
*
* @param request servlet request
* @param response servlet response
* @throws ServletException if a servlet-specific error occurs
* @throws IOException if an I/O error occurs
*/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
            
        
    }

    /**
* Handles the HTTP <code>POST</code> method.
*
* @param request servlet request
* @param response servlet response
* @throws ServletException if a servlet-specific error occurs
* @throws IOException if an I/O error occurs
* @throws javax.jcr.RepositoryException
*/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            processRequest(request, response);
        } catch (RepositoryException ex) {
            Logger.getLogger(LoadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(LoadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
     
       
    }

    /**
* Returns a short description of the servlet.
*
* @return a String containing servlet description
*/
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    

}

