package com.foohyfooh.lt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Jonathan
 */
public class Artwork extends HttpServlet {

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
            String path = request.getServletContext().getRealPath("/artwork");
            int bufferSize = 10240;
            
            String requestedImage = request.getPathInfo();
            if(requestedImage == null){
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
                return;
            }
            File image = new File(path, URLDecoder.decode(requestedImage, "UTF-8"));
            if(!image.exists()){
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
                return;
            }
            String contentType = getServletContext().getMimeType(image.getName());
            if(contentType == null || !contentType.startsWith("image")){
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
                return;
            }
            
            response.reset();
            response.setBufferSize(bufferSize);
            response.setContentType(contentType);
            response.setHeader("Content-Length", String.valueOf(image.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + image.getName() + "\"");
            
            
            BufferedInputStream input = null;
            BufferedOutputStream output = null;

            try {
                input = new BufferedInputStream(new FileInputStream(image), bufferSize);
                output = new BufferedOutputStream(response.getOutputStream(), bufferSize);
                IOUtils.copy(input, output);
            } finally {
                if(input != null){
                    input.close();
                }
                if(output != null){
                    output.close();
                }
            }
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
