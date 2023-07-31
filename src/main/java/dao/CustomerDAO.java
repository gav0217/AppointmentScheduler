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

public class CustomerDAO {

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

public static void DeleteCustomer(Customer cust) {
    try (Connection connection = JDBC.startConnection();
         PreparedStatement preparedStatement = connection.prepareStatement("delete from customers where Customer_ID = " + cust.getCustomer_ID())) {
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static void ModifyCustomer(Customer model) {
    String updateSQL = "Update customers set Customer_Name = ?, Address = ?,  Postal_Code = ?, Phone = ?, Last_Update = NOW(), " +
                               "Last_Updated_By = '" + Utils.loginUser + "', Division_ID = ? where Customer_ID = ?" ;

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

