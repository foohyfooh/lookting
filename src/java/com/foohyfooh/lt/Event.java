package com.foohyfooh.lt;

import java.io.Serializable;
import java.util.Comparator;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jonathan
 */
@Entity
@Table(name = "events")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
    @NamedQuery(name = "Event.findById", query = "SELECT e FROM Event e WHERE e.id = :id"),
    @NamedQuery(name = "Event.findByCategory", query = "SELECT e FROM Event e WHERE e.category = :category"),
    @NamedQuery(name = "Event.findByStartDate", query = "SELECT e FROM Event e WHERE e.startDate = :startDate"),
    @NamedQuery(name = "Event.findByEndDate", query = "SELECT e FROM Event e WHERE e.endDate = :endDate"),
    @NamedQuery(name = "Event.findByPrice", query = "SELECT e FROM Event e WHERE e.price = :price"),
    @NamedQuery(name = "Event.findByAddress", query = "SELECT e FROM Event e WHERE e.address = :address"),
    @NamedQuery(name = "Event.findByUserId", query = "SELECT e FROM Event e WHERE e.userId = :userId" ),
    @NamedQuery(name = "Event.past", query = "SELECT e FROM Event e WHERE e.startDate <= :startDate AND e.endDate <= :endDate ORDER BY e.startDate DESC"),
    @NamedQuery(name = "Event.happening", query = "SELECT e FROM Event e WHERE e.startDate >= :startDate OR e.endDate >= :endDate ORDER BY e.startDate ASC"),
    @NamedQuery(name = "Event.coming", query = "SELECT e FROM Event e WHERE e.startDate >= :startDate ORDER BY e.startDate ASC")})
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "category")
    private String category;
    @Basic(optional = false)
    @Column(name = "start_date")
    private String startDate;
    @Basic(optional = false)
    @Column(name = "end_date")
    private String endDate;
    @Basic(optional = false)
    @Column(name = "price")
    private String price;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Column(name = "description")
    private String description;
    @Column(name = "contact")
    private String contact;
    @Column(name = "artwork")
    private String artwork;
    @Column(name = "user_id")
    private Integer userId;

    public Event() {
        //Setting optional fields to empty values so null value isn't set in database
        description = "";
        contact = "";
        artwork = "";
    }

    public Event(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description != null ? description : "";
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getContact(){
        return contact != null ? contact : "";
    }
    
    public void setContact(String contact){
        this.contact = contact;
    }

    public String getArtwork() {
        return artwork != null ? artwork : "";
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }
    
    public int getUserId(){
        return userId;
    }
    
    public void setUserId(int userId){
        this.userId = userId;
    }
    
    //Methods made of simplisity
    private String getStartDay(){
        return startDate.substring(0, 10);
    }
    
    private String getStartTime(){
        return startDate.substring(11, 19);
    }
    
    private String getEndDay(){
        return endDate.substring(0, 10);
    }
    
    private String getEndTime(){
        return endDate.substring(11, 19);
    }
    
    
    public long getStartDateMillis(){
        return Utils.toMillis(startDate);
    }
    
    public long getEndDateMillis(){
        return Utils.toMillis(endDate);
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(getStartDay().equals(getEndDay())){
            return String.format("%s will be occuring on %s at %s from %s to %s. Price: %s", name, getStartDay(), address, getStartTime(), getEndTime(), price);
        }
        return String.format("%s will be at %s from %s to %s. Price: %s", name, address, startDate, endDate, price);
    }
    
    /**
     * <p>Get the string that has the edit and delete options</p>
     * @return The HTML String for the edit and delete option
     */
    public String toDevString(){
        return String.format(" <a href='edit.jsp?id=%d&name=%s&category=%s&start_date=%s&end_date=%s&price=%s&address=%s&description=%s&contact=%s'>Edit</a>"
                + " <input class='changeBtn' type='button' value='Delete'/>"
                + "<a href='Service/delete?id=%s' class='hidden'>Confirm</a>"
                + "<input class='changeBtn' type='button' value='Cancel'/>",
                id, Utils.encode(name), Utils.encode(category), startDate, endDate, Utils.encode(price), 
                Utils.encode(address), Utils.encode(description), Utils.encode(contact), id);
        
    }
    
    /**
     * <p>Get the HTML formatted artwork image</p>
     * @return 
     */
    public String toArtworkImage(){
        return artwork != null ?
                String.format("<img src='artwork/%s' width='80x' height='80px' />", artwork)
                : "";
    }
    
    /**
     * Compare by dates in ascending order
     */
    public static Comparator<Event> START_DATE_ASC = new Comparator<Event>(){

        @Override
        public int compare(Event e1, Event e2) {
            return e1.startDate.compareTo(e2.startDate);
        }
        
    };
    
    
    
}
