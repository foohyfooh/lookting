package com.foohyfooh.lt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jonathan
 */
@MultipartConfig
public class Service extends HttpServlet {

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
        HttpSession session = request.getSession();
        String method = request.getPathInfo();
        if(method == null){//No method specified
            response.sendRedirect("../login.jsp");
        }else if(method.equals("/add")){//Add new event
            
            if(!Auth.verifySession(session)){
                response.sendRedirect("../login.jsp");
            }else{
                Database database = new Database();
                database.add(request);
                response.sendRedirect("../index.jsp");
            }
            
        }else if(method.equals("/edit")){//Edit existing event
            
            if(!Auth.verifySession(session)){                
                response.sendRedirect("../login.jsp");
            }else{
                Database database = new Database();
                database.edit(request);
                response.sendRedirect("../index.jsp");
            }
            
        }else if(method.equals("/delete")){//Delete existing event
            
            if(!Auth.verifySession(session)){
                response.sendRedirect("../login.jsp");
            }else{
                Database database = new Database();
                database.delete(request);
                response.sendRedirect("../index.jsp");
            }
            
        }else if(method.equals("/login")){//Login existing user
            
            if(Auth.verifyUser(request) != -1){
                response.sendRedirect("../index.jsp");
            }else{
                response.sendRedirect("../login.jsp");
            }
            
        }else if(method.equals("/register")){//Create new user
            
            if(Auth.register(request) != -1){
                response.sendRedirect("../index.jsp");
            }else{
                response.sendRedirect("../login.jsp");
            }
            
        }else if(method.equals("/logout")){//Logout current user
            
            Auth.removeSession(session);
            response.sendRedirect("../login.jsp");
            
        }else{//No valid method specified
            response.sendRedirect("../login.jsp");
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
