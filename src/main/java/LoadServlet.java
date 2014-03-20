/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author xumakgt6 (Allan Revolorio)
 * @version alfa4
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jcr.*;
import javax.naming.NamingException;
import javax.jcr.Repository;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import org.apache.jackrabbit.commons.JcrUtils;
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class LoadServlet extends HttpServlet {
        private final static Logger LOGGER = 
            Logger.getLogger(LoadServlet.class.getCanonicalName());
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
        response.setContentType("text/html");
        //JackRabbit Log in 
        Repository repository;
        repository = JcrUtils.getRepository("http://localhost:8080/rmi");
        SimpleCredentials creds = new SimpleCredentials("admin",
            "admin".toCharArray());
        Session jcrSession = repository.login(creds, "default");
        System.out.println("Login successful, workspace: " + jcrSession.getWorkspace());
        //messages a desplegar
       
        String fromRepo = "CDP";
        //
         // Create path components to save the file
          final String path = "/home/xumakgt6/jackrabbit-2.6.5/jackrabbit-standalone/target/jackrabbit/images";
          final Part filePart = request.getPart("file");
          final String fileName = getFileName(filePart);
        //
        PrintWriter printer = response.getWriter();
        InputStream in = null;
        OutputStream out = null;
        //verificacion de paramentros de entrada
      
          
            
        //
      try{
          
          in = filePart.getInputStream();
          Node root = jcrSession.getRootNode();    
          addFileToRepo(jcrSession,in,request);// @see method addFileToRepo(Session jcr,InputStream in,HttpServletRequest request)
          jcrSession.save();
          Node node = root.getNode("Message");
          PropertyIterator piterator =node.getProperties();
          Node imageNode = root.getNode("Images");
          PropertyIterator img_iterator =imageNode.getProperties();
          Node DocsNode = root.getNode("Docs");
          PropertyIterator docs_iterator =DocsNode.getProperties();
          String title = "Uploading to JackRabbit Repo";
          String docType =
            "<!doctype html public \"-//w3c//dtd html 4.0 " +
            "transitional//en\">\n";
            printer.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                " <li><b>thoose are the strings saved on jack Rabbit Repository</b>:</li> ");
                while(piterator.hasNext())
                {
                  Property tmp = piterator.nextProperty();
                  fromRepo = tmp.getString();
                  if(!(fromRepo.equals("nt:unstructured")))
                  {    
                      printer.println("<li>"+fromRepo+"</li>");
                  }
                }
                 
               printer.println("</ul>\n" +
                "<ul>\n" +
                " <li><b>thoose are the images saved on jack Rabbit Repository</b>:</li> ");
                while(img_iterator.hasNext())
                {
                  Property tmp = img_iterator.nextProperty();
                  String filename = tmp.getName();
                  String pathJR;
                  pathJR = tmp.getPath();
                  InputStream imagen = tmp.getStream();
                  String spath = path+"/new"+filename;
                  System.out.println("*******************"+pathJR+"****************"+spath);
                  if(!(filename.equals("jcr:primaryType")))
                  {    
                      printer.println("<li><a  href=\"ImageLouderServlet?id="+filename+"\" > "+filename+"</a></li>");
                       
                  }
                }
                 
               printer.println("</ul>\n"+
                "<ul>\n" +
                " <li><b>thoose are the Documents saved on jack Rabbit Repository</b>:</li> ");
                while(docs_iterator.hasNext())
                {
                  Property tmp = docs_iterator.nextProperty();
                  String filename = tmp.getName();
                  if(!(filename.equals("jcr:primaryType")))
                  {    
                      printer.println("<li>"+filename+"</li>");
                  }
                }
                 
                printer.println("</ul>\n"+
                "</body></html>");
         
      }finally{
          jcrSession.logout();
          if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
        }
        if (printer != null) {
            printer.close();
        }
      }
      
      
    }
    /*creado por @autor xumakgt6 (Allan Revolorio)
    *el metodo getFileName(Part part) devuelve un string con el nombre del archivo 
    * @param part es un Objecto file del cual obtendremos el String.
    * @return un substring con el nombre del archivo + la extension del archivo
    */
    private String getFileName(Part part) {
    final String partHeader = part.getHeader("content-disposition");
    LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
         for (String content : part.getHeader("content-disposition").split(";")) {
              if (content.trim().startsWith("filename")) {
                 return content.substring(
                    content.indexOf('=') + 1).trim().replace("\"", "");
              }
        }
        return null;
    }
     /*creado por @autor xumakgt6 (Allan Revolorio)
    *el metodo addFileToRepo(Session jcrSession, InputStream in, HttpServletRequest request) se encarga de agregar el archivo leido en el InputStream 
    *y Guardarlo en un nuevo property en el nodo que le corresponde segun su tipo de archivo.
    * @param Session jcrSession es la sesion iniciada al servidor de jackrabbit-standalone (Donde esta el repositorio remoto).
    * @param InputStream in es el binario del archivo.
    * @param HttpServletRequest request es el request que se le hizo a este servlet, desde este se optiene el file name 
    */
    public void addFileToRepo(Session jcrSession, InputStream in, HttpServletRequest request) throws RepositoryException, IOException, ServletException// este metodo agrega archivos al repositorio dependiendo de que tipo de archivo sea 
    {
          Node root = jcrSession.getRootNode();
          Part filePart = request.getPart("file");
          final String fileName = getFileName(filePart);//* @see getFileName(Part part)
          int point = fileName.lastIndexOf(".");
          String ext = fileName.substring(point, fileName.length());
          Node fileNode = null;
          if((ext.equals(".jpg"))||(ext.equals(".png"))||(ext.equals(".gif"))||(ext.equals(".jpeg")))
          {
              fileNode = root.getNode("Images");
              fileNode.setProperty(fileName, in);
              jcrSession.save();
          }else{
              if((ext.equals(".doc"))||(ext.equals(".txt"))||(ext.equals(".pdf"))||(ext.equals(".docx"))||(ext.equals(".docx"))){
                  fileNode = root.getNode("Docs");
                  fileNode.setProperty(fileName, in);
                  jcrSession.save();
              }
          }
         //Node imgNode = root.getNode("Images");
         //System.out.println(">>>>name of the file: "+fileName+" and the extension is "+ext);
         
    }
      /*creado por @autor xumakgt6 (Allan Revolorio)
    *el metodo addFileToRepo(Session jcrSession, String msg) se encarga de agregar un string al nodo "Messages" en repositorio remoto remoto de jackrabbit 
    *y Guardarlo en un nuevo property en el nodo que le corresponde segun su tipo de archivo.
    * @param Session jcrSession es la sesion iniciada al servidor de jackrabbit-standalone (Donde esta el repositorio remoto).
    * @param String msg es el string que se desea agregar al repositorio remoto de jackrabbit
    */
    public void addMessageToRepo(Session jcrSession, String msg) throws RepositoryException// este metodo agrega Messages(strings) al repositorio de jackrabbit
    {
        Node root = jcrSession.getRootNode();
        Node node = root.getNode("Message");
        node.setProperty(msg, msg);
        jcrSession.save();
        
    }
    /*creado por @autor xumakgt6 (Allan Revolorio)
    *el metodo getMessagesFromRepo(Session jcrSession) obtiene un lista encadenada con cada property del nodo Messages del repositorio de jackrabbit.
    * @param Session jcrSession es la sesion iniciada al servidor de jackrabbit-standalone (Donde esta el repositorio remoto).
    */
    public LinkedList<Node> getMessagesFromRepo(Session jcrSession) throws RepositoryException// este metodo obtienen una lista de nodos del nodo message del repositorio de jackrabbit 
    {
        LinkedList<Node> msg = new LinkedList<Node>();
        Node root = jcrSession.getRootNode();
        Node Messages = root.getNode("Message");
        NodeIterator ni = Messages.getNodes();
        while(ni.hasNext())
        {
            Node next = ni.nextNode();
            msg.add(next);
        }
        
        return msg;
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

