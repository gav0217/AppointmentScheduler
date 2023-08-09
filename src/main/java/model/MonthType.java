package model;

import java.time.Month;

/**
 * This is the MonthType class.
 *
 * @author Gavril Fofiu.
 */
public class MonthType {
/**
 * This the Month Type.
 */
public MonthType(){

}

/**
 * This the table view month.
 */

private String month;

/**
 * @param month sets the appointment month.
 */
public void setMonth(String month){
        this.month = month;
};

/**
 * @return the appointment month.
 */
public String getMonth(){
        return month;}

/**
 * This the appointment type.
 */
private String type;

/**
 * @param type sets the appoitment type.
 */

public void setType(String type){
        this.type = type;
};

/**
 * @return the appointment type.
 */
public String getType(){
        return type;}

/**
 * This is the appointment total.
 */
public int total;

/**
 * @param total sets the appointment total.
 */
public void setTotal(int total){
        this.total = total;
}

/**
 * @return the appointment total.
 */
public int getTotal(){
        return total;
}

}
