package dao;

import db.JDBC;
import helper.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import model.Appointment;
import model.Customer;
import model.ItemComboBox;

import java.sql.*;
import java.time.ZoneOffset;

/**
 * This is the Customer DAO class. This is the interface for the implementation of the customer class.
 *
 * @author Gavril Fofiu
 */
public class CustomerDAO {
/**
 * This is a get customer method. This method gets all the customers from the database
 * into a list.
 *
 * @return the observableList getAll customers.
 * @throws SQLException
 */
public static ObservableList<Customer> getAllCustomers() throws SQLException {
    ObservableList<Customer> customersObservableList = FXCollections.observableArrayList();
    String query = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone," +
                           "customers.Division_ID, first_level_divisions.Division, countries.Country from client_schedule.customers " +
                           "INNER JOIN  client_schedule.first_level_divisions ON " +
                           "customers.Division_ID = first_level_divisions.Division_ID " +
                           "INNER JOIN client_schedule.countries  ON " +
                           "first_level_divisions.country_id = countries.country_id";
    PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        Customer customers = new Customer();
        customers.setCustomer_ID(rs.getInt("Customer_ID"));
        customers.setCustomer_Name(rs.getString("Customer_Name"));
        customers.setAddress(rs.getString("Address"));
        customers.setPostal_Code(rs.getString("Postal_Code"));
        customers.setPhone(rs.getString("Phone"));
        customers.setCountry(rs.getString("Country"));
        customers.setDivision(rs.getString("Division"));
        customersObservableList.add(customers);
    }

    return customersObservableList;
}

/**
 * This is a get all customer's method. This method gets all the customer names from the
 * database.
 *
 * @return an observable list of all the customer names.
 * @throws SQLException
 */
public static ObservableList<String> getAll() throws SQLException {
    ObservableList<String> customersObservableList = FXCollections.observableArrayList();
    customersObservableList.add("All");
    String query = "Select Customer_Name from Customers";
    PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        customersObservableList.add(rs.getString("Customer_Name"));
    }

    return customersObservableList;
}

/**
 * This is a get all Countries method. This method gets all the countries from the database and
 * loads it into a list for a combo box.
 *
 * @return a observableList of AllCountries.
 * @throws SQLException
 */
public static ObservableList<ItemComboBox> getAllCountries() throws SQLException {
    ObservableList<ItemComboBox> customersObservableList = FXCollections.observableArrayList();

    String query = "Select Country_ID, Country from countries";
    PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        customersObservableList.add(new ItemComboBox(rs.getInt("Country_ID"), rs.getString("Country")));
    }

    return customersObservableList;
}

/**
 * This is a get states method. This method gets the states which match the country ID from the database
 * into a list for the combo box.
 *
 * @param countryId the country ID is passed in.
 * @return an observableList of states/provences that match the country ID.
 * @throws SQLException
 */
public static ObservableList<ItemComboBox> getStates(int countryId) throws SQLException {
    ObservableList<ItemComboBox> customersObservableList = FXCollections.observableArrayList();

    String query = "Select Division, Division_ID from first_level_divisions where COUNTRY_ID = " + countryId;
    PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        customersObservableList.add(new ItemComboBox(rs.getInt("Division_ID"), rs.getString("Division")));
    }
    return customersObservableList;
}

/**
 * This a get method. This method gets the customer ID and customer Name from the database, and creates a list for
 * a combo box.
 *
 * @return an observable list of customer names.
 * @throws SQLException
 */
public static ObservableList<ItemComboBox> getAllIdName() throws SQLException {
    ObservableList<ItemComboBox> customersObservableList = FXCollections.observableArrayList();
    String query = "Select Customer_Id, Customer_Name from Customers";
    PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        customersObservableList.add(new ItemComboBox(rs.getInt("Customer_Id"), rs.getString("Customer_Name")));
    }
    return customersObservableList;
}

/**
 * This is a get method for users. This method loads all the user ID and usernames from the database.
 * It sets the list into a combo box.
 *
 * @return an observableList of User ID and Username.
 * @throws SQLException
 */
public static ObservableList<ItemComboBox> getUsersIdName() throws SQLException {
    ObservableList<ItemComboBox> customersObservableList = FXCollections.observableArrayList();
    String query = "Select User_Id, User_Name from Users";
    PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        customersObservableList.add(new ItemComboBox(rs.getInt("User_Id"), rs.getString("User_Name")));
    }
    return customersObservableList;
}

