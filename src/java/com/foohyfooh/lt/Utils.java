package com.foohyfooh.lt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jonathan
 */
public class Utils {
    /**
     * <p>Create an <code>EntityManager</code> for Look_Thing Database</p>
     * @return an <code>EntityManager</code> for the database
     */
    public static EntityManager createEntityManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Look_Thing");
        return factory.createEntityManager();
    }
    
    /**
     * <p>Get the current year-month-day hours:minutes:seconds</p>
     * @return 
     */
    public static String getCurrentDate(){
        GregorianCalendar g = (GregorianCalendar) GregorianCalendar.getInstance();
        int year = g.get(GregorianCalendar.YEAR);
        int month = g.get(GregorianCalendar.MONTH) + 1;
        int day = g.get(GregorianCalendar.DAY_OF_MONTH);
        int hours = g.get(GregorianCalendar.HOUR_OF_DAY);
        int minutes = g.get(GregorianCalendar.MINUTE);
        int seconds = g.get(GregorianCalendar.SECOND);
        return String.format("%d-%02d-%02d %02d:%02d:%02d", year, month, day, 
                hours, minutes, seconds);
    }
    
    /**
     * <p>Get a parameter from a request</p>
     * @param request
     * @param parameter Name of the query parameter that you want
     * @return The value of the parameter if it exists else empty string
     */
    public static String getParameter(HttpServletRequest request, String parameter){
        String value = request.getParameter(parameter);
        return value != null ? value : "";
    }
    
    
    /**
     * <p>Return the milliseconds of a specified date passed in</p>
     * @param date A date in the format YYYY-MM-DD HH:mm:SS
     * @return 
     */
    public static long toMillis(String date){
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date).getTime();
        } catch (ParseException e) {//Fallback on deprecated date
            return new Date(date).getTime();
        }
    }
    
    public static String encode(String s){
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
