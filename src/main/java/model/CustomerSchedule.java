package model;

import java.time.LocalDateTime;

/**
 * This is the model class for customer schedule.
 *
 * @author Gavril Fofiu
 */
public class CustomerSchedule {
/**
 * This is the customer schedule.
 */
public CustomerSchedule() {

}

/**
 * This is the customer schedule appointment ID.
 */
private int appointment_ID;

/**
 * @return the customer schedule appointment ID.
 */

public int getAppointment_ID() {
    return appointment_ID;
}

/**
 * @param appointment_ID sets the customer schedule appointment ID.
 */
public void setAppointment_ID(int appointment_ID) {
    this.appointment_ID = appointment_ID;
}

/**
 * This the customer schedule appointment title.
 */
private String title;

/**
 * @return customer schedule appointment title.
 */
public String getTitle() {
    return title;
}

/**
 * @param title set the customer schedule appointment title.
 */
public void setTitle(String title) {
    this.title = title;
}

/**
 * This is the customer schedule appointment type.
 */
private String type;

/**
 * @return the customer schedule appointment type.
 */
public String getType() {
    return type;
}

/**
 * @param type sets the customer schedule appointment type.
 */
public void setType(String type) {
    this.type = type;
}

/**
 * This is the customer schedule appointment description.
 */
private String description;

/**
 * @return the customer schedule appointment description.
 */
public String getDescription() {
    return description;
}

/**
 * @param description sets the customer schedule appointment description.
 */
public void setDescription(String description) {
    this.description = description;
}

/**
 * This is the customer schedule appointment location.
 */
private String location;

/**
 * @return the customer schedule appoitment location.
 */
public String getLocation() {
    return location;
}

/**
 * @param location sets customer schedule appointment location.
 */
public void setLocation(String location) {
    this.location = location;
}

/**
 * This is the customer schedule appointment start.
 */
private LocalDateTime start;

/**
 * @param Start sets the customer schedule appointment start.
 */
public void setStart(LocalDateTime Start) {
    this.start = Start;
}

/**
 * @return the customer schedule appointment start.
 */
public LocalDateTime getStart() {
    return start;
}

/**
 * This the customer schedule appointment end.
 */
private LocalDateTime end;

/**
 * @param End sets the customer schedule appointment end.
 */
public void setEnd(LocalDateTime End) {
    this.end = End;
}

/**
 * @return the customer schedule appointment end.
 */
public LocalDateTime getEnd() {
    return end;
}

/**
 * This is the customer schedule appointment customer ID.
 */
private int customer_ID;

/**
 * @return the customer schedule appointment customer ID.
 */
public int getCustomer_ID() {
    return customer_ID;
}

/**
 * @param customer_ID sets the customer schedule appointment customer ID.
 */
public void setCustomer_ID(int customer_ID) {
    this.customer_ID = customer_ID;
}

/**
 * This is the customer schedule appointment customer name.
 */
private String customer_Name;

/**
 * @return the customer schedule appointment customer name.
 */
public String getCustomer_Name() {
    return customer_Name;
}

/**
 * @param customer_Name sets the customer schedule appointment customer name.
 */
public void setCustomer_Name(String customer_Name) {
    this.customer_Name = customer_Name;
}
}
