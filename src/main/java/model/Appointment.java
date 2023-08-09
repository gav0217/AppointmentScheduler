package model;

import java.time.LocalDateTime;

/**
 * This is the model class for Appointments.
 *
 * @author Gavril Fofiu
 */
public class Appointment {

public Appointment() {

}

/**
 * This is the appointment ID.
 */
private int appointment_ID;

/**
 * @return appointment ID.
 */

public int getAppointment_ID() {
    return appointment_ID;
}

/**
 * @param appointment_ID appointment ID to set.
 */
public void setAppointment_ID(int appointment_ID) {
    this.appointment_ID = appointment_ID;
}

/**
 * This is the appointment title.
 */
private String title;

/**
 * @return appointment title.
 */
public String getTitle() {
    return title;
}

/**
 * @param title appointment title to set.
 */
public void setTitle(String title) {
    this.title = title;
}

/**
 * This the appointment description.
 */
private String description;

/**
 * @return appointment description.
 */
public String getDescription() {
    return description;
}

/**
 * @param description appointment description to set.
 */
public void setDescription(String description) {
    this.description = description;
}

/**
 * This is the appointment location.
 */
private String location;

/**
 * @return the appointment location.
 */
public String getLocation() {
    return location;
}

/**
 * @param Location sets the appointment location.
 */
public void setLocation(String Location) {
    this.location = Location;
}

/**
 * This is the appointment string type.
 */
private String type;

/**
 * @return the appointment type.
 */
public String getType() {
    return type;
}

/**
 * @param Type sets the appointment type.
 */
public void setType(String Type) {
    this.type = Type;
}

/**
 * This the appointment start time.
 */
private LocalDateTime start;

/**
 * @param Start sets the appointment start time.
 */
public void setStart(LocalDateTime Start) {
    this.start = Start;
}

/**
 * @return the appointment start time.
 */
public LocalDateTime getStart() {
    return start;
}

/**
 * This is the appointment end time.
 */
private LocalDateTime end;

/**
 * @param End sets the appointment end time.
 */
public void setEnd(LocalDateTime End) {
    this.end = End;
}

/**
 * @return the appointment end time.
 */
public LocalDateTime getEnd() {
    return end;
}

/**
 * This is the appointment created by.
 */
private String createdBy;

/**
 * @return the appointment created by.
 */
public String getCreatedBy() {
    return createdBy;
}

/**
 * This is the appointment customer ID.
 */
private Integer customer_ID;

/**
 * @param Customer_ID sets the appointment customer ID.
 */
public void setCustomer_ID(Integer Customer_ID) {
    this.customer_ID = Customer_ID;
}

/**
 * @return the appointment customer ID.
 */
public Integer getCustomer_ID() {
    return customer_ID;
}

/**
 * This is the appointment user ID.
 */
private Integer user_ID;

/**
 * @param User_ID sets the appointment user ID.
 */
public void setUser_ID(Integer User_ID) {
    this.user_ID = User_ID;
}

/**
 * @return the appointment user ID.
 */
public Integer getUser_ID() {
    return user_ID;
}

/**
 * This the appointment contact ID.
 */
private Integer contact_ID;

/**
 * @param Contact_ID sets the appointment contact ID.
 */
public void setContact_ID(Integer Contact_ID) {
    this.contact_ID = Contact_ID;
}

/**
 * @return the appointment contact ID.
 */
public Integer getContact_ID() {
    return contact_ID;
}
}