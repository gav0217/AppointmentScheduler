package controller;

import dao.AppointmentDAO;
import dao.CustomerDAO;
import helper.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import model.Appointment;
import model.ItemComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This the controller to Add Appointments.
 * <p>
 * This class adds appointments to the database. The user inputs the title, description, location, and type.
 * Afterward, they select a customer name, user ID, and contact ID. Also, they select the date using a data picker, and time's
 * using the drop-downs.
 *
 * @author Gavril Fofiu
 */
public class AddAppointmentController implements Initializable {
/**
 * This is the combo box to select the Customer.
 */
public ComboBox<ItemComboBox> addAppointmentCustomer;
/**
 * This is the Cancel adding appointment button.
 */
public Button cancelAddAppointment;
/**
 * This is the combo box to select the Contact ID.
 */
public ComboBox<ItemComboBox> addAppointmentContactID;
/**
 * This is the combo box to select the User ID.
 */
public ComboBox<ItemComboBox> addAppointmentUserID;
/**
 * This is the non-editable text box to see the Appointment ID.
 */
@FXML
private TextField addAppointmentAppointmentID;
/**
 * This is the text field to add a description to the appointment.
 */
@FXML
private TextField addAppointmentDescription;
/**
 * This the date picker to select the end date for the appointment.
 */
@FXML
private DatePicker addAppointmentEndDate;
/**
 * This the combo box to select the end time for the appointment.
 */
@FXML
private ComboBox<String> addAppointmentEndTime;
/**
 * This is the text field to add the location for the appointment.
 */
@FXML
private TextField addAppointmentLocation;
/**
 * This is the date picker to select the start date for the appointment.
 */
@FXML
private DatePicker addAppointmentStartDate;
/**
 * This is the combo box to select the start for the appointment.
 */
@FXML
private ComboBox<String> addAppointmentStartTime;
/**
 * This the text field for the appointment Title for the appointment.
 */
@FXML
private TextField addAppointmentTitle;
/**
 * This is the text field to add the appointment type for the appointment.
 */
@FXML
private TextField addAppointmentType;

/**
 * This method closes the current stage. This method is the cancel button it closes the window.
 *
 * @param event the cancel button is clicked.
 */
@FXML
void addAppointmentCancelButton(ActionEvent event) {

    Stage stage = (Stage) cancelAddAppointment.getScene().getWindow();
    stage.close();
}
/**
 * This is the Save Button method. The method has the user fill the text fields, select the combo boxes
 * for the appointments, and use the date picker.  The method checks for overlaps, and if there are any
 *  overlaps, the error alert notifies the user. If there are no errors, the appointment is saved to the
 * database.
 *
 * @param event the save button is clicked.
 */
@FXML
void addAppointmentSaveButton(ActionEvent event) throws SQLException, IOException {

    ItemComboBox selectedOption = addAppointmentCustomer.getSelectionModel().getSelectedItem();
    Appointment model = new Appointment();

    String startTime = addAppointmentStartTime.getSelectionModel().getSelectedItem();

    String endTime = addAppointmentEndTime.getSelectionModel().getSelectedItem();

    int startHour = Integer.parseInt(startTime.substring(0, 2));
    int startMinute = Integer.parseInt(startTime.substring(3, 5));
    model.setStart(addAppointmentStartDate.getValue().atTime(startHour, startMinute));

    int endHour = Integer.parseInt(endTime.substring(0, 2));
    int endMinute = Integer.parseInt(endTime.substring(3, 5));
    model.setEnd(addAppointmentEndDate.getValue().atTime(endHour, endMinute));

    if (AppointmentDAO.isOverlap(model.getStart(), model.getEnd(), 0)) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment");
        alert.setHeaderText("Appointment overlap");
        alert.show();
        return;
    }

    model.setTitle(addAppointmentTitle.getText());
    model.setDescription(addAppointmentDescription.getText());
    model.setLocation(addAppointmentLocation.getText());
    model.setType(addAppointmentType.getText());

    model.setCustomer_ID(selectedOption.getId());
    model.setUser_ID(addAppointmentUserID.getSelectionModel().getSelectedItem().getId());
    model.setContact_ID((addAppointmentContactID.getSelectionModel().getSelectedItem().getId()));

    AppointmentDAO.AddAppointment(model);

    Stage stage = (Stage) cancelAddAppointment.getScene().getWindow();
    stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    stage.close();
}

private int id;

/**
 * This method set the Title ID.  Method displays the latest Appointment ID from the database.
 *
 * @param _id is the appointment ID.
 */
public void setNewAppointment(int _id) {
    id = _id;
    addAppointmentAppointmentID.setText(String.valueOf(id));
}
/**
 * This is the main initialize method.  When this method is reached, it sets all the Customers into the
 * combo box, the User ID into the combo box, the contact IDs into the combo box, and it sets the appointment
 * start and end times.
 *
 * LAMDA EXPRESSION: When this method is run, it populates the customer combo box.
 * I used this LAMDA function because it helped cut down on the amount of code needed, and much more effective.
 *
 */
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
        addAppointmentCustomer.setItems(CustomerDAO.getAllIdName());
        addAppointmentCustomer.setConverter(new StringConverter<>() {
            @Override
            public String toString(ItemComboBox object) {
                if (object == null) return "";
                return object.getName();
            }

            @Override
            public ItemComboBox fromString(String string1) {
                return addAppointmentCustomer.getItems().stream().filter(ap -> ap.getName().equals(string1)).findFirst().orElse(null);
            }
        });

        addAppointmentStartTime.setItems(Utils.getTime(9, 16));
        var endTime = Utils.getTime(9, 16);
        endTime.remove(0);
        endTime.add("17:00");
        addAppointmentEndTime.setItems(endTime);


    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    try {
        addAppointmentUserID.setItems(CustomerDAO.getUsersIdName());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    addAppointmentUserID.setConverter(new StringConverter<>() {
        @Override
        public String toString(ItemComboBox object) {
            if (object == null) return "";
            return object.getName();
        }

        @Override
        public ItemComboBox fromString(String string1) {
            return null;
        }
    });

    try {
        addAppointmentContactID.setItems(CustomerDAO.getContactID());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    addAppointmentContactID.setConverter(new StringConverter<>() {
        @Override
        public String toString(ItemComboBox object) {
            if (object == null) return "";
            return object.getName();
        }

        @Override
        public ItemComboBox fromString(String string1) {
            return null;
        }
    });
}

public void addAppointmentCustomer(MouseEvent mouseEvent) {
}
/**
 * This is the change selection method.  Records when there is a change in the combo boxes for
 * Customer Name, user ID, and contact ID.
 *
 * @param actionEvent records when the selected item is changed in the combo boxes.
 */
public void changeSelection(ActionEvent actionEvent) throws SQLException {

}
}