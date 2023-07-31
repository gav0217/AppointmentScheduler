package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Customer {

public Customer(){
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

private String address;

public String getAddress() {
    return address;
}

public void setAddress(String address) {
    this.address = address;
}

private String postal_Code;

public String getPostal_Code() {
    return postal_Code;
}

public void setPostal_Code(String postal_Code) {
    this.postal_Code = postal_Code;
}

private String phone;

public String getPhone() {
    return phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}

private LocalDateTime create_Date;

public void setCreate_Date(LocalDateTime create_Date){
    this.create_Date = create_Date;
}

public LocalDateTime getCreate_Date(){
    return create_Date;
}

private String created_By;

public String getCreated_By() {
    return created_By;
}

public void setCreated_By(String created_By) {
    this.created_By = created_By;
}

private Timestamp lastUpdate;

public void setLastUpdate(Timestamp LastUpdate){
    this.lastUpdate = LastUpdate;
}

public Timestamp getLastUpdate(){
    return lastUpdate;
}

private String last_Updated_By;

public void setLast_Updated_By(String Last_Updated_By){
    this.last_Updated_By = Last_Updated_By;
}

public String getLast_Updated_By(){
    return last_Updated_By;
}

private int division_ID;

public int getDivision_ID() {
    return division_ID;
}

public void setDivision_ID(int division_ID) {
    this.division_ID = division_ID;
}
private String division;

public String getDivision() {
    return division;
}

public void setDivision(String division) {
    this.division = division;
}

private String country;

public String getCountry() {
    return country;
}

public void setCountry(String country) {
    this.country = country;
}

}

