/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author xumakgt6 (Allan Revolorio)
 */
import com.liferay.portal.util.PortalUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.jackrabbit.commons.JcrUtils;

@WebServlet(urlPatterns = {"/ImageLouderServlet"})
public class ImageLouderServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws javax.jcr.RepositoryException 
     */
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RepositoryException
    {
         if(!(request == null||(response == null))){
                response.setContentType("image/jpeg");
        
                 ServletOutputStream output = null;
                 PrintWriter out = response.getWriter();
        
            try{
                
                Repository repository;
                repository = JcrUtils.getRepository("http://localhost:8080/rmi");
                SimpleCredentials creds = new SimpleCredentials("admin",
                "admin".toCharArray());
                Session jcrSession = repository.login(creds, "default");
                System.out.println("Login successful, workspace: " + jcrSession.getWorkspace());
                String fileName = request.getParameter("id");
                System.out.println(fileName);
                Node root = jcrSession.getRootNode();
                Node Images = root.getNode("Images");
                Property property = Images.getProperty(fileName);
                InputStream is = property.getStream();
                int read =0;
                while ((read=is.read())!=-1){
                    out.write(read);
                }
                is.close();
                out.flush();
                out.close();
                jcrSession.save();
                
        //response.setHeader("Content-Disposition", "inline; filename=\"" + + "\"");
 
            
                jcrSession.save();
            } finally {
                out.close();
            
            }
        }
         
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
            throws ServletException, IOException {
        try {
            if(!(request == null||(response == null)))
            {    
            
                processRequest(request, response);
            }
        } catch (RepositoryException ex) {
            Logger.getLogger(ImageLouderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
          if(!(request == null||(response == null))){
            
                processRequest(request, response);
            }
        } catch (RepositoryException ex) {
            Logger.getLogger(ImageLouderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "This Servlet add an image file to a JackRabbit's Remote Repository";
    }// </editor-fold>

}