/**
 * This is a get method for contact ID. This method loads all the contact ID, and contact name into a list
 * for the combo box.
 *
 * @return an observableList of ContactID.
 * @throws SQLException
 */
public static ObservableList<ItemComboBox> getContactID() throws SQLException {
    ObservableList<ItemComboBox> customersObservableList = FXCollections.observableArrayList();
    String query = "Select Contact_ID, Contact_Name from Contacts";
    PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        customersObservableList.add(new ItemComboBox(rs.getInt("Contact_ID"), rs.getString("Contact_Name")));
    }
    return customersObservableList;
}

/**
 * This is an add method.  This method adds a new customer to the database.  This method
 * used the customer model to add into proper columns.
 *
 * @param model of customers' table.
 * @throws SQLException
 */
public static void AddCustomer(Customer model) throws SQLException {
    String insertSQL = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)" +
                               " VALUES (?, ?, ?, ?, NOW(), '" + Utils.loginUser + "', NOW(), '" + Utils.loginUser + "', ?)";

    try (Connection connection = JDBC.startConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

        preparedStatement.setString(1, model.getCustomer_Name());
        preparedStatement.setString(2, model.getAddress());
        preparedStatement.setString(3, model.getPostal_Code());
        preparedStatement.setString(4, model.getPhone());
        preparedStatement.setInt(5, model.getDivision_ID());

        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Row inserted successfully!");
        } else {
            System.out.println("Failed to insert row!");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

/**
 * This a check method.  This method checks if a customer has appointments before allowing deleting.
 *
 * @param customerId ths customerID to check for appointments.
 * @return statement if there are or are not appointments.
 * @throws SQLException
 */
public static boolean checkCustomerAppointment(int customerId) throws SQLException {
    boolean haveAppointment = false;
    String query = "select count(*) as count from appointments where Customer_ID = ?";
    PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
    ps.setInt(1, customerId);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        haveAppointment = rs.getInt("count") != 0;
    }
    return haveAppointment;
}

/**
 * This is a delete customer method. This method deletes a customer from the database.
 * Searches and deletes by customer ID.
 *
 * @param cust is a customer object.
 */
public static void DeleteCustomer(Customer cust) {
    try (Connection connection = JDBC.startConnection();
         PreparedStatement preparedStatement = connection.prepareStatement("delete from customers where Customer_ID = " + cust.getCustomer_ID())) {
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

/**
 * This is a modify customer method.  This method allows for the modifying of customers in the database.
 * Use a customer model.
 *
 * @param model is customer model object.
 */
public static void ModifyCustomer(Customer model) {
    String updateSQL = "Update customers set Customer_Name = ?, Address = ?,  Postal_Code = ?, Phone = ?, Last_Update = NOW(), " +
                               "Last_Updated_By = '" + Utils.loginUser + "', Division_ID = ? where Customer_ID = ?";

    try (Connection connection = JDBC.startConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

        // Set the parameter values for the insert statement
        preparedStatement.setString(1, model.getCustomer_Name());
        preparedStatement.setString(2, model.getAddress());
        preparedStatement.setString(3, model.getPostal_Code());
        preparedStatement.setString(4, model.getPhone());
        preparedStatement.setInt(5, model.getDivision_ID());
        preparedStatement.setInt(6, model.getCustomer_ID());

        preparedStatement.executeUpdate();


    } catch (SQLException e) {
        e.printStackTrace();
    }
}
/**
 * This method gets the country depending on the selected state or provence. This method gets the country ID
 * from the first level division database.
 *
 * @param division string of divisions.
 * @return a observableList CountryFromState.
 * @throws SQLException
 */
public static int getCountryFromState(String division) throws SQLException {
    int countryId = 0;
    String query = "select Country_ID from first_level_divisions where Division = ?";
    PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
    ps.setString(1, division);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        countryId = rs.getInt("Country_ID");
    }
    return countryId;
}

/**
 * This method gets the next customer ID. The method checks the database for the latest Customer ID, and returns it
 * in the add customer window.
 *
 * @return last customer ID.
 * @throws SQLException
 */
public static int getNextCustomerId() throws SQLException {
    int max = 1;
    try {
        String sql = "select MAX(Customer_ID) as max from customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            max = rs.getInt("max") + 1;

        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return max;
}

}

