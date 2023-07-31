package model;

import java.time.LocalDateTime;

public class CustomerSchedule {
public CustomerSchedule() {

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

private String type;

public String getType() {
    return type;
}

public void setType(String type) {
    this.type = type;
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

public void setLocation(String location) {
    this.location = location;
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

private int customer_ID;

public int getCustomer_ID() {
    return customer_ID;
}

public void setCustomer_ID(int customer_ID) {
    this.customer_ID = customer_ID;
}

private String customer_Name;

public String getCustomer_Name() {
    return customer_Name;
}

public void setCustomer_Name(String customer_Name) {
    this.customer_Name = customer_Name;
}
}
