package com.foohyfooh.lt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Jonathan
 */
public class Database {

    private final EntityManager entityManager;
    private final List<Event> events;
    private final int next;
    private static final int ADMIN_ID = 1;

    public Database() {
        entityManager = Utils.createEntityManager();
        Query query = entityManager.createNamedQuery("Event.findAll", Event.class);
        events = query.getResultList();
        next = events.isEmpty() ? 1 : 1 + ((Event) events.get(events.size() - 1)).getId();
    }
    
    /**
     * Simple print out of all the events in the database
     * @param out 
     */
    public void print(Writer out){
        for(Event event: events){
            try {
                out.write(event.toString()+"<br />");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }
    
    /**
     * <p>Display the user options to modify the a user's events</p>
     * @param out
     * @param request 
     */
    public void printDev(Writer out, HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer user_id = (Integer) session.getAttribute("user_id");
        
        List<Event> toPrint = null;
        if(user_id != ADMIN_ID){
            Query query = entityManager.createNamedQuery("Event.findByUserId", Event.class)
                    .setParameter("userId", user_id);
            toPrint = query.getResultList();
        }
        for(Event event: user_id == ADMIN_ID ? events : toPrint){
            try {
                out.write(event.toArtworkImage()+ event.toString() + " " + event.toDevString()+"<br />");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    /**
     * <p>Add a new event to the database with the info sent in the request</p>
     * @param request 
     */
    public void add(HttpServletRequest request) {
        Event event = new Event(next);
        try {
            String eventName = "";
            if(ServletFileUpload.isMultipartContent(request)){
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);
                
                //Setup event properties
                for(FileItem item: items){
                    if(item == null)
                        break;
                    String inputName = item.getFieldName();
                    if(inputName.equals("name")){
                        eventName = item.getString().trim();
                        if(eventName == null || eventName.isEmpty()){
                            return;
                        }
                        event.setName(eventName);
                    }else if(inputName.equals("category")){
                        String category = item.getString().trim();
                        if(category == null || category.isEmpty()){
                            return;
                        }
                        event.setCategory(category);
                    }else if(inputName.equals("start_date")){
                        String startDate = item.getString().trim();
                        if(startDate == null || startDate.isEmpty()){
                            return;
                        }
                        event.setStartDate(startDate);
                    }else if(inputName.equals("end_date")){
                        String endDate = item.getString().trim();
                        if(endDate == null || endDate.isEmpty()){
                            return;
                        }
                        event.setEndDate(endDate);
                    }else if(inputName.equals("price")){
                        String price = item.getString();
                        if(price == null || price.isEmpty()){
                            return;
                        }
                        event.setPrice(price);
                    }else if(inputName.equals("address")){
                        String address = item.getString().trim();
                        if(address == null || address.isEmpty()){
                            return;
                        }
                        event.setAddress(address);
                    }else if(inputName.equals("description")){
                        String description = item.getString().trim();
                        if(description != null){
                            event.setDescription(description);
                        }else{
                            event.setDescription("");
                        }
                    }else if(inputName.equals("contact")){
                         String contact = item.getString().trim();
                        if(contact != null){
                            event.setContact(contact);
                        }else{
                            event.setContact("");
                        }
                    }else if(inputName.equals("artwork")){
                        setUpArtwork(event, item, next, eventName, request);
                    }
                }
            }
            Integer userId = (Integer) request.getSession().getAttribute("user_id");
            event.setUserId(userId);
            persist(event);
        } catch (FileUploadException e) {
            JOptionPane.showMessageDialog(null, e);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * <p>Edit an existing event in the database</p>
     * @param request 
     */
    public void edit(HttpServletRequest request) throws IOException{
        Event  event = new Event();
        try {
            String eventName = "";
            int id = -1;
            if(ServletFileUpload.isMultipartContent(request)){
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);
                
                //Setup event properties
                for(FileItem item: items){
                    if(item == null)
                        break;
                    String inputName = item.getFieldName();
                    if(inputName.equals("id")){
                        String idString = item.getString();
                        if(idString == null || idString.isEmpty()){
                            return;
                        }
                        id = Integer.parseInt(idString);
                        Query query = entityManager.createNamedQuery("Event.findById", Event.class)
                                .setParameter("id", id);
                        event = (Event) query.getSingleResult();
                    }else if(inputName.equals("name")){
                        eventName = item.getString().trim();
                        if(eventName == null || eventName.isEmpty()){
                            return;
                        }
                        event.setName(eventName);
                    }else if(inputName.equals("category")){
                        String category = item.getString().trim();
                        if(category == null || category.isEmpty()){
                            return;
                        }
                        event.setCategory(category);
                    }else if(inputName.equals("start_date")){
                        String startDate = item.getString().trim();
                        if(startDate == null || startDate.isEmpty()){
                            return;
                        }
                        event.setStartDate(startDate);
                    }else if(inputName.equals("end_date")){
                        String endDate = item.getString().trim();
                        if(endDate == null || endDate.isEmpty()){
                            return;
                        }
                        event.setEndDate(endDate);
                    }else if(inputName.equals("price")){
                        String price = item.getString();
                        if(price == null || price.isEmpty()){
                            return;
                        }
                        event.setPrice(price);
                    }else if(inputName.equals("address")){
                        String address = item.getString().trim();
                        if(address == null || address.isEmpty()){
                            return;
                        }
                        event.setAddress(address);
                    }else if(inputName.equals("description")){
                        String description = item.getString().trim();
                        if(description != null){
                            event.setDescription(description);
                        }
                    }else if(inputName.equals("contact")){
                         String contact = item.getString().trim();
                        if(contact != null){
                            event.setContact(contact);
                        }else{
                            event.setContact("");
                        }
                    }else if(inputName.equals("artwork")){
                        setUpArtwork(event, item, id, eventName, request);
                    }
                }
            }
            Integer userId = (Integer) request.getSession().getAttribute("user_id");
            if(event != null && (event.getUserId() == userId || userId == ADMIN_ID)){
                persist(event);
            }
        } catch (FileUploadException e) {
            JOptionPane.showMessageDialog(null, e);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * <p> Delete an event with the id specified in the request </p>
     * @param request 
     */
    public void delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Query query = entityManager.createNamedQuery("Event.findById", Event.class)
                .setParameter("id", id);
        Event event = (Event) query.getSingleResult();
        Integer user_id = (Integer) request.getSession().getAttribute("user_id");
        if(event != null && (event.getUserId() == user_id || user_id == ADMIN_ID)){
            //Remove the artwork for this event
            /*String fileLocation = request.getServletContext().getRealPath("/artwork");
            String deleteLocation =  fileLocation + event.getArtwork().replace("/", "\\");
            System.out.print(deleteLocation);
            if(!fileLocation.equals(deleteLocation)){
                //Ensure that the fileLocation is not the deleteLocation
                //so that it doesn't delete files for the other events
                File d = new File(deleteLocation);
                if(d.exists() && d.delete()) JOptionPane.showMessageDialog(null, "Worked");
            }*/

            entityManager.getTransaction().begin();
            entityManager.remove(event);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }
    
    
    private void persist(Event e){
        entityManager.getTransaction().begin();
        entityManager.persist(e);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    
    /**
     * Create the artwork for the 
     * @param event
     * @param item
     * @param id
     * @param eventName
     * @param request
     * @throws IOException 
     */
    private void setUpArtwork(Event event, FileItem item, int id, String eventName, HttpServletRequest request) throws IOException{
        //Setup file name
        String itemName = item.getName();
        if(id == -1) return;
        if(itemName.isEmpty()) return;//If no file is present exit
        
        String fileLocation = request.getServletContext().getRealPath("/artwork/");        
        String fileExtension = "";
        if(itemName.endsWith(".png"))fileExtension = ".png";
        else if(itemName.endsWith(".jpeg"))fileExtension = ".jpeg";
        else if(itemName.endsWith(".jpg"))fileExtension = ".jpg";
        else if(itemName.endsWith(".bmp"))fileExtension = ".bmp";
        String artworkURL =  eventName.replace(" ", "_") + "-" + id + fileExtension;

        InputStream uploadStream = item.getInputStream();
        OutputStream outputStream = new FileOutputStream(fileLocation + "/" + artworkURL);
        IOUtils.copy(uploadStream, outputStream);
        event.setArtwork(artworkURL);
    }
}
