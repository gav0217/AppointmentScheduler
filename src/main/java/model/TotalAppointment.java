package model;

public class TotalAppointment {

public TotalAppointment(){

}

private int customer_ID;

public void setCustomer_ID(int customer_ID){
    this.customer_ID = customer_ID;
};
public int getCustomer_ID(){
    return customer_ID;}

private String customer_Name;

public void setCustomer_Name(String customer_Name){
    this.customer_Name = customer_Name;
};
public String getCustomer_Name(){
    return customer_Name;}

public int amount;

public void setAmount(int amount){
    this.amount = amount;
}

public int getAmount(){
    return amount;
}

}
