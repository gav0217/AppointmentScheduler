package model;

import java.time.LocalDateTime;

public class Appointment {

public Appointment() {

}

private int appointment_ID;

public int getAppointment_ID() {
    return appointment_ID;
}

public void setAppointment_ID(int appointment_ID) {
    this.appointment_ID = appointment_ID;
}

private String title;

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

private String description;

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

private String location;

public String getLocation() {
    return location;
}

public void setLocation(String Location) {
    this.location = Location;
}

private String type;

public String getType() {
    return type;
}

public void setType(String Type) {
    this.type = Type;
}

private LocalDateTime start;

public void setStart(LocalDateTime Start) {
    this.start = Start;
}

public LocalDateTime getStart() {
    return start;
}

private LocalDateTime end;

public void setEnd(LocalDateTime End) {
    this.end = End;
}

public LocalDateTime getEnd() {
    return end;
}

private String createdBy;

public String getCreatedBy() {
    return createdBy;
}

private Integer customer_ID;

public void setCustomer_ID(Integer Customer_ID) {
    this.customer_ID = Customer_ID;
}

public Integer getCustomer_ID() {
    return customer_ID;
}

private Integer user_ID;

public void setUser_ID(Integer User_ID) {
    this.user_ID = User_ID;
}

public Integer getUser_ID() {
    return user_ID;
}

private Integer contact_ID;

public void setContact_ID(Integer Contact_ID) {
    this.contact_ID = Contact_ID;
}

public Integer getContact_ID() {
    return contact_ID;
}
}