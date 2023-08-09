package controller;

import dao.CustomerDAO;
import dao.ReportDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This is the report's controller class. This class loads three different table views.
 * The main table view shows all the customer's and their appointments.  This can be sorted by selecting
 * a specific customer. The next table loaded is the number of appointments for each month based on the type.
 * The final table view loads the total appointments for each customer.
 *
 * @author Gavril Fofiu
 */

public class ReportController implements Initializable {
/**
 * This is the customers and appointments table view.
 */
public TableView customerScheduleTable;
/**
 * This the month and type table view.
 */
public TableView monthTypeTable;
/**
 * This is the total appointments for each customer table view.
 */
public TableView totalAppointmentTable;
/**
 * This is the choice box to select the desired customer for appointments.
 */
public ChoiceBox reportsSelectCustomer;

/**
 * This it the main initialize method for the report controller. This method loads the table views
 * when the tab is selected.
 *
 * @param url
 * @param resourceBundle
 */
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {

    try {
        customerScheduleTable.setItems(ReportDAO.getAllCustomerSchedule(""));

        monthTypeTable.setItems(ReportDAO.getAllMonthType());

        totalAppointmentTable.setItems(ReportDAO.getAllTotalAppointment());

        reportsSelectCustomer.setItems(CustomerDAO.getAll());

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

/**
 * This method checks the choice box selection. The method loads the selected customer appointments into the
 * table view.
 *
 * @param actionEvent this is an on click.
 * @throws SQLException
 */

public void changeSelection(ActionEvent actionEvent) throws SQLException {
    String selectedOption = (String) reportsSelectCustomer.getSelectionModel().getSelectedItem();
    customerScheduleTable.setItems(ReportDAO.getAllCustomerSchedule(selectedOption));
}
}
