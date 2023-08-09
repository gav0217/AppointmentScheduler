package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This is the model class for Customer.
 *
 * @author Gavril Fofiu
 */
public class Customer {
/**
 * This the Customer.
 */
public Customer() {
}

/**
 * This is the customer ID.
 */
private int customer_ID;

/**
 * @return the customer ID.
 */

public int getCustomer_ID() {
    return customer_ID;
}

/**
 * @param customer_ID set the customer ID.
 */
public void setCustomer_ID(int customer_ID) {
    this.customer_ID = customer_ID;
}

/**
 * This the customer name.
 */
private String customer_Name;

/**
 * @return the customer name.
 */
public String getCustomer_Name() {
    return customer_Name;
}

/**
 * @param customer_Name sets the customer name.
 */
public void setCustomer_Name(String customer_Name) {
    this.customer_Name = customer_Name;
}

/**
 * This is the customer address.
 */
private String address;

/**
 * @return returns the customer address.
 */
public String getAddress() {
    return address;
}

/**
 * @param address sets the customer address.
 */
public void setAddress(String address) {
    this.address = address;
}

/**
 * This is the customer postal code.
 */
private String postal_Code;

/**
 * @return the customer postal code.
 */
public String getPostal_Code() {
    return postal_Code;
}

/**
 * @param postal_Code customer postal code.
 */
public void setPostal_Code(String postal_Code) {
    this.postal_Code = postal_Code;
}

/**
 * This is the customer phone.
 */
private String phone;

/**
 * @return the customer phone.
 */
public String getPhone() {
    return phone;
}

/**
 * @param phone sets the customer phone.
 */
public void setPhone(String phone) {
    this.phone = phone;
}

/**
 * This is the customer create date.
 */
private LocalDateTime create_Date;

/**
 * @param create_Date sets the customer create date.
 */
public void setCreate_Date(LocalDateTime create_Date) {
    this.create_Date = create_Date;
}

/**
 * @return the customer create date.
 */
public LocalDateTime getCreate_Date() {
    return create_Date;
}

/**
 * This the customer created by.
 */
private String created_By;

/**
 * @return the customer created by.
 */
public String getCreated_By() {
    return created_By;
}

/**
 * @param created_By sets the customer created by.
 */
public void setCreated_By(String created_By) {
    this.created_By = created_By;
}

/**
 * This is the last customer update timestamp.
 */
private Timestamp lastUpdate;

/**
 * @param LastUpdate sets the last customer update timestamp.
 */
public void setLastUpdate(Timestamp LastUpdate) {
    this.lastUpdate = LastUpdate;
}

/**
 * @return the customer last update timestamp.
 */
public Timestamp getLastUpdate() {
    return lastUpdate;
}

/**
 * This is the customer last updated by.
 */
private String last_Updated_By;

/**
 * @param Last_Updated_By sets the customer last updated by.
 */
public void setLast_Updated_By(String Last_Updated_By) {
    this.last_Updated_By = Last_Updated_By;
}

/**
 * @return customer last updated by.
 */
public String getLast_Updated_By() {
    return last_Updated_By;
}

/**
 * This is the division ID.
 */
private int division_ID;

/**
 * @return the division ID.
 */
public int getDivision_ID() {
    return division_ID;
}

/**
 * @param division_ID sets the customer division ID.
 */
public void setDivision_ID(int division_ID) {
    this.division_ID = division_ID;
}

/**
 * This is the customer division.
 */
private String division;

/**
 * @return the customer division.
 */
public String getDivision() {
    return division;
}

/**
 * @param division sets the division.
 */
public void setDivision(String division) {
    this.division = division;
}

/**
 * This the customer country.
 */
private String country;

/**
 * @return customer country.
 */
public String getCountry() {
    return country;
}

/**
 * @param country sets the customer country.
 */
public void setCountry(String country) {
    this.country = country;
}

}

