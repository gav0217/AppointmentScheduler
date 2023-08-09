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

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This is the edit appointment controller class.  This class loads the selected appointment, and sets the information
 * from the database into it. Loads the information into the combo boxes that was selected.  Afterward, it saves it into
 * the database.
 *
 * @author Gavril Fofiu
 */
public class EditAppointment implements Initializable {
/**
 * This is the cancel appointment edit button.
 */
public Button cancelEditAppointment;
/**
 * This is the save appointment edit button.
 */
public Button saveEditAppointment;
/**
 * This the edit customer name combo box.
 */
public ComboBox<ItemComboBox> editAppointmentCustomerName;
/**
 * This is the edit user ID combo box.
 */
public ComboBox<ItemComboBox> editAppointmentUserID;
/**
 * This is the edit contact combo box.
 */
public ComboBox<ItemComboBox> editAppointmentContact;
/**
 * This is a non-editable appointment ID text field.
 */
@FXML
private TextField editAppointmentAppointmentID;
/**
 * This is the appointment description text field.
 */

@FXML
private TextField editAppointmentDescription;
/**
 * This the appointment end date, date picker.
 */
@FXML
private DatePicker editAppointmentEndDate;
/**
 * This is the end time combo box.
 */

@FXML
private ComboBox<String> editAppointmentEndTime;
/**
 * This is the appointment location text field.
 */

@FXML
private TextField editAppointmentLocation;
/**
 * This is the appointment start date, date picker.
 */

@FXML
private DatePicker editAppointmentStartDate;
/**
 * This is the appointment start time combo box.
 */

@FXML
private ComboBox<String> editAppointmentStartTime;
/**
 * This is the appointment title text field.
 */

@FXML
private TextField editAppointmentTitle;
/**
 * This is the appointment type text field.
 */

@FXML
private TextField editAppointmentType;

/**
 * This a method to cancel.  This method cancels the edit appointment.
 * It closes the window once clicked.
 *
 * @param event once the button is clicked.
 */

@FXML
void editAppointmentCancelButton(ActionEvent event) {

    Stage stage = (Stage) cancelEditAppointment.getScene().getWindow();
    stage.close();

}

/**
 * This method is not used.
 *
 * @param event when the button is clicked.
 */

@FXML
void editAppointmentCustomer(MouseEvent event) {
}

/**
 * This is a save method. This method saves the information in the text fields, selected combo box choices,
 * and the dates from the date picker.  Save the information to the database.  Also checks to see if there is any
 * overlap before saving the appointment.
 *
 * @param event when the save button is clicked.
 * @throws SQLException
 */

@FXML
void editAppointmentSaveButton(ActionEvent event) throws SQLException {

    String startTime = editAppointmentStartTime.getSelectionModel().getSelectedItem();

    String endTime = editAppointmentEndTime.getSelectionModel().getSelectedItem();

    int startHour = Integer.parseInt(startTime.substring(0, 2));
    int startMinute = Integer.parseInt(startTime.substring(3, 5));
    appointment.setStart(editAppointmentStartDate.getValue().atTime(startHour, startMinute));

    int endHour = Integer.parseInt(endTime.substring(0, 2));
    int endMinute = Integer.parseInt(endTime.substring(3, 5));
    appointment.setEnd(editAppointmentEndDate.getValue().atTime(endHour, endMinute));

    if (AppointmentDAO.isOverlap(appointment.getStart(), appointment.getEnd(), appointment.getAppointment_ID())) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment");
        alert.setHeaderText("There is a appointment overlap");
        alert.show();
        return;
    }

    appointment.setTitle(editAppointmentTitle.getText());
    appointment.setType(editAppointmentType.getText());
    appointment.setLocation(editAppointmentLocation.getText());
    appointment.setDescription(editAppointmentDescription.getText());

    appointment.setUser_ID(editAppointmentUserID.getSelectionModel().getSelectedItem().getId());
    appointment.setContact_ID((editAppointmentContact.getSelectionModel().getSelectedItem().getId()));
    appointment.setCustomer_ID((editAppointmentCustomerName.getSelectionModel().getSelectedItem().getId()));
    editAppointmentContact.getSelectionModel().select(appointment.getContact_ID());

    AppointmentDAO.ModifyAppoinment(appointment);
    Stage stage = (Stage) saveEditAppointment.getScene().getWindow();
    stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    stage.close();
}

/**
 * This is the main initialize method.  When this window is loaded, it loads the selected combo box options for the appointment.
 * It sets the selected options in the combo boxes by calling the combo box model.
 *
 * @param url
 * @param resourceBundle
 */

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
        editAppointmentCustomerName.setItems(CustomerDAO.getAllIdName());
        editAppointmentCustomerName.setConverter(new StringConverter<>() {
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

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    try {
        editAppointmentUserID.setItems(CustomerDAO.getUsersIdName());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    editAppointmentUserID.setConverter(new StringConverter<>() {
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
        editAppointmentContact.setItems(CustomerDAO.getContactID());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    editAppointmentContact.setConverter(new StringConverter<>() {
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

private Appointment appointment;

/**
 * This is a seter method.  It sets all the selected appointments and all the components from the database.
 *
 * @param _appointment this is the appointment model.
 */

public void setAppointment(Appointment _appointment) {
    appointment = _appointment;

    editAppointmentCustomerName.getSelectionModel().select(editAppointmentCustomerName.getItems().filtered(x -> x.getId().equals(appointment.getCustomer_ID())).stream().findFirst().get());
    editAppointmentUserID.getSelectionModel().select(editAppointmentUserID.getItems().filtered(x -> x.getId().equals(appointment.getUser_ID())).stream().findFirst().get());
    editAppointmentContact.getSelectionModel().select(editAppointmentContact.getItems().filtered(x -> x.getId().equals(appointment.getContact_ID())).stream().findFirst().get());
    editAppointmentAppointmentID.setText(String.valueOf(appointment.getAppointment_ID()));
    editAppointmentTitle.setText(appointment.getTitle());
    editAppointmentType.setText(appointment.getType());
    editAppointmentLocation.setText(appointment.getLocation());
    editAppointmentDescription.setText(appointment.getDescription());
    editAppointmentStartDate.setValue(appointment.getStart().toLocalDate());
    editAppointmentEndDate.setValue(appointment.getEnd().toLocalDate());
    String stime = String.format("%02d", appointment.getStart().getHour()) + ":" + String.format("%02d", appointment.getStart().getMinute());
    editAppointmentStartTime.setItems(Utils.getTime(9, 16));
    var endTime = Utils.getTime(9, 16);
    endTime.remove(0);
    endTime.add("17:00");
    editAppointmentEndTime.setItems(endTime);

    editAppointmentStartTime.getSelectionModel().select(stime);

    String etime = String.format("%02d", appointment.getEnd().getHour()) + ":" + String.format("%02d", appointment.getEnd().getMinute());
    editAppointmentEndTime.getSelectionModel().select(etime);

}

}

