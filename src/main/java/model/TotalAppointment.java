package model;

/**
 * This is the TotalAppointment Class
 *
 * @author Gavril Fofiu
 */
public class TotalAppointment {
/**
 * This the total Appointment model.
 */
public TotalAppointment(){

}

/**
 * This the appointment total customer ID.
 */
private int customer_ID;

/**
 * @param customer_ID sets the appointment total customer ID.
 */
public void setCustomer_ID(int customer_ID){
    this.customer_ID = customer_ID;
};

/**
 * @return the appointment total customer ID.
 */
public int getCustomer_ID(){
    return customer_ID;}

/**
 * This is the appointment customer name.
 */
private String customer_Name;

/**
 * @param customer_Name sets the appointment total customer name.
 */
public void setCustomer_Name(String customer_Name){
    this.customer_Name = customer_Name;
};

/**
 * @return the appointment total customer name.
 */
public String getCustomer_Name(){
    return customer_Name;}

/**
 * This is the appointment total amount.
 */
public int amount;

/**
 * @param amount sets the appointment total amount.
 */
public void setAmount(int amount){
    this.amount = amount;
}

/**
 * @return the appointment total amount.
 */
public int getAmount(){
    return amount;
}

}
