package com.foohyfooh.lt;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jonathan
 */
public class Auth {
    
           
    /**
     * <p>Check if the user has a valid session</p>
     * @param session
     * @return <code>true</code> if valid user or <code>false</code> if invalid user
     */
    public static boolean verifySession(HttpSession session){
        EntityManager entityManager = Utils.createEntityManager();
        Integer user_id = (Integer) session.getAttribute("user_id");
        Query query = entityManager.createNamedQuery("User.findById", User.class);
        List<User> user = query.setParameter("id", user_id).getResultList();
        return user.size() == 1 ? true : false;
    };
    
    /**
     * <p>Store the user_id in the session storage</p>
     * @param session 
     * @param userId A user id that should match one in the database
     * @return  <code>true</code> if successful or <code>false</code> if unsuccessful
     */
    public static boolean storeSession(HttpSession session, Integer userId, String username){
        if(userId == -1)
            return false;
        session.setAttribute("user_id", userId);
        session.setAttribute("username", username);
        return true;
    }
    
    /**
     * <p>Remove the user_id from the session</p>
     * @param session
     * @return true
     */
    public static boolean removeSession(HttpSession session){
        session.removeAttribute("user_id");
        session.removeAttribute("username");
        return true;
    }
    
    /**
     * <p>
     * Check the database to see if the user data sent is correct
     * And return back the id of a the user data from the request
     * </p>
     * @param request 
     * @return >= 0 if valid and -1 if invalid
     */
    public static int verifyUser(HttpServletRequest request){
        String username = request.getParameter("username"),
                password = request.getParameter("password");
        EntityManager entityManager = Utils.createEntityManager();
        Query query = entityManager.createNamedQuery("User.getUser", User.class);
        List<User> user = query.setParameter("username", username)
            .setParameter("password", password).getResultList();
        //Can only have one user with this info. So if it the size is not 1
        //then return -1
        return user != null && user.size() == 1 ? user.get(0).getId() : -1;
    }
    
    
    //Method should be in the Database class but was easier to put here
    /**
     * <p>Add a new user to the database</p>
     * @param request
     * @return the int value of the new users id
     */
    public static int register(HttpServletRequest request){
        String username = request.getParameter("username"),  
                password = request.getParameter("password");
        
        EntityManager entityManager = Utils.createEntityManager();
        Query query = entityManager.createNamedQuery("User.findByUsername", User.class);
        List<User> u =  query.setParameter("username", username).getResultList();
        if(u != null && u.size() == 1) return -1;//ie user already exists
        
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        
        //Return verifyUser giving the id of the user in the database 
        return verifyUser(request);
    }
    
}
