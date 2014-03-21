/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author xumakgt6 (Allan Revolorio)
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.jackrabbit.commons.JcrUtils;

    /*
    * Viewer es el servlet encargado seleccionar una imagen del repositorio de jackrabbit utilizando un select para poder 
    * desplegado en el browser.
    * @version Alfa4 20/3/2014
    */
public class Viewer extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /*creado por @autor xumakgt6 (Allan Revolorio) 
    *processRequest(HttpServletRequest request, HttpServletResponse response) genera un html con un select con todos las imagenes almacenada en el 
    * repositorio remoto de jackrabbit.
    * @param request, es el HttpServletRequest obtenido de la llamada al servlet
    * @param response, es el HttpServletResponse que al cual responde la llamada al servlet
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RepositoryException {
         if(!(request == null||(response == null))){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            Repository repository;
            repository = JcrUtils.getRepository("http://localhost:8080/rmi");
            SimpleCredentials creds = new SimpleCredentials("admin",
                                                            "admin".toCharArray());
            Session jcrSession = repository.login(creds, "default");
            Node root = jcrSession.getRootNode();
            Node imageNode = root.getNode("Images");
            PropertyIterator img_iterator =imageNode.getProperties();
            System.out.println("Login successful, workspace: " + jcrSession.getWorkspace());
            try {
            
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Viewer</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<center><h1>Image Viewer</h1>"
                             + "  <table>\n" +
"                                   <tr>\n" +
"                                       <td><a href=\"Viewer\">Viewer</a></td>\n" +
"                                       <td><a href=\"upload.jsp\">Upload</a></td>\n" +
"                                   </tr>"
                               + "</table></center>");
                out.println("<center><form action=\"Viewer\" method =\"POST\" >");
                out.println("</br><p>Select an Image:</>");
                out.println("<select name=\"id\"onchange=\"this.form.submit()\">");
                out.println("<option value=\"\">select an image</option>");
                while(img_iterator.hasNext()){
                    Property tmp = img_iterator.nextProperty();
                    String filename = tmp.getName();
                    if(!(filename.equals("jcr:primaryType")))
                    {
                        out.println("<option value=\""+filename+"\">"+filename+"</option>");
                    }
                }
            
                out.println("</select>");
                out.println("</form></br></br></center>");
                if(!(request.getParameter("id").toString().equals(""))){ // si el id es "" quiere decir que es la primera vez que se invoca a la pagina 
                    out.println("<center><img src=\"ImageLouderServlet?id="+request.getParameter("id")+"\" width=\"40%\" height=\"40%\"></center>");
                }
                out.println("</body>");
                out.println("</html>");
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
             if(!(request == null||(response == null))){
                processRequest(request, response);
            }
        } catch (RepositoryException ex) {
            Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "This ServletClass is display the selected image from the select";
    }// </editor-fold>

}
