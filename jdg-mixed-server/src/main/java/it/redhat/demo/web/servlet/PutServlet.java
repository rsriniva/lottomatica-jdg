/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.redhat.demo.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import it.redhat.demo.cache.LocalCacheContainer;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

/**
 *
 * @author francesco
 */
@WebServlet(name = "PutServlet", urlPatterns = {"/PutServlet"})
public class PutServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Inject
    @LocalCacheContainer
    private CacheContainer cacheContainer;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cache cache = null;
        try {
            response.setContentType("text/html;charset=UTF-8");

            cache = cacheContainer.getCache("MyCoolCache");
            cache.put("frank", "value");
            System.out.println("Put in cache!");

            TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
            tm.begin();
            cache.put("wrong", "value");
            tm.rollback();

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet PutServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Cache content</h1>");

                Set set = cache.keySet();
                Iterator i = set.iterator();
                while (i.hasNext()) {
                    String key = (String) i.next();
                    out.println("<p>[ key: " + key + " - value: " + cache.get(key) + " </p>");
                }

                out.println("</body>");
                out.println("</html>");
            }
        } catch (NotSupportedException ex) {
            Logger.getLogger(PutServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(PutServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
