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

public class ReportController implements Initializable {

public TableView customerScheduleTable;
public TableView monthTypeTable;
public TableView totalAppointmentTable;
public ChoiceBox reportsSelectCustomer;

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

public void changeSelection(ActionEvent actionEvent) throws SQLException {
    String selectedOption = (String) reportsSelectCustomer.getSelectionModel().getSelectedItem();
    customerScheduleTable.setItems(ReportDAO.getAllCustomerSchedule(selectedOption));
}
}
