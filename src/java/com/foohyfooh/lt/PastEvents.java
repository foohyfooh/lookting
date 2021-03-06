package com.foohyfooh.lt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;

/**
 *
 * @author Jonathan
 */
public class PastEvents extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int page = 1;
            final int ENTRIES_PER_PAGE = 30;
            String pageNum = request.getParameter("page");
            if(pageNum != null && !pageNum.isEmpty()){
                page = Integer.parseInt(pageNum);
            }
            String current = Utils.getCurrentDate();
            EntityManager entityManager = Utils.createEntityManager();
            Query query = entityManager.createNamedQuery("Event.past", Event.class);
            query.setFirstResult((page-1) * ENTRIES_PER_PAGE).setMaxResults(ENTRIES_PER_PAGE);
            List<Event> pastEvents = query.setParameter("startDate", current)
                    .setParameter("endDate", current).getResultList();
            Collections.sort(pastEvents, Event.START_DATE_ASC);
            JSONArray jsonArray  = new JSONArray(pastEvents);
            jsonArray.write(out);
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
