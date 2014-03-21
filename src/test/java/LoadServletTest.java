/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.InputStream;
import java.util.LinkedList;
import javax.jcr.Node;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xumakgt6
 */
public class LoadServletTest {
    
    public LoadServletTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of processRequest method, of class LoadServlet.
     * @throws java.lang.Exception
     */
    @Test
    public void testProcessRequest() throws Exception {
        System.out.println("processRequest");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        LoadServlet instance = new LoadServlet();
        instance.processRequest(request, response);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addFileToRepo method, of class LoadServlet.
     */
    @Test
    public void testAddFileToRepo() throws Exception {
        System.out.println("addFileToRepo");
        InputStream in = null;
        HttpServletRequest request = null;
        LoadServlet instance = new LoadServlet();
        instance.addFileToRepo(in, request);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addMessageToRepo method, of class LoadServlet.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddMessageToRepo() throws Exception {
        System.out.println("addMessageToRepo");
        String msg = "";
        LoadServlet instance = new LoadServlet();
        instance.addMessageToRepo(msg);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getMessagesFromRepo method, of class LoadServlet.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetMessagesFromRepo() throws Exception {
        System.out.println("getMessagesFromRepo");
        LoadServlet instance = new LoadServlet();
        LinkedList<Node> expResult = new LinkedList<Node>();
        LinkedList<Node> result = instance.getMessagesFromRepo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
    }

    /**
     * Test of doGet method, of class LoadServlet.
     * @throws java.lang.Exception
     */
    @Test
    public void testDoGet() throws Exception {
        System.out.println("doGet");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        LoadServlet instance = new LoadServlet();
        instance.doGet(request, response);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of doPost method, of class LoadServlet.
     * @throws java.lang.Exception
     */
    @Test
    public void testDoPost() throws Exception {
        System.out.println("doPost");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        LoadServlet instance = new LoadServlet();
        instance.doPost(request, response);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
